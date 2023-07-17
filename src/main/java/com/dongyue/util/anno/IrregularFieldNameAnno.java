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

    /**所处第几行*/
    int row();

    /**所处第几列*/
    int cloumn();

    /**跨行合并*/
    int rowspan() default 0;

    /**跨列合并*/
    int colspan() default 0;

    /**对应名称*/
    String title() default "";
}
