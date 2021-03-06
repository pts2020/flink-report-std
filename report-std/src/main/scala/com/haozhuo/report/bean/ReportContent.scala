package com.haozhuo.report.bean

import java.util

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonProperty

import scala.beans.BeanProperty

/**
  * Created by DELL on 2020/5/18 9:57
  */
class ReportContent extends Serializable{

  @BeanProperty
  @JsonProperty("checkItems") var checkItems: util.ArrayList[ChkItem] = null

  @BeanProperty
  @JsonProperty("generalSummarys") var generalSummarys: util.ArrayList[GeneralSummary1] = null

  @BeanProperty
  @JsonProperty("generalSummarys2") var generalSummarys2: util.ArrayList[String]=null

  override def toString = s"ReportContent($checkItems, $generalSummarys, $generalSummarys2)"
}
