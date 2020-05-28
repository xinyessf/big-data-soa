package com.huanyu.kafka1.demo1;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class KafkaConsumerClient {
    public static void main(String[] args) {
        //1、准备配置文件 参考：ConsumerConfig.scala
        Properties props = new Properties();
        // kakfa 服务列表
        props.put("bootstrap.servers", "hadoop1:9092,hadoop2:9092,hadoop3:9092");
        //一个字符串用来指示一组consumer所在的组
        props.put("group.id", "test");
        // 如果true,consumer定期地往zookeeper写入每个分区的offset
        props.put("enable.auto.commit", "true");
        // 往zookeeper上写offset的频率
        props.put("auto.commit.interval.ms", "1000");
        // key的反序列化类型
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // value的反序列化类型
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //2、创建KafkaConsumer
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer(props);

        // 3、订阅数据，这里的topic可以是多个
        kafkaConsumer.subscribe(Arrays.asList("TEST_JAVA"));

        // 4、获取数据
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("topic = %s,offset = %d, key = %s, value = %s%n",record.topic(), record.offset(), record.key(), record.value());
            }
        }
    }
}