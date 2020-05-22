//package com.haozhuo.report.std
//
//import com.haozhuo.report.bean.{CheckResult, ChkItem, Report}
//import com.haozhuo.report.mysql.MysqlMethods
//import org.apache.flink.api.common.functions.RichMapFunction
//import org.apache.flink.shaded.zookeeper.org.apache.zookeeper.OpResult.CheckResult
//import org.slf4j.{Logger, LoggerFactory}
//
///**
//  * Created by DELL on 2020/5/21 0:03
//  */
//class StdFunction extends RichMapFunction[Report,Report]{
//  private val logger: Logger = LoggerFactory.getLogger(classOf[StdFunction])
//
//
//
//  override def map(report1: Report): Report = {
//    val report =report1
//    val checkItems =report.obj.reportContent.checkItems
//    for (i <- 0 until checkItems.size()){
//     val checkResults = checkItems.get(i).checkResults
//     val itemName = checkItems.get(i).checkItemName.trim
//     for(j<- 0 until checkResults.size()){
//       val indexName = checkResults.get(j).checkIndexName.trim
//       if (MysqlMethods.result.contains(itemName+indexName)) {
//         val stdCheckName =MysqlMethods.result.get(itemName+indexName).getOrElse().toString
//         report.obj.reportContent.checkItems.get(i).setStdCheckItemName(stdCheckName)
//         println("标准化名称："+report.obj.reportContent.checkItems.get(i).stdCheckItemName)
//       }
//     }
//   }
//
//
//    report
//
//}
//}
