package com.dongyue.util.anno;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标准excel解析所使用的注解
 * @author 东岳
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StandardExcel {

    /**excel的列数 从0开始*/
    int excelColumn();
    /** 格式化的数据 */
    String formart() default "";
}
