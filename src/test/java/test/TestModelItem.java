package test;

import com.dongyue.util.anno.StandardExcelAttr;

import java.util.List;

public class TestModelItem {

    @StandardExcelAttr(excelColumn = 4, title = "子标题2")
    String number2;


    @StandardExcelAttr(excelColumn = 5,colspan = 1, useListTitle = true,forIn = true)
    List<TestModelItem2> number3;


    public String getNumber2() {
        return number2;
    }

    public void setNumber2(String number2) {
        this.number2 = number2;
    }

    public List<TestModelItem2> getNumber3() {
        return number3;
    }

    public void setNumber3(List<TestModelItem2> number3) {
        this.number3 = number3;
    }
}