package com.dongyue.util.Excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 东岳
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyExcelAnno {
    /**excel的列数 从0开始*/
    int excelColumn();
    /** 格式化的数据 */
    String formart() default "";
}
