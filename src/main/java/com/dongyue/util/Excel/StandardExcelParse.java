package com.dongyue.util.Excel;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * excel解析器
 * @param <T>
 * @author 东岳
 */
public interface StandardExcelParse<T> {

     List<T> standardExcelToList(File file, Class<T> tClass);



     List<T> standardExcelToList(List<List<String>> list, Class<T> tClass);


     Set<T> standardExcelToSet(List<List<String>> list, Class<T> tClass);
}
