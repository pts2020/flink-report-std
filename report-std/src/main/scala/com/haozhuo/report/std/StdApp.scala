package com.haozhuo.report.std

import com.haozhuo.flink.common.KafkaBaseInfo
import com.haozhuo.flink.common.ScalaUtils.getClass
import com.haozhuo.report.bean.{Constants, Report}
import com.haozhuo.report.mysql.MysqlMethods
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable



/**
  * Created by DELL on 2020/5/15 16:07
  */
object StdApp {
  def main(args: Array[String]): Unit = {

    //private val logger: Logger = LoggerFactory.getLogger(getClass)

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.enableCheckpointing(30000)
    env.addSource(KafkaBaseInfo.getConsumer(Constants.KAFKA_REPORT_STD, groupId = "report-std"))
      .map (
        x => {
          try {
            val report =new ObjectMapper().readValue(x, classOf[Report])
            //val obj = new ObjectMapper().writeValueAsString(report.obj)
            new ObjectMapper().readValue(x, classOf[Report])
            //println(report.obj.reportContent)
          } catch {
            case ex: Exception =>
              //logger.error("报告json转对象失败:{}" + ex.getMessage)
              new Report()
          }
        }).map(new StdFuc())
      .addSink(new StdSinkFunction())
    env.execute("ReportStd")
  }


}
