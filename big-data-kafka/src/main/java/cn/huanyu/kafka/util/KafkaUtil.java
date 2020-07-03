package cn.huanyu.kafka.util;

import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * @author
 * @version 1.0
 * @date
 */
public class KafkaUtil {


    public static Properties props = new Properties();

    public static String CMCC_QUEUE ="cmccsmsqueue";

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

    public static Producer<String, String> getProducer() {
        //


        ProducerConfig config = new ProducerConfig(props);
        Producer<String, String> producer = new Producer<String, String>(config);
        return producer;
    }

    public static void close(Producer<String, String> kafka) {
        try {
            if (kafka != null) {
                kafka.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
