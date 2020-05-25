package com.haozhuo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DELL on 2020/5/22 16:36
 */
public class StrUtils implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(StrUtils.class);
    public static String replaceLineBreak(String str) {
        Pattern p = Pattern.compile("\\s*|\\n");
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
    public static Double toDouble(String str) {
        try {
            str = str.replaceAll("[^\\d.]+", "");
            if (str.isEmpty()) {
                return 0D;
            } else {
                return Double.parseDouble(str);
            }
        } catch (Exception e) {
            return 0D;
        }
    }
//
//    public static String getStrDate(String format) {
//        return new SimpleDateFormat(format).format(new Date());
//    }

   // public static String getStrDate() {
//        return getStrDate("yyyy-MM-dd HH:mm:ss");
//    }

    public static Date strToDate(String str) {
        System.out.println(str);
        java.sql.Date date = java.sql.Date.valueOf(str);
        return date;
    }

//    public static Date strToDate(String str,String format) {
//        SimpleDateFormat sdf = new SimpleDateFormat(format);//yyyy-MM-dd HH:mm:ss
//        Date date;
//        try {
//            date=sdf.parse(str);
//        } catch (ParseException e) {
//            logger.info("解析日期出错:{}",e);
//            date = new Date();
//        }
//        return date;
//    }
}
