package com.dongyue.util.parse;

public class ExcelUtilFactory {


    public static final Integer TYPE_STANDARD = 0;


    public static final Integer TYPE_IRREGULAR = 1;


    public static <T> StandardExcelParse<T> getStandard(){
        return new StandardExcelParseUtils<>();
    }

    public static <T> StandardExcelParse<T> getIrregular(){
        return new StandardExcelParseUtils<>();
    }

}
