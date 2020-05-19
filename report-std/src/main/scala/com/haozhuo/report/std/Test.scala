import java.util.Properties
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.util.serialization.JSONKeyValueDeserializationSchema

object Test {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    //kafka参数
    val properties: Properties = new Properties()
    properties.setProperty("bootstrap.servers", "docker:9092")
    properties.setProperty("group.id", "flink_test")
    properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    properties.setProperty("auto.offset.reset", "latest")
    import scala.collection.JavaConverters._
    val topics = List[String]("con1","con2").asJava
    val jsonDstream = env.addSource(new FlinkKafkaConsumer010(topics, new JSONKeyValueDeserializationSchema(true), properties))
    val result = jsonDstream.map(obj => {
      val name = obj.get("value").get("friend").get("name")
      val age = obj.get("value").get("age")
      val offset = obj.get("metadata").get("offset")
      val topic = obj.get("metadata").get("topic")
      val partition = obj.get("metadata").get("partition")
      (name, age, s"消费的主题是：$topic,分区是：$partition,当前偏移量是：$offset")
    })
    result.print()
    env.execute()

  }
}

