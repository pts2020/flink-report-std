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
  val sugMap = querySugMap()

  def queryItemIndexNameMysql()={
    logger.info("加载标准化映射表：index_map")
    var index_name: String = ""
    var item_name: String = ""
    var index_type: String = ""
    var std_item_name: String = ""
    var std_index_name: String = ""
    var std_type: String = ""
    val indexMap: mutable.HashMap[String,Array[String]] = mutable.HashMap()
    //val indexMap1: mutable.HashMap[String,String] = mutable.HashMap()
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
        var array = new Array[String](4)
        index_name = resultSet.getString("index_name").trim
        item_name = resultSet.getString("item_name").trim
        index_type = resultSet.getString("index_type").trim
        std_index_name = resultSet.getString("std_index_name").trim
        std_item_name = resultSet.getString("std_item_name").trim
        std_type = resultSet.getString("std_type").trim
         array(0) = std_item_name
        array(1) = std_index_name
        array(2) = index_type
        array(3) = std_type
        indexMap.put(item_name+index_name,array)
      }

    } catch {
      case e: Exception =>
        logger.error("Error", e)
    } finally {
      DataSource.close(preparedStmt, conn)
    }
   indexMap
  }
  def querySugMap()={
    logger.info("加载标准化映射表：sug_map")
    var sug_name: String = ""
    var std_sug_name: String = ""
    var body: String = ""
    var check_mode: String = ""
    var abnormal_label: String = ""
    val sugMap: mutable.HashMap[String,Array[String]] = mutable.HashMap()
    //val indexMap1: mutable.HashMap[String,String] = mutable.HashMap()
    var conn: Connection = null
    var preparedStmt: PreparedStatement = null
    var i =1
    try {
      conn = JdbcConfig.getDataetlConnection
      //val resultSet2 = conn.createStatement().executeQuery("select * from check_index_name_map ;")
      preparedStmt = conn.prepareStatement("select sug_name,std_sug_name,body,check_mode,abnormal_label from check_sug_name_map ")
      //preparedStmt = conn.prepareStatement("select * from check_index_name_map ;")
      val resultSet = preparedStmt.executeQuery()
      while (resultSet.next()) {
        var array = new Array[String](4)
        sug_name = resultSet.getString("sug_name").trim
        std_sug_name = resultSet.getString("std_sug_name").trim
        body = resultSet.getString("body").trim
        check_mode = resultSet.getString("check_mode").trim
        abnormal_label = resultSet.getString("abnormal_label").trim
        array(0) = std_sug_name
        array(1) = body
        array(2) = check_mode
        array(3) = abnormal_label
        sugMap.put(sug_name,array)
      }

    } catch {
      case e: Exception =>
        logger.error("Error", e)
    } finally {
      DataSource.close(preparedStmt, conn)
    }
    sugMap
  }

}

class MysqlMethods {}
