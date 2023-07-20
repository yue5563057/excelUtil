package com.dongyue.util.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 东岳
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IrregularFieldNameAnno {

    /**
     * 行数从0开始数，字段所处的行数
     * @return 字段所处第几行
     */
    int row();

    /**
     * 列数从0开始数，字段所处的列数
     * @return 字段所处第几列
     */
    int cloumn();

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
     * 字段对应的跨单元格标题
     * @return 标题的名称
     */
    String title() default "";
}
