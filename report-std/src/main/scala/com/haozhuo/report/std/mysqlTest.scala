package com.haozhuo.report.std

import com.haozhuo.report.mysql.MysqlMethods

/**
  * Created by DELL on 2020/5/18 17:21
  */
object mysqlTest {
  def main(args: Array[String]): Unit = {
    val a = "5"
    val b = "5.9"
    val c = "5.5555"

    println(a.toDouble)
    println(b.toDouble)
    println(c.toDouble)
    val d = """^[\d-]+/(hp|HP)$"""
    println(d)
    val indexMap = MysqlMethods.result
    val e = indexMap.get("身高体重身高")
    val f = MysqlMethods.sugMap.size
    println(f)
  }

}
