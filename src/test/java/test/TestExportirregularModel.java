package test;

import com.dongyue.util.anno.IrregularExcel;
import com.dongyue.util.anno.IrregularFieldAnno;
import com.dongyue.util.anno.IrregularFieldNameAnno;
import com.dongyue.util.anno.IrregularItem;

import java.util.List;

@IrregularExcel
public class TestExportirregularModel {

    @IrregularFieldAnno(row =0,cloumn = 0,colspan = 10)
    private String title;

    @IrregularFieldNameAnno(row = 2,cloumn = 3,title = "测试导出字段名称",colspan = 1)
    @IrregularFieldAnno(row = 3,cloumn = 3,rowspan = 1,colspan = 3)
    private String file;


    public List<TestModel> getTestModelList() {
        return testModelList;
    }

    public void setTestModelList(List<TestModel> testModelList) {
        this.testModelList = testModelList;
    }

    @IrregularItem(startRow=20)
    private List<TestModel> testModelList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFile() {
        return file;
    }
}
