package cn.huanyu.kafka;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;


public class ProducerDemo {

	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("metadata.broker.list", "192.168.73.128:9092,192.168.73.128:9092,192.168.73.128:9092");
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		/*生产者*/
		props.put("buffer.memory",33554432); // 缓存大小
		props.put("batch.size",1000); // producer试图批量处理消息记录。目的是减少请求次数，改善客户端和服务端之间的性能。
		// 这个配置是控制批量处理消息的字节数。如果设置为0，则禁用批处理。如果设置过大，会占用内存空间.
		props.put("linger.ms",1);
		props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
		props.put("retries",0); // 重试次数

		/**/
		ProducerConfig config = new ProducerConfig(props);
		Producer<String, String> producer = new Producer<String, String>(config);
		for (int i = 1001; i <= 1100; i++)
			producer.send(new KeyedMessage<String, String>("huanyu", "发消息测试" + i));
	}
}