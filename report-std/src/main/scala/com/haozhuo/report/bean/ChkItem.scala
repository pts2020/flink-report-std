package com.haozhuo.report.bean

import java.util

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonProperty

import scala.beans.BeanProperty

/**
  * Created by DELL on 2020/5/18 10:12
  */
class ChkItem extends Serializable {
  @BeanProperty
  @JsonProperty("checkItemName") var checkItemName: String = ""

  @BeanProperty
  @JsonProperty("stdCheckItemName") var stdCheckItemName: String = ""

  @BeanProperty
  @JsonProperty("checkResults") var checkResults: Array[CheckResult]=null

  @BeanProperty
  @JsonProperty("checkStateId") var checkStateId: String = ""

  @BeanProperty
  @JsonProperty("checkUserName") var checkUserName: String = ""

  @BeanProperty
  @JsonProperty("departmentName") var departmentName: String = ""

  @BeanProperty
  @JsonProperty("salePrice") var salePrice: String = ""


  override def toString = s"ChkItem($checkItemName, $stdCheckItemName, $checkResults, $checkStateId, $checkUserName, $departmentName, $salePrice)"
}
