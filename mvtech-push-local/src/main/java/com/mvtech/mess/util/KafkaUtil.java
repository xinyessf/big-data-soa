package com.mvtech.mess.util;

import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

/**
 * @author
 * @version 1.0
 * @date
 */
public class KafkaUtil {

    public static Properties props = new Properties();

    static {
        props.put("bootstrap.servers", PropUtils.KAFKA_LIST);
        //props.put("acks", "all");
        /**
         * 设置一个大于零的值,将导致客户端重新发送任何失败的记录
         */
        //props.put("retries", 0);
        // key的序列化类型
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // value的序列化类型
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        /**
         * 权限相关部分，根据实际情况填写用户名、密码
         * 权限会限制用户对topic拥有消费/生产权限
         * username=\"test\"
         * password=\"111111\"
         */
        props.setProperty("security.protocol", "SASL_PLAINTEXT");
        props.setProperty("sasl.mechanism", "SCRAM-SHA-512");
        props.setProperty("sasl.jaas.config", "org.apache.kafka.common.security.scram.ScramLoginModule required username=" + PropUtils.KAFKA_SECURITY_USERNAME + "  password=" + PropUtils.KAFKA_SECURITY_PASSWORD + ";");
    }

    public static KafkaProducer<String, String> getProducer() {

        return new KafkaProducer(props);
    }

    public static void close(KafkaProducer<String, String> kafka) {
        try {
            if (kafka != null) {
                kafka.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
