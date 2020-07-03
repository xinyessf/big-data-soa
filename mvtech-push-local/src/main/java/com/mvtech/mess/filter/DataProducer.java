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
public class DataProducer {
    private static Logger logger = Logger.getLogger(DataProducer.class);

    private DataProducer() {
    }

    private static volatile DataProducer INSTANCE;

    private static Producer<String, String> producer;
    private static Properties props = new Properties();


    public static DataProducer getInstance() {
        if (INSTANCE == null) {
            synchronized (DataProducer.class) {
                if (INSTANCE == null) {
                    init();
                    producer = new KafkaProducer<>(props);
                    INSTANCE = new DataProducer();
                }
            }
        }
        return INSTANCE;
    }


    public static void init() {
        props = new Properties();
        props.put("bootstrap.servers", "10.10.113.21:9092,10.10.113.22:9092,10.10.113.23:9092,10.10.113.24:9092,10.10.113.25:9092,10.10.113.26:9092,10.10.113.27:9092,10.10.113.29:9092,10.10.113.30:9092");
       // props.put("bootstrap.servers", PropUtils.KAFKA_LIST);
        props.put("request.timeout.ms", 10000);
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.setProperty("security.protocol", "SASL_PLAINTEXT");
        props.setProperty("sasl.mechanism", "SCRAM-SHA-512");
        props.setProperty("sasl.jaas.config", "org.apache.kafka.common.security.scram.ScramLoginModule required username=" + PropUtils.KAFKA_SECURITY_USERNAME + "  password=" + PropUtils.KAFKA_SECURITY_PASSWORD + ";");
        logger.info("org.apache.kafka.common.security.scram.ScramLoginModule required username=" + PropUtils.KAFKA_SECURITY_USERNAME + "  password=" + PropUtils.KAFKA_SECURITY_PASSWORD + ";");

    }

    public void sendData(String topic, String data) {
        producer.send(new ProducerRecord<>(topic, data));
    }

    public static void main(String[] args) {
    }
}
