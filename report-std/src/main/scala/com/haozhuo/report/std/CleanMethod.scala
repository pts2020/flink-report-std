package com.haozhuo.report.std

import com.haozhuo.report.bean.CheckResult
import com.haozhuo.report.mysql.MysqlMethods
import org.slf4j.{Logger, LoggerFactory}


/**
  * Created by DELL on 2020/5/19 15:36
  */
object CleanMethod {
  private val logger: Logger = LoggerFactory.getLogger(classOf[CleanMethod])

  private val not_needed_symbols_replace_pattern =  "[\\s+`~!@#$%^&*()=|{}'\\[\\\\].。<>/?！￥…（）—\\-【】‘;；:：”“’,，、？\\]"
  //阴阳性结果匹配模式
  private val positive_or_negative_match_pattern1 = "^[+-]+(/(hp|HP))?$"
  private val positive_or_negative_match_pattern_2 = """^[\d-]+/(hp|HP)$""".r.toString()
  private val positive_or_negative_match_pattern_3 = """^(hp|HP|弱|强)?[阴阳]性""".r.toString()

  //数值型指标项检查结果
  private val numeric_chk_item_value_pattern = """^[0-9]+[0-9Ee.]+([↓↑]?(\([\u4e00-\u9fa5,*].*?\))?|次/分|(mmol|umol|CELL|g)/u?L)?$""".r
  // 非数字字符
  private val non_decimal = """[^\d.]+"""

  def clean_abn_name(abn_name:String)= {
    abn_name.replaceAll(not_needed_symbols_replace_pattern,"")
    .replaceAll("Ⅰ", "1")
    .replaceAll("Ⅱ", "2")
    .replaceAll("Ⅲ", "3")
    .toLowerCase()
  }


  //checkItemName，checkIndexName 用以下函数对特殊字符处理
  def index_name_replace(index_name1:String) = {
    var index_name = index_name1
    index_name = index_name.replace(" ", "").toUpperCase
    index_name = index_name.replace("*", "")
    index_name = index_name.replace("【", "[").replace("】", "]")
    index_name = index_name.replace("（", "(").replace("）", ")")
    index_name = index_name.replace("[", "(").replace("]", ")")
    index_name = index_name.replace("—", "-").replace("－", "-").replace("--", "-")
    index_name = index_name.replace("_", "-")
    index_name = index_name.replace("★", "")
    index_name = index_name.replace("Ⅰ", "I")
    index_name = index_name.replace("Ⅱ", "II")

    if (index_name.charAt(0).toString=="-") {
      index_name = index_name.substring(1, index_name.length - 1)
    }
    if (index_name.charAt(index_name.length-1).toString==(".")){
      index_name =index_name.substring(0,index_name.length-2)
    }
    index_name
  }

  /**
    * 根据结果值判断指标类型
    * @param checkResult
    * @return 0-数值型;1-描述型;2-阴阳性
    */
  def get_chk_item_data_type(checkResult: CheckResult)={
    var flag:Integer = 0
    if(checkResult.resultValue.matches(positive_or_negative_match_pattern1)|| checkResult.resultValue.matches(positive_or_negative_match_pattern_2)
    || checkResult.resultValue.matches(positive_or_negative_match_pattern_3)){
      flag = 2
    }else if(checkResult.resultFlagId == 4){
      if (checkResult.resultValue.length> 20) {flag = 1}
      else {flag =2}
    }else if(checkResult.resultFlagId==2 || checkResult.resultFlagId==3 || is_numberic_data(checkResult.resultValue) ==1){
      flag =0
    }else{
      flag =1
    }
    flag
  }

  /**
    * 判断是否为数值型数据
    * @param value
    * @return
    */
  def is_numberic_data(value:String)= {
    var flag:Integer = 0
    if (numeric_chk_item_value_pattern.findAllIn(value).length!=0) {
      val vDouble = value.replaceAll(non_decimal,"")
      try{
        vDouble.toDouble
        flag=1
      }catch {
        case ex: Exception =>
          logger.info("value:{}",value)
          flag = 2
      }
    }else{
      flag = 3
    }
    flag
  }

