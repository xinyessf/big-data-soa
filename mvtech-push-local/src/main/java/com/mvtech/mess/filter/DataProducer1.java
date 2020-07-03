package com.mvtech.mess.filter;

import com.mvtech.mess.util.PropUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
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
        // props.put("bootstrap.servers", PropUtils.KAFKA_LIST);
        props.put("acks", "all");
        props.put("request.timeout.ms", 10000);
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //props.setProperty("security.protocol", "SASL_PLAINTEXT");
        //props.setProperty("sasl.mechanism", "SCRAM-SHA-512");
        //props.setProperty("sasl.jaas.config", "org.apache.kafka.common.security.scram.ScramLoginModule required username=" + PropUtils.KAFKA_SECURITY_USERNAME + "  password=" + PropUtils.KAFKA_SECURITY_PASSWORD + ";");
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
