package com.haozhuo.report.bean

import com.haozhuo.flink.common.Props

/**
  * Created by zbj on 2020/5/15 15:23
  */
object Constants {

  val KAFKA_REPORT_STD = Props.get("kafka.topic.report-std","report")
  val KAFKA_NEWS_RCMD_REQUEST = Props.get("kafka.topic.news-rcmd-request","news-rcmd-request")

}
