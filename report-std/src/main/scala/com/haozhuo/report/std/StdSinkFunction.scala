package com.haozhuo.report.std

import com.haozhuo.report.bean.Report
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction

/**
  * Created by DELL on 2020/5/20 23:53
  */
class StdSinkFunction extends RichSinkFunction[Report]{
  override def invoke(value: Report): Unit = {
    println(value.obj.age)
    println(new ObjectMapper().writeValueAsString(value.obj))
//    value.obj.reportContent.checkItems.map(x=>
//      {
//        x.checkResults.map(y=>{
//          println("stdItemName："+x.stdCheckItemName)
//          println("itemName："+x.checkItemName+"=="+"indexName："+y.checkIndexName)
//        }
//        )
//      }
//    )
  }
}
