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
public @interface StandardExcelAttr {

    /**
     * excel的列数 从0开始
     * @return 从0开始
     */
    int excelColumn();

    /**
     * 格式化数据，如日期等需要做格式化处理时使用
     * @return 格式化的样式
     */
    String formart() default "";

    /**
     * 导出时列的标题
     * @return 列的标题
     */
    String title() default "";

}