  /**
    * 判断是否为纯数字 1 是
    * @param n
    * @return
    */
  def is_number(n:String)= {
    var flag:Integer = 2
    try{
      n.toDouble
      flag=1
    }catch {
      case ex: Exception =>
        logger.info("value:{}",n)
        flag = 0
    }
    flag
  }
  /** 范围清洗,flag= 0 表示清洗成功
    *
    * @param text_ref1
    * @return (text_ref,text_ref_lower,text_ref_upper,flag)
    */
  def text_ref_replace(text_ref1:String)= {
    var flag:Integer = 0
    var text_ref_lower:String = ""
    var text_ref_upper:String = ""
    var text_ref = text_ref1.replace(" ","")
    text_ref = text_ref.replace("-<=", "0.00-")
    text_ref = text_ref.replace("-＜=", "0.00-")
    text_ref = text_ref.replace("-<", "0.00-")
    text_ref = text_ref.replace("-＜", "0.00-")

    text_ref = text_ref.replace("<=", "0.00-")
    text_ref = text_ref.replace("＜=", "0.00-")
    text_ref = text_ref.replace("<", "0.00-")
    text_ref = text_ref.replace("＜", "0.00-")
    text_ref = text_ref.replace("〈", "0.00-")
    text_ref = text_ref.replace("-≤", "0.00-")
    text_ref = text_ref.replace("〈", "0.00-")
    if(text_ref.startsWith("≤")){
      text_ref = text_ref.replace("≤","0.00-")
    }

    if ( text_ref.contains("〉") ){
      text_ref = text_ref.replace("〉", "")
      text_ref = text_ref + "-Inf"
    }
    if ( text_ref.contains("->") ){
      text_ref = text_ref.replace("〉", "")
      text_ref = text_ref + "-Inf"
    }
    if ( text_ref.contains(">")) {
      text_ref = text_ref.replace(">", "")
      text_ref = text_ref + "-Inf"
    }
    if ( text_ref.contains("﹥"))
      text_ref = text_ref.replace("﹥", "")
      text_ref = text_ref + "-Inf"
    if(text_ref.contains("＞")) {
      text_ref = text_ref.replace("＞", "")
      text_ref = text_ref + "-Inf"
    }
    if(text_ref.contains("》")) {
      text_ref = text_ref.replace("》", "")
      text_ref = text_ref + "-Inf"
    }
    if ( text_ref.contains("--")) text_ref = text_ref.replace("--", "-")
    if ( text_ref.contains("～")) text_ref = text_ref.replace("～", "-")
    if ( text_ref.contains("~")) text_ref = text_ref.replace("~", "-")
    if (text_ref.contains("-")){
    text_ref_lower = text_ref.split("-")(0)
    text_ref_upper = text_ref.split("-")(1)
      if (is_number(text_ref_lower)==1 && is_number(text_ref_upper)==1){
        text_ref_lower = text_ref_lower.toDouble.toString
        if(text_ref_upper.equals("Inf")){
          text_ref_upper = text_ref_upper
        }else{
          text_ref_upper = text_ref_upper.toDouble.toString
        }
        text_ref = text_ref_lower + "-" + text_ref_upper
      }
      else
      {
        flag = 1
        text_ref = text_ref1 + "(数据错误)"
      }
    }
    else{
      flag =2
      text_ref = text_ref1 + "(数据错误)"
    }

    (text_ref,text_ref_lower,text_ref_upper,flag)
  }

  /**
    * 结果值清理
    * @param result_value
    * @return
    */
  def result_value_replace(result_value:String)= {
    var value:String = result_value
    value = value.replaceAll(non_decimal,"")
    if (value.endsWith(".")){
      value = value.dropRight(1)
    }
    try {
      value = value.toDouble.toString
    }catch {
      case ex:Exception=>
        logger.info("不能转成纯数字："+ex)
      value = value
    }
    value
  }

class CleanMethod{}

  def main(args: Array[String]): Unit = {
    val name = clean_abn_name(" -糖尿病Ⅱ")
    val checkResult = new CheckResult
    checkResult.setResultValue("2.82")
    checkResult.setResultFlagId(1)
    val result = get_chk_item_data_type(checkResult)
    println(result)
    //范围清洗
    val ref = text_ref_replace("10  ～15 ")
    println(ref)
    val value = result_value_replace("45.")
    println(value)
  }
}
