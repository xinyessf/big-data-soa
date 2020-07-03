package com.huanyu.spark.msb.learn.rddv1

import java.util.Properties

import com.google.gson.Gson
import com.huanyu.spark.msb.learn.rddv1.filter.KafkaSink
import org.apache.kafka.common.serialization.{Serializer, StringSerializer}
import org.apache.spark.sql.{Dataset, Row, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable

object Starter {
  //private[this] val logger = Logger(this.getClass)
  val properties = new Properties()
  val inputStream = Starter.getClass.getClassLoader.getResourceAsStream("messlocal.properties")
  properties.load(inputStream);
  val DAY = properties.get("manual.task.day")
  val BROKER_LIST=properties.get("kafka.broker.list")

  val props = new Properties
  props.put("metadata.broker.list", BROKER_LIST)
  //props.put("metadata.broker.list", "192.168.73.128:9092,192.168.73.129:9092,192.168.73.130:9092")
  props.put("serializer.class", "kafka.serializer.StringEncoder")
  /*生产者*/
  props.put("buffer.memory", "33554432"); // 缓存大小
  props.put("batch.size", "1000"); // producer试图批量处理消息记录。目的是减少请求次数，改善客户端和服务端之间的性能。
  // 这个配置是控制批量处理消息的字节数。如果设置为0，则禁用批处理。如果设置过大，会占用内存空间.
  props.put("linger.ms", "1")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("retries", "0"); // 重试次数


  def main(args: Array[String]): Unit = {
    import java.util.concurrent.Future
    import org.apache.kafka.clients.producer.RecordMetadata


    val spark = SparkSession.builder()
      .appName("HiveOnSpark")
      .master("local[*]")
      .config("spark.kryoserializer.buffer", "1024m")
      .config("spark.kryoserializer.buffer.max", "2046m")
      .enableHiveSupport() //启用spark对hive的支持(可以兼容hive的语法了)
      .getOrCreate()
    new SparkConf().setAppName("spark-streaming-kafka-example").setMaster("local[*]")
    val result4: Dataset[Row] = spark.sql("select * from original.t_ods_cmcc_block_sm where day='20200521' limit 100")
    result4.foreachPartition(partitionOfRecords => {
      val metadata = partitionOfRecords.map(record => {
        val c_usernum = record.getString(0)
        println(c_usernum)
        val c_relatenum = record.getString(1)
        val c_time = record.getString(2)
        val c_content = record.getString(3)
        val sms = new CmccSms(c_usernum, c_relatenum, c_time, c_content, 1)
        kafkaProducer.value.send("huanyu", new Gson().toJson(sms))
      }).toStream
      metadata.foreach(data => {
        data.get()
      })
    })
  }

  class CmccSms(var c_usernum: String, var c_relatenum: String, var c_time: String,
                var c_content: String, var sms_type: Int) extends Serializable

  import org.apache.kafka.clients.producer.ProducerConfig

  val ssc = {
    val sparkConf = new SparkConf().setAppName("spark-streaming-kafka-example").setMaster("local[*]")
    new StreamingContext(sparkConf, Seconds(5))
  }

  ssc.checkpoint("checkpoint-directory")

  // 初始化KafkaSink,并广播
  val kafkaProducer = {
    val kafkaProducerConfig = getKafkaProducerParams()
    /*  if (logger.isInfoEnabled){
        logger.info("kafka producer init done!")
      }*/
    ssc.sparkContext.broadcast(KafkaSink[String, String](kafkaProducerConfig))
  }

  def getKafkaProducerParams() = {
    Map[String, Object](
      ProducerConfig.BOOTSTRAP_SERVERS_CONFIG -> props.getProperty("metadata.broker.list"),
      // ProducerConfig.ACKS_CONFIG -> props.getProperty("kafka1.acks"),
      ProducerConfig.RETRIES_CONFIG -> props.getProperty("retries"),
      ProducerConfig.BATCH_SIZE_CONFIG -> props.getProperty("batch.size"),
      ProducerConfig.LINGER_MS_CONFIG -> props.getProperty("linger.ms"),
      ProducerConfig.BUFFER_MEMORY_CONFIG -> props.getProperty("buffer.memory"),
      ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG -> classOf[StringSerializer],
      ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG -> classOf[StringSerializer]
    )
  }
}
