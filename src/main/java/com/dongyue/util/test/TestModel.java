package com.dongyue.util.test;

import com.dongyue.util.anno.StandardExcel;
import com.dongyue.util.anno.StandardExcelAttr;
@StandardExcel(startRow = 0,sheetNumber=1)
public class TestModel {
    @StandardExcelAttr(excelColumn = 0)
    String number1;
    @StandardExcelAttr(excelColumn = 1)
    String number2;
    @StandardExcelAttr(excelColumn = 2)
    String number3;


    public String getNumber1() {
        return number1;
    }

    public void setNumber1(String number1) {
        this.number1 = number1;
    }

    public String getNumber2() {
        return number2;
    }

    public void setNumber2(String number2) {
        this.number2 = number2;
    }

    public String getNumber3() {
        return number3;
    }

    public void setNumber3(String number3) {
        this.number3 = number3;
    }

    @Override
    public String toString() {
        return "TestModel{" +
                "number1='" + number1 + '\'' +
                ", number2='" + number2 + '\'' +
                ", number3='" + number3 + '\'' +
                '}';
    }
}
