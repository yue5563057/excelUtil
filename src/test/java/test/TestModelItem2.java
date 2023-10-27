package test;

import com.dongyue.util.anno.StandardExcelAttr;

import java.util.List;

public class TestModelItem2 {

    @StandardExcelAttr(excelColumn = 5, title = "子子标题2")
    String number1;


    @StandardExcelAttr(excelColumn = 6,title = "子子标题3")
    List<String> number2;


    public String getNumber1() {
        return number1;
    }

    public void setNumber1(String number1) {
        this.number1 = number1;
    }

    public List<String> getNumber2() {
        return number2;
    }

    public void setNumber2(List<String> number2) {
        this.number2 = number2;
    }
}
