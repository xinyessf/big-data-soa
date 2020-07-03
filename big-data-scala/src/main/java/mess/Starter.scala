package mess

import org.apache.spark.streaming.kafka.KafkaUtils

object Starter {

  def main(args: Array[String]): Unit = {
    import org.apache.kafka.clients.producer.ProducerConfig

    val ssc: StreamingContext = {
      val sparkConf = new SparkConf().setAppName("spark-streaming-kafka-example").setMaster("local[2]")
      new StreamingContext(sparkConf, Seconds(5))
    }

    ssc.checkpoint("checkpoint-directory")

    // 初始化KafkaSink,并广播
    val kafkaProducer: Broadcast[KafkaSink[String, String]] = {
      val kafkaProducerConfig: Map[String, Object] = getKafkaProducerParams()
      if (logger.isInfoEnabled){
        logger.info("kafka producer init done!")
      }
      ssc.sparkContext.broadcast(KafkaSink[String, String](kafkaProducerConfig))
    }
    def getKafkaProducerParams(): Map[String, Object] = {
      Map[String, Object](
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG -> properties.getProperty("kafka1.bootstrap.servers"),
        ProducerConfig.ACKS_CONFIG -> properties.getProperty("kafka1.acks"),
        ProducerConfig.RETRIES_CONFIG -> properties.getProperty("kafka1.retries"),
        ProducerConfig.BATCH_SIZE_CONFIG -> properties.getProperty("kafka1.batch.size"),
        ProducerConfig.LINGER_MS_CONFIG -> properties.getProperty("kafka1.linger.ms"),
        ProducerConfig.BUFFER_MEMORY_CONFIG -> properties.getProperty("kafka1.buffer.memory"),
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG -> classOf[StringSerializer],
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG -> classOf[StringSerializer]
      )
    }

  }

}
