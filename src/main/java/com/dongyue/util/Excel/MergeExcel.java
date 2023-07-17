package com.dongyue.util.Excel;


import java.io.InputStream;
import java.util.List;

/**
 * @author 东岳
 */
public interface MergeExcel {

    List<List<String>> mergeExcel(List<InputStream> inputStreamList);


}
