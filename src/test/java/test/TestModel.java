package test;

import com.dongyue.util.anno.StandardExcelAttr;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestModel {
    @StandardExcelAttr(excelColumn = 0, title = "列表标题1")
    String number1;
    @StandardExcelAttr(excelColumn = 1, title = "列表标题2")
    Integer number2;
    @StandardExcelAttr(excelColumn = 2, title = "列表标题3")
    String number3;
    @StandardExcelAttr(excelColumn = 3, title = "列表标题4")
    String number4;
    @StandardExcelAttr(excelColumn = 4, title = "列表标题5")
    String number5;
    @StandardExcelAttr(excelColumn = 5, title = "列表标题6")
    LocalDateTime number6;
    @StandardExcelAttr(excelColumn = 6, title = "列表标题7")
    BigDecimal number7;
    @StandardExcelAttr(excelColumn = 7, title = "列表标题8")
    Long number8;
    @StandardExcelAttr(excelColumn = 8, title = "列表标题9")
    Character number9;
    @StandardExcelAttr(excelColumn = 9, title = "列表标题10")
    String number10;


    public String getNumber1() {
        return number1;
    }

    public void setNumber1(String number1) {
        this.number1 = number1;
    }

    public Integer getNumber2() {
        return number2;
    }

    public void setNumber2(Integer number2) {
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

    public LocalDateTime getNumber6() {
        return number6;
    }

    public void setNumber6(LocalDateTime number6) {
        this.number6 = number6;
    }

    public BigDecimal getNumber7() {
        return number7;
    }

    public void setNumber7(BigDecimal number7) {
        this.number7 = number7;
    }

    public Long getNumber8() {
        return number8;
    }

    public void setNumber8(Long number8) {
        this.number8 = number8;
    }

    public Character getNumber9() {
        return number9;
    }

    public void setNumber9(Character number9) {
        this.number9 = number9;
    }

    public String getNumber10() {
        return number10;
    }

    public void setNumber10(String number10) {
        this.number10 = number10;
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
                '}';
    }
}
