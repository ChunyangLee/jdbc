package com.lichunyang.testdate;

import org.junit.Test;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

/**
 * @author ChunyangLi
 * @create 2021-06-14-16:13
 */
public class Demo1 {
    @Test
    public void test(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = dtf.parse("1970-01-04 12:13:14", LocalDateTime::from);
        System.out.println(ldt.isSupported(ChronoField.MILLI_OF_SECOND)); //true
        System.out.println(ldt.getDayOfYear()); // 4
        long aLong = ldt.getLong(ChronoField.MILLI_OF_SECOND); // 0 ? //未设置毫秒数，因此为0
        System.out.println(ldt.getLong(ChronoField.MINUTE_OF_HOUR));//13
        long l2 = ldt.getLong(ChronoField.EPOCH_DAY); //3   从1970-1-1 开始的第几天
        System.out.println(l2);

    }
    @Test
    public void test2(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = dtf.parse("1995-01-04 12:13:14", LocalDateTime::from);
        //ldt加时区变成zdt
        //zdt才有，toInstant方法，
        ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
        Instant in1 = zdt.toInstant();
        System.out.println(new Date(in1.toEpochMilli()));
    }
    @Test
    public void test3(){
        Instant now = Instant.now();
        System.out.println(now); //2021-06-14T09:32:53.351Z
        LocalDateTime ldt = LocalDateTime.ofInstant(now, ZoneId.systemDefault());
        System.out.println(ldt); //2021-06-14T17:32:53.351
    }
}
