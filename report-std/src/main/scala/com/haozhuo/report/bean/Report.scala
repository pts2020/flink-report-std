package com.haozhuo.report.bean


import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonProperty

import scala.beans.BeanProperty

class Report extends Serializable {

  @BeanProperty
  @JsonProperty("eventType") var eventType: String = ""

  @BeanProperty
  @JsonProperty("table") var table: String = ""

  @BeanProperty
  @JsonProperty("obj") var obj: ReportObj=null


  //override def toString = s"Report($healthReportId, $name, $userId, $idCardNoMd5, $birthday, $sex, $checkUnitCode, $checkUnitName, $checkDate, $dwdm, $dwmc, $userLoadTime, $reportCreateTime, $lastUpdateTime, $updateTime, $rowkeyPrefix)"
  override def toString = s"Report($eventType, $table, $obj)"
}
