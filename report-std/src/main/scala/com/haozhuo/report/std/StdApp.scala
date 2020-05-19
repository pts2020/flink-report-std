package com.haozhuo.report.std

import com.haozhuo.flink.common.KafkaBaseInfo
import com.haozhuo.flink.common.ScalaUtils.getClass
import com.haozhuo.report.bean.{Constants, Report}
import com.haozhuo.report.mysql.MysqlMethods
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.slf4j.LoggerFactory

/**
  * Created by DELL on 2020/5/15 16:07
  */
object StdApp {
  def main(args: Array[String]): Unit = {
    val logger = LoggerFactory.getLogger(getClass())
    val itemMap = MysqlMethods.itemMap
    logger.info("开始")
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.enableCheckpointing(30000)
    env.addSource(KafkaBaseInfo.getConsumer(Constants.KAFKA_REPORT_STD, groupId = "report-std"))
      .map {
        x =>
          try {
            var report = new ObjectMapper().readValue(x, classOf[Report])
            //val obj = new ObjectMapper().writeValueAsString(report.obj)
            //println(report)
            println("映射表："+itemMap.size)
            //println(report.obj.reportContent)
          } catch {
            case ex: Exception =>
              logger.error(ex.getMessage)
              new Report()
          }
      }
      .addSink(println(_))
    env.execute("ReportStd")
  }


}
