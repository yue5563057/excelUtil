package test;

import com.dongyue.util.anno.StandardExcel;
import com.dongyue.util.anno.StandardExcelAttr;

import java.util.Date;

@StandardExcel(sheetNumber = 1)
public class Test1 {
    @StandardExcelAttr(excelColumn = 0)
    private String string;
    @StandardExcelAttr(excelColumn = 1 )
    private String string2;
    @StandardExcelAttr(excelColumn = 2)
    private String string3;
    @StandardExcelAttr(excelColumn = 3)
    private String string4;
    @StandardExcelAttr(excelColumn = 4)
    private String string5;
    @StandardExcelAttr(excelColumn = 5)
    private String string6;
    @StandardExcelAttr(excelColumn = 6)
    private String string7;
    @StandardExcelAttr(excelColumn = 7)
    private String string8;


    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getString2() {
        return string2;
    }

    public void setString2(String string2) {
        this.string2 = string2;
    }

    public String getString3() {
        return string3;
    }

    public void setString3(String string3) {
        this.string3 = string3;
    }

    public String getString4() {
        return string4;
    }

    public void setString4(String string4) {
        this.string4 = string4;
    }

    public String getString5() {
        return string5;
    }

    public void setString5(String string5) {
        this.string5 = string5;
    }

    public String getString6() {
        return string6;
    }

    public void setString6(String string6) {
        this.string6 = string6;
    }

    public String getString7() {
        return string7;
    }

    public void setString7(String string7) {
        this.string7 = string7;
    }

    public String getString8() {
        return string8;
    }

    public void setString8(String string8) {
        this.string8 = string8;
    }

    @Override
    public String toString() {
        return "Test1{" +
                "string='" + string + '\'' +
                ", string2='" + string2 + '\'' +
                ", string3='" + string3 + '\'' +
                ", string4='" + string4 + '\'' +
                ", string5='" + string5 + '\'' +
                ", string6='" + string6 + '\'' +
                ", string7='" + string7 + '\'' +
                ", string8='" + string8 + '\'' +
                '}';
    }
}
