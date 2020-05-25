package com.haozhuo.report.std

import com.haozhuo.flink.common.KafkaBaseInfo
import com.haozhuo.report.bean.{Constants, Report}
import com.haozhuo.report.mysql.MysqlMethods
import org.apache.flink.configuration.Configuration
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction
import org.apache.flink.streaming.api.functions.sink.SinkFunction.Context
import org.slf4j.{Logger, LoggerFactory}

/**
  * Created by DELL on 2020/5/20 23:53
  */
class StdSinkFunction extends RichSinkFunction[Report] with KafkaBaseInfo{
  private val logger: Logger = LoggerFactory.getLogger(classOf[StdSinkFunction])

  val topic = Constants.KAFKA_REPORT_SINK

  override def open(parameters: Configuration): Unit = {
    initKafkaProducer("1003")
  }
  override def invoke(value: Report,context: Context[_]): Unit = {
    if(value !=null) {
      val reportContent: String = new ObjectMapper().writeValueAsString(value.obj)
      try {
        MysqlMethods.saveToMysql(value, reportContent)
      } catch {
        case e: Exception => logger.error("报告sinkmysql失败：{}", e)
      }
      try {
        sendMsg(topic,reportContent)
      } catch {
        case e: Exception => logger.error("报告sinkkafka失败：{}", e)
      }

    }
  }
}
