package com.haozhuo.report.std

import com.haozhuo.report.bean.Report
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction

/**
  * Created by DELL on 2020/5/16 23:16
  */
class StdFuc extends RichSinkFunction[Report]{

  override def invoke(value: Report): Unit = {
    println(value)
  }
}
