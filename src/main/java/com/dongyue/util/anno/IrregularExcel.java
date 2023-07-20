package com.dongyue.util.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  异形表格解析
 *
 * @author 东岳
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IrregularExcel {


    /**
     * 工作薄名称
     * @return 设置工作薄的名称
     */
    String sheetName() default "sheet1";

}
