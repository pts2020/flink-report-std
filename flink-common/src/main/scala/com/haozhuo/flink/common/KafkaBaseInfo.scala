package com.haozhuo.flink.common

import java.util
import java.util.Properties

import jdk.nashorn.internal.ir.ObjectNode
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010
import org.apache.flink.api.common.serialization.{DeserializationSchema, SimpleStringSchema}
import org.apache.flink.streaming.util.serialization.{JSONKeyValueDeserializationSchema, KeyedDeserializationSchema}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

/**
  * Created by zbj on 15/05/20.
  */
object KafkaBaseInfo {
  def getConsumer(topicName: String, groupId: String,bootstrapServer: String = Props.get("kafka.bootstrap.server",defaultValue = "192.168.100.100:9092")) = {
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", bootstrapServer)
    properties.setProperty("group.id", groupId)
    new FlinkKafkaConsumer010(
      topicName,
      new SimpleStringSchema(),
      properties
    )
  }

  def getConsumerList(topics:util.List[String], groupId: String, bootstrapServer: String = Props.get("kafka.bootstrap.server",defaultValue = "192.168.100.100:9092"))={
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", bootstrapServer)
    properties.setProperty("group.id", groupId)
     new FlinkKafkaConsumer010(
      topics,
      new JSONKeyValueDeserializationSchema(true),
      properties)
  }
}

trait KafkaBaseInfo {
  private var producer: org.apache.kafka.clients.producer.Producer[String, String] = _;

  def initKafkaProducer(clientId: String = null) = {
    val props: Properties = new Properties()
    if (clientId != null) {
      props.put("client.id", clientId)
    }
    props.put("bootstrap.servers", Props.get("kafka.bootstrap.server"))
    props.put("acks", "all")
    props.put("retries", "1")
    props.put("batch.size", "16384")
    props.put("linger.ms", "100")
    props.put("buffer.memory", "33554432")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    producer = new KafkaProducer[String, String](props)
  }


  def sendMsg(topicName: String, msg: String): Unit = {
    /*
    If your producer is being used throughout the lifetime of your application,
    don't close it, and let it die once the application terminates. As said
    in the documentation, the producer is safe to used in a multi-threaded environment
    and hence you should re-use the same instance.
    */
    producer.send(new ProducerRecord[String, String](topicName, null, msg))
  }
}
