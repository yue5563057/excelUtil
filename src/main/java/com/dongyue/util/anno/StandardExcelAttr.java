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
     * 跨几行，默认一行不跨为0
     * @return 跨几行
     */
    int rowspan() default 0;

    /**
     * 跨几列，默认一列不跨为0
     * @return 跨几列
     */
    int colspan() default 0;

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
    /**
     * 是否使用字段的标题
     * @return 列的标题
     */
    boolean useListTitle() default false;

    /**
     * 继续遍历
     * @return
     */
    boolean forIn() default false;
}
