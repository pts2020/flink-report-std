package com.haozhuo.report.std

import com.haozhuo.report.mysql.MysqlMethods
import com.haozhuo.util.StrUtils


/**
  * Created by DELL on 2020/5/18 17:21
  */
object mysqlTest {
  def main(args: Array[String]): Unit = {
    val da = StrUtils.strToDate("2020-03-10")
    println(da)

  }

}
