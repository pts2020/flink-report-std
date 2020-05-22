package com.haozhuo.report.std

import com.haozhuo.report.bean.{CheckResult, ChkItem, Report}
import com.haozhuo.report.mysql.MysqlMethods
import org.apache.flink.api.common.functions.RichMapFunction
import org.slf4j.{Logger, LoggerFactory}


/**
  * Created by DELL on 2020/5/16 23:16
  */
class StdFuc extends RichMapFunction[Report,Report]{
  private val logger: Logger = LoggerFactory.getLogger(classOf[StdFuc])

  override def map(value: Report): Report = {
    var report:Report = value
    report.obj.reportContent.checkItems.map(
      x=>{
          x.checkResults.map(
            y=> {
              val chkItemName = CleanMethod.index_name_replace(x.checkItemName)
              val chkIndexName = CleanMethod.index_name_replace(y.checkIndexName)
              if(MysqlMethods.result.get(chkItemName+chkIndexName)!=None){
                stdItems(chkItemName,chkIndexName,x,y)
              }
            })
      }
    )

    report.obj.reportContent.generalSummarys.map(x=>{
        val sugName = x.summaryName
        val sugKey = CleanMethod.clean_abn_name(sugName)
        val sugMap = MysqlMethods.sugMap
        if(sugMap.get(sugKey)!=None){
          val sugArray = sugMap.get(sugKey)
          val std_sug_name = sugArray.get(0)
          val body = sugArray.get(1)
          val check_mode = sugArray.get(2)
          val abnormal_label = sugArray.get(3)
          x.setStdSummaryName(std_sug_name)
          x.setBody(body)
          x.setCheckMode(check_mode)
          x.setAbnormalLabel(abnormal_label)
        }
    })

    if(report.obj.birthday!=null){
      try {
        println("计算年龄")
        val age = Integer.valueOf(report.obj.checkDate.substring(0,4))-Integer.valueOf(report.obj.birthday.substring(0,4))
        report.obj.setAge(age.toString)
        println("年龄："+report.obj.age)
      }catch {
        case ex:Exception=>
          logger.info("年龄计算错错误："+ex)
      }
    }
    report.obj.setAge("31")
    report
  }

  /**
    * ItemName,IndexName清洗
    * @param item
    * @param index
    * @param x
    * @param y
    */
  def stdItems(item:String,index:String,x:ChkItem,y:CheckResult)={
    val itemMap = MysqlMethods.result.get(item+index)
    val stdItemName = itemMap.get(0)
    val stdIndexName = itemMap.get(1)
    val indexType = itemMap.get(2)
    val stdType = itemMap.get(3)
    x.setStdCheckItemName(stdItemName)
    y.setStdCheckIndexName(stdIndexName)
    y.setCheckIndexType(indexType)
    y.setStdType(stdType)
    y.setUnit(y.unit.trim)
    //根据结果值判断指标类型
    val flag = CleanMethod.get_chk_item_data_type(y)
    if(flag==0){
      y.setResultTypeId(0)
      val stdValue = CleanMethod.result_value_replace(y.resultValue.trim)
      y.setStdResultValue(stdValue)
      val textRef = CleanMethod.text_ref_replace(y.textRef)
      if(textRef._4==0){
        y.setStdTextRef(textRef._1)
        y.setLowValueRef(textRef._2)
        y.setHighValueRef(textRef._3)
      }
    }
  }


}
