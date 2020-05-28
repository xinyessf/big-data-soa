package cn.huanyu.kafka.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConsumerDemo {
    public static void main(String[] args) throws Exception{
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "hmaster:9092");
        properties.put("group.id", "group-1");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("auto.offset.reset", "earliest");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        //kafkaConsumer.subscribe(Arrays.asList("test"));
        kafkaConsumer.subscribe("hello");
        while (true) {
            Map<String, ConsumerRecords<String, String>> records = kafkaConsumer.poll(100);
            for (String key : records.keySet()) {
                System.out.println(key);
                System.out.println(records.get(key));


            }
        }

    }
}

