package com.haozhuo.report.std

import com.haozhuo.report.mysql.MysqlMethods

/**
  * Created by DELL on 2020/5/18 17:21
  */
object mysqlTest {
  def main(args: Array[String]): Unit = {
    val map = MysqlMethods.itemMap
    val indexMap = MysqlMethods.indexMap
      println(map.size)
    println(indexMap.size)
  }

}
