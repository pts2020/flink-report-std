package com.haozhuo.report.bean

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonProperty

import scala.beans.BeanProperty

/**
  * Created by DELL on 2020/5/18 10:03
  */
class GeneralSummary1 extends Serializable{
  @BeanProperty
  @JsonProperty("fw") var fw: String = ""

  @BeanProperty
  @JsonProperty("isPrivacy") var isPrivacy: String = ""

  @BeanProperty
  @JsonProperty("result") var result: String=""

  @BeanProperty
  @JsonProperty("reviewAdvice") var reviewAdvice: String = ""

  @BeanProperty
  @JsonProperty("summaryAdvice") var summaryAdvice: String = ""

  @BeanProperty
  @JsonProperty("summaryDescription") var summaryDescription: String=""

  @BeanProperty
  @JsonProperty("summaryMedicalExplanation") var summaryMedicalExplanation: String = ""

  @BeanProperty
  @JsonProperty("summaryName") var summaryName: String = ""

  @BeanProperty
  @JsonProperty("summaryReasonResult") var summaryReasonResult: String=""

  @BeanProperty
  @JsonProperty("body") var body: String = ""

  @BeanProperty
  @JsonProperty("checkMode") var checkMode: String = ""

  @BeanProperty
  @JsonProperty("abnormalLabel") var abnormalLabel: String=""

  @BeanProperty
  @JsonProperty("stdSummaryName") var stdSummaryName: String=""


  override def toString = s"GeneralSummary1($fw, $isPrivacy, $result, $reviewAdvice, $summaryAdvice, $summaryDescription, $summaryMedicalExplanation, $summaryName, $summaryReasonResult, $body, $checkMode, $abnormalLabel, $stdSummaryName)"
}
