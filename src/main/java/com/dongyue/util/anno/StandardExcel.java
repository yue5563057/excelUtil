package com.dongyue.util.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 东岳
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface StandardExcel {
    /**
     * 从第几行开始读取数据
     * @return 默认从第1行开始读取数据
     */
    int startRow() default 1;

    /**
     * 读取第几个工作薄
      * @return 默认读取第0个工作薄，
     */
    int sheetNumber() default 0;
}
