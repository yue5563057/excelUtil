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
public @interface IrregularItem {

    /**
     * 列表开始的行数，从0开始计数
     * @return 列表开始的行数
     */
    int startRow();
}
