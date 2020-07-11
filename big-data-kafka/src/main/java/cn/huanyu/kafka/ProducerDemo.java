package cn.huanyu.kafka;

import cn.huanyu.kafka.util.CmccSms;
import cn.huanyu.kafka.util.JsonUtils;
import cn.huanyu.kafka.util.KafkaUtil;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Properties;


public class ProducerDemo {
    private static Logger logger = Logger.getLogger(ProducerDemo.class);
    //   public static Producer<String, String> producer = null;
    static Properties props = new Properties();

    static {
        props.put("metadata.broker.list", "192.168.73.128:9092,192.168.73.129:9092,192.168.73.130:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        /*生产者*/
        props.put("buffer.memory", 33554432); // 缓存大小
        props.put("batch.size", 1000); // producer试图批量处理消息记录。目的是减少请求次数，改善客户端和服务端之间的性能。
        // 这个配置是控制批量处理消息的字节数。如果设置为0，则禁用批处理。如果设置过大，会占用内存空间.
        props.put("linger.ms", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("retries", 0); // 重试次数
    }

    static ProducerConfig config = new ProducerConfig(props);

    // private static Producer<String, String> producer = new Producer<String, String>(config);
    public static void main(String[] args) {
/*
        Properties props = new Properties();
        props.put("metadata.broker.list", "192.168.73.128:9092,192.168.73.129:9092,192.168.73.130:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        *//*生产者*//*
        props.put("buffer.memory", 33554432); // 缓存大小
        props.put("batch.size", 1000); // producer试图批量处理消息记录。目的是减少请求次数，改善客户端和服务端之间的性能。
        // 这个配置是控制批量处理消息的字节数。如果设置为0，则禁用批处理。如果设置过大，会占用内存空间.
        props.put("linger.ms", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("retries", 0); // 重试次数
        ProducerConfig config = new ProducerConfig(props);
        *//**//*
         Producer<String, String> producer = new Producer<String, String>(config);*/
        //  test(props);
        sendKafka();
    /*    for (int i = 1; i <= 1; i++){
            producer.send(new KeyedMessage<String, String>("huanyu", "发消息测试" + i));

        }*/
        //producer.send(new ProducerRecord<String, String>("hello_topic", msg));

    }

    static long cmccFailCount = 0;

    public void test2() {
        SparkSession sparkSession = SparkSession.builder()
                .appName("HiveOnSpark2")
                .master("local[*]")
                .enableHiveSupport()
                .getOrCreate();
        //JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(1));
        try {
            String sql = "select *  from iaf.t_ods_cmcc_block_sm where day='20200522' limit 1";
            Dataset<Row> originalData = sparkSession.sql(sql);
            originalData.show();
            JavaRDD<Row> rowJavaRDD = originalData.javaRDD();
            System.out.println("JavaRDD<Row> rowJavaRDD = originalData.javaRDD();");
            rowJavaRDD.foreach(record -> {
                String queue = KafkaUtil.CMCC_QUEUE;
                Producer<String, String> producer = KafkaUtil.getProducer();
                String c_usernum = record.getString(0);
                String c_relatenum = record.getString(1);
                String c_time = record.getString(2);
                String c_content = record.getString(3);
                //avro此处需要优化
                CmccSms cmccSms = new CmccSms(c_usernum, c_relatenum, c_time, c_content, 4);
                String json = null;
                try {
                    json = JsonUtils.serialize(cmccSms);
                    System.out.println("===============2"+json);
                    if (json != null && json != "") {
                        producer.send(new KeyedMessage<String, String>(queue, json));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("写移动json" + "错误 " + cmccSms.toString());
                }
            });
            //KafkaProducer<String, String> finalProducer = producer;
            /*rowJavaRDD.foreach(row -> {
                String c_usernum = row.getString(0);
                String c_relatenum = row.getString(1);
                String c_time = row.getString(2);
                String c_content = row.getString(3);
                //avro此处需要优化
                CmccSms cmccSms = new CmccSms(c_usernum, c_relatenum, c_time, c_content, 1);
                String json = null;
                try {
                    json = JsonUtils.serialize(cmccSms);
                    if (json != null && json != "") {
                        // System.out.println("finalProducer" + finalProducer);
                        //sendKafka(json);
                        producer.send(new KeyedMessage<String, String>("huanyu", json));
                        //2.==写入到队列
                        // finalProducer.send(new KeyedMessage<String, String>("huanyu", json));
                    } else {
                        cmccFailCount++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("写移动json" + "错误 " + cmccSms.toString());
                    cmccFailCount++;
                }
            });*/
            logger.info("==推送数据到移动结束:==== ");
            logger.info("移动推送到kafka失败数量 " + cmccFailCount);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // producer.close();
            sparkSession.close();
        }
    }

    public void test() {
        test2();
    }

    public static void sendKafka() {
        new ProducerDemo().test();
    }

}