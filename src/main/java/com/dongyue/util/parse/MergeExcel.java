package com.dongyue.util.parse;


import java.io.InputStream;
import java.util.List;

/**
 * 合并excel
 * @author 东岳
 */
public interface MergeExcel {

    /**
     * 合并多个excel 输出list
     * @param inputStreamList 多个excel的输入流集合
     * @return 解析合并出来的行列数据
     */
    List<List<String>> mergeExcel(List<InputStream> inputStreamList);

    /**
     * 将多个street合并成一个
     *
     * @param inputStream 多个excel的输入流集合
     * @return 解析合并出来的行列数据
     */
    List<List<String>> mergeStreetExcel(InputStream inputStream);

}
