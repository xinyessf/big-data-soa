package com.mvtech.mess.filter;

import com.mvtech.mess.util.PropUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * @author 黎政
 */
public class DataProducer1 {
    private static Logger logger = Logger.getLogger(DataProducer1.class);


    private static DataProducer1 INSTANCE;

    private static Producer<String, String> producer;

    private DataProducer1() {
    }


    static {
        Properties props = new Properties();
        props = new Properties();
        props.put("bootstrap.servers", "192.168.73.128:9092,192.168.73.129:9092,192.168.73.130:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        /*生产者*/
        props.put("buffer.memory", 33554432); // 缓存大小
        props.put("batch.size", 1000); // producer试图批量处理消息记录。目的是减少请求次数，改善客户端和服务端之间的性能。
        // 这个配置是控制批量处理消息的字节数。如果设置为0，则禁用批处理。如果设置过大，会占用内存空间.
        props.put("linger.ms", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("retries", 0); // 重试次数
        //props.setProperty("security.protocol", "SASL_PLAINTEXT");
        // props.setProperty("sasl.mechanism", "SCRAM-SHA-512");
        // props.setProperty("sasl.jaas.config", "org.apache.kafka.common.security.scram.ScramLoginModule required username=" + PropUtils.KAFKA_SECURITY_USERNAME + "  password=" + PropUtils.KAFKA_SECURITY_PASSWORD + ";");
        //logger.info("org.apache.kafka.common.security.scram.ScramLoginModule required username=" + PropUtils.KAFKA_SECURITY_USERNAME + "  password=" + PropUtils.KAFKA_SECURITY_PASSWORD + ";");
        producer = new KafkaProducer<>(props);
        INSTANCE = new DataProducer1();
    }

    public static DataProducer1 getInstance() {
        return INSTANCE;
    }

    public void sendData(String topic, String data) {
        logger.info("----producer = " + producer);
        logger.info("----topic = " + topic);
        logger.info("----data = " + data);
        logger.info("---producer.send start---");
        producer.send(new ProducerRecord<>(topic, data));
        System.out.println("===");
        //producer.flush();
        System.out.println("===");
        logger.info("---producer.send end---");
    }
}
