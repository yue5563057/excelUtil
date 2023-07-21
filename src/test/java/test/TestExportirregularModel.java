package test;

import com.dongyue.util.anno.IrregularExcel;
import com.dongyue.util.anno.IrregularFieldAnno;
import com.dongyue.util.anno.IrregularFieldNameAnno;
import com.dongyue.util.anno.IrregularItem;

import java.util.List;

@IrregularExcel
public class TestExportirregularModel {

    @IrregularFieldAnno(row =0,cloumn = 0,colspan = 9)
    private String title;

    @IrregularFieldNameAnno(row = 2,cloumn = 0,title = "编号",rowspan = 1)
    @IrregularFieldAnno(row = 2,cloumn = 1,colspan = 2,rowspan = 1)
    private String sn;


    @IrregularFieldNameAnno(row = 2,cloumn =4,title = "名称")
    @IrregularFieldAnno(row = 2,cloumn = 5,colspan = 4)
    private String name;


    @IrregularFieldNameAnno(row = 3,cloumn =4,title = "地址")
    @IrregularFieldAnno(row = 3,cloumn = 5,colspan = 4)
    private String address;

    @IrregularFieldAnno(row = 4,cloumn = 0,colspan = 9)
    private String listInfo;

    @IrregularItem(startRow=5)
    private List<TestModel> testModelList;


    public List<TestModel> getTestModelList() {
        return testModelList;
    }

    public void setTestModelList(List<TestModel> testModelList) {
        this.testModelList = testModelList;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getListInfo() {
        return listInfo;
    }

    public void setListInfo(String listInfo) {
        this.listInfo = listInfo;
    }
}
