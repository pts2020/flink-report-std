package com.haozhuo.report.bean

import com.haozhuo.flink.common.Props

/**
  * Created by zbj on 2020/5/15 15:23
  */
object Constants {

  val KAFKA_REPORT_STD = Props.get("kafka.topic.report-std","report")
  val KAFKA_REPORT_SINK = Props.get("kafka.topic.report-sink","report-sink")

}
