package com.haozhuo.flink.common

import java.util.Properties
import org.slf4j.LoggerFactory
/**
  * Created by zbj on 15/05/20.
  */
object Props extends Serializable {
  val logger = LoggerFactory.getLogger(getClass())
  private val prop = new Properties()

  {
    prop.load(ScalaUtils.readFile("E:\\flink-report-std\\report-std\\src\\main\\resources\\config.properties"))
     //prop.load(ScalaUtils.readFile("hdfs://datagcluster/flink/flink-data-etl/article-rcmd/config.properties"))
    // prop.load(ScalaUtils.readFile("hdfs://namenode:9000/flink/flink-data-etl/article-rcmd/config.properties"))
  }


  /**
   * 根据配置文件中的的属性名获取属性值
   */
  def get(propertyName: String): String = {
    var value = prop.getProperty(propertyName)
    if (value == null) {
      logger.warn("config.properties文件中沒有这个属性:{}" , propertyName)
      value = ""
    }
    new String(value.getBytes("ISO-8859-1"), "utf-8")
  }

  def get(propertyName: String, defaultValue: String): String = {
    var value = prop.getProperty(propertyName)
    if (value == null) {
      value = defaultValue
    }
    new String(value.getBytes("ISO-8859-1"), "utf-8")
  }
}
