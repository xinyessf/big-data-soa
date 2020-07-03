package com.mvtech.mess;


import com.mvtech.mess.filter.DataProducer1;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.execution.datasources.json.JsonUtils;

import java.util.Properties;


public class ProducerDemo {
    private static Logger logger = Logger.getLogger(ProducerDemo.class);
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
    public static void main(String[] args) {
        sendKafka();


    }

    static long cmccFailCount = 0;

    public void test2() {
        SparkSession sparkSession = SparkSession.builder()
                .appName("HiveOnSpark2")
                .master("local[*]")
                .enableHiveSupport()
                .getOrCreate();
        try {
            String sql = "select *  from original.t_ods_cmcc_block_sm where day='20200521' limit 1";
            Dataset<Row> originalData = sparkSession.sql(sql);
            originalData.show();
            JavaRDD<Row> rowJavaRDD = originalData.javaRDD();
            System.out.println("JavaRDD<Row> rowJavaRDD = originalData.javaRDD();");
            rowJavaRDD.foreach(record -> {
                logger.info("开始RDD遍历");
                logger.info("======DataProducer1.getInstance()======"+ DataProducer1.getInstance());
                DataProducer1.getInstance().sendData("cmccsmsqueue", "20200702 18:45 nei");
                logger.info("结束RDD遍历");
            });
            logger.info("==推送数据到移动结束:==== ");
            logger.info("移动推送到kafka失败数量 " + cmccFailCount);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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