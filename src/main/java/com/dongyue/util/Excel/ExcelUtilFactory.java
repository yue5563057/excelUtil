package com.dongyue.util.Excel;

public class ExcelUtilFactory<T> {


    public static final Integer TYPE_STANDARD = 0;


    public static final Integer TYPE_IRREGULAR = 1;


    public static <T> StandardExcelParse<T> getStandard(){
        return new ExcelModelUtils<T>();
    }

    public static <T> StandardExcelParse<T> getIrregular(){
        return new ExcelModelUtils<T>();
    }

}
