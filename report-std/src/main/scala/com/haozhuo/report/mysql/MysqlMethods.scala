package com.haozhuo.report.mysql

import java.sql.{Connection, PreparedStatement}
import java.util

import com.haozhuo.flink.common.{DataSource, JdbcConfig}
import org.apache.flink.table.api.scala.map
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable

/**
  * Created by Lucius on 12/21/18.
  */
object MysqlMethods {
  private val logger: Logger = LoggerFactory.getLogger(classOf[MysqlMethods])
  val result = queryItemIndexNameMysql()

  val itemMap: mutable.HashMap[String, String]=result._1
  val indexMap: mutable.HashMap[String,Array[String]]=result._2



  def queryItemIndexNameMysql()={
    var index_name: String = ""
    var item_name: String = ""
    var index_type: String = ""
    var std_item_name: String = ""
    var std_index_name: String = ""
    var std_type: String = ""
    val itemMap1: mutable.HashMap[String, String] = mutable.HashMap()
    val indexMap1: mutable.HashMap[String,Array[String]] = mutable.HashMap()
    var conn: Connection = null
    var preparedStmt: PreparedStatement = null
    var i =1
    try {
      conn = JdbcConfig.getDataetlConnection
      //val resultSet2 = conn.createStatement().executeQuery("select * from check_index_name_map ;")
      preparedStmt = conn.prepareStatement("select index_name,item_name,index_type,std_index_name,std_item_name,std_type from check_index_name_map ")
      //preparedStmt = conn.prepareStatement("select * from check_index_name_map ;")
      val resultSet = preparedStmt.executeQuery()
       while (resultSet.next()) {
        var array = new Array[String](3)
        index_name = resultSet.getString("index_name")
        item_name = resultSet.getString("item_name")
        index_type = resultSet.getString("index_type")
        std_index_name = resultSet.getString("std_index_name")
        std_item_name = resultSet.getString("std_item_name")
        std_type = resultSet.getString("std_type")
        itemMap1.put(item_name.trim,std_item_name)
        array(0) = std_index_name.trim
        array(1) = index_type.trim
        array(2) = std_type.trim
        indexMap1.put(item_name.trim+index_name.trim,array)
      }

    } catch {
      case e: Exception =>
        logger.error("Error", e)
    } finally {
      DataSource.close(preparedStmt, conn)
    }
   (itemMap1,indexMap1)
  }
}

class MysqlMethods {}
