package com.haozhuo.flink.common

import java.sql.Connection

/**
 * Created by root on 7/2/18.
 */
object JdbcConfig {
 // private val originDataDB: String = Props.get("mysql.connection.url.origin_data")
  private val dataetlDB: String = Props.get("mysql.connection.url")
  private val password: String = Props.get("mysql.connection.password")
  private val user: String = Props.get("mysql.connection.user")
  //private lazy val originData: DataSource = new DataSource(originDataDB, user, password)
  private lazy val dataetl: DataSource = new DataSource(dataetlDB, user, password)

//  def getOriginDataConnection: Connection = {
//    return originData.getConnection
//  }

  def getDataetlConnection: Connection = {
    return dataetl.getConnection
  }

//  def newDataetl() = {
//    new DataSource(dataetlDB, user, password)
//  }
}
