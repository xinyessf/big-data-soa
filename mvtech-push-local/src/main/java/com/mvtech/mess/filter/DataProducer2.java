package com.mvtech.mess.filter;

import com.mvtech.mess.util.PropUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Properties;

/**
 * @author 黎政
 */
public class DataProducer2 implements Serializable {
    private static Logger logger = Logger.getLogger(DataProducer1.class);

    private static DataProducer2 INSTANCE;

    private static Producer<String, String> producer;

    private DataProducer2() {
    }


    static {
        Properties props = new Properties();
        props.put("bootstrap.servers", PropUtils.KAFKA_LIST);
        props.put("request.timeout.ms", 10000);
        props.put("retries", 3);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<>(props);
        INSTANCE = new DataProducer2();
    }

    public static DataProducer2 getInstance() {
        return INSTANCE;
    }

    public void sendData(String topic, String data) {
        logger.info("----producer = " + producer);
        logger.info("----topic = " + topic);
        logger.info("----data = " + data);
        logger.info("---producer.send start---");
        producer.send(new ProducerRecord<>(topic, data));
        //producer.flush();
        logger.info("---producer.send end---");
    }
}
