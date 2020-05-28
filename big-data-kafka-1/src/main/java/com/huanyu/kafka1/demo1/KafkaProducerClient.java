package com.huanyu.kafka1.demo1;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaProducerClient {
    public static void main(String[] args) {
        //1、准备配置文件 参考:ProducerConfig.java
        Properties props = new Properties();
        // kakfa 服务列表
        props.put("bootstrap.servers", "hadoop1:9092,hadoop2:9092,hadoop3:9092");
        /**
         * 当生产者将ack设置为“全部”（或“-1”）时，min.insync.replicas指定必须确认写入被认为成功的最小副本数。
         * 如果这个最小值不能满足，那么生产者将会引发一个异常（NotEnoughReplicas或NotEnoughReplicasAfterAppend）。
         * 当一起使用时，min.insync.replicas和acks允许您执行更大的耐久性保证。
         * 一个典型的情况是创建一个复制因子为3的主题，将min.insync.replicas设置为2，并使用“全部”选项来产生。
         * 这将确保生产者如果大多数副本没有收到写入引发异常。
         */
        props.put("acks", "all");
        /**
         * 设置一个大于零的值,将导致客户端重新发送任何失败的记录
         */
        props.put("retries", 0);
        /**
         * 只要有多个记录被发送到同一个分区，生产者就会尝试将记录一起分成更少的请求。
         * 这有助于客户端和服务器的性能。该配置以字节为单位控制默认的批量大小。
         */
        props.put("batch.size", 16384);
        /**
         * 在某些情况下，即使在中等负载下，客户端也可能希望减少请求的数量。
         * 这个设置通过添加少量的人工延迟来实现这一点，即不是立即发出一个记录，
         * 而是等待达到给定延迟的记录，以允许发送其他记录，以便发送可以一起批量发送
         */
        props.put("linger.ms", 1);
        /**
         * 生产者可用于缓冲等待发送到服务器的记录的总字节数。
         * 如果记录的发送速度比发送给服务器的速度快，那么生产者将会阻塞，max.block.ms之后会抛出异常。
         * 这个设置应该大致对应于生产者将使用的总内存，但不是硬性限制，
         * 因为不是所有生产者使用的内存都用于缓冲。
         * 一些额外的内存将被用于压缩（如果压缩被启用）以及用于维护正在进行的请求。
         */
        props.put("buffer.memory", 33554432);
        // key的序列化类型
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // value的序列化类型
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //2、创建KafkaProducer
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer(props);
        for (int i = 0; i < 100; i++) {
            //3、发送数据
            kafkaProducer.send(new ProducerRecord("TEST_JAVA", "key"+i, "value" + i));
        }

        kafkaProducer.flush();
        kafkaProducer.close();
    }
}