package test;

import com.dongyue.util.anno.StandardExcel;
import com.dongyue.util.anno.StandardExcelAttr;
@StandardExcel(startRow = 1,sheetNumber=0)
public class TestModel {
    @StandardExcelAttr(excelColumn = 0,title = "number1")
    String number1;
    @StandardExcelAttr(excelColumn = 1,title = "number2")
    String number2;
    @StandardExcelAttr(excelColumn = 2,title = "number3")
    String number3;
    @StandardExcelAttr(excelColumn = 3,title = "number4")
    String number4;
    @StandardExcelAttr(excelColumn = 4,title = "number5")
    String number5;
    @StandardExcelAttr(excelColumn = 5,title = "number6")
    String number6;
    @StandardExcelAttr(excelColumn =6,title = "number7")
    String number7;
    @StandardExcelAttr(excelColumn = 7,title = "number8")
    String number8;
    @StandardExcelAttr(excelColumn = 8,title = "number9")
    String number9;
    @StandardExcelAttr(excelColumn = 9,title = "number10")
    String number10;
    @StandardExcelAttr(excelColumn = 10,title = "number11")
    String number11;
    @StandardExcelAttr(excelColumn = 11,title = "number12")
    String number12;
    @StandardExcelAttr(excelColumn = 12,title = "number13")
    String number13;
    @StandardExcelAttr(excelColumn = 13,title = "number14")
    String number14;
    @StandardExcelAttr(excelColumn = 14,title = "number15")
    String number15;

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

    public String getNumber4() {
        return number4;
    }

    public void setNumber4(String number4) {
        this.number4 = number4;
    }

    public String getNumber5() {
        return number5;
    }

    public void setNumber5(String number5) {
        this.number5 = number5;
    }

    public String getNumber6() {
        return number6;
    }

    public void setNumber6(String number6) {
        this.number6 = number6;
    }

    public String getNumber7() {
        return number7;
    }

    public void setNumber7(String number7) {
        this.number7 = number7;
    }

    public String getNumber8() {
        return number8;
    }

    public void setNumber8(String number8) {
        this.number8 = number8;
    }

    public String getNumber9() {
        return number9;
    }

    public void setNumber9(String number9) {
        this.number9 = number9;
    }

    public String getNumber10() {
        return number10;
    }

    public void setNumber10(String number10) {
        this.number10 = number10;
    }

    public String getNumber11() {
        return number11;
    }

    public void setNumber11(String number11) {
        this.number11 = number11;
    }

    public String getNumber12() {
        return number12;
    }

    public void setNumber12(String number12) {
        this.number12 = number12;
    }

    public String getNumber13() {
        return number13;
    }

    public void setNumber13(String number13) {
        this.number13 = number13;
    }

    public String getNumber14() {
        return number14;
    }

    public void setNumber14(String number14) {
        this.number14 = number14;
    }

    public String getNumber15() {
        return number15;
    }

    public void setNumber15(String number15) {
        this.number15 = number15;
    }

    @Override
    public String toString() {
        return "TestModel{" +
                "number1='" + number1 + '\'' +
                ", number2='" + number2 + '\'' +
                ", number3='" + number3 + '\'' +
                ", number4='" + number4 + '\'' +
                ", number5='" + number5 + '\'' +
                ", number6='" + number6 + '\'' +
                ", number7='" + number7 + '\'' +
                ", number8='" + number8 + '\'' +
                ", number9='" + number9 + '\'' +
                ", number10='" + number10 + '\'' +
                ", number11='" + number11 + '\'' +
                ", number12='" + number12 + '\'' +
                ", number13='" + number13 + '\'' +
                ", number14='" + number14 + '\'' +
                ", number15='" + number15 + '\'' +
                '}';
    }
}
