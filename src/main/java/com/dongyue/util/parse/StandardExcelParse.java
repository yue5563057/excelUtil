package com.dongyue.util.parse;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

/**
 * excel解析器
 * @author 东岳
 */
public interface StandardExcelParse<T> {

    /**
     * excel转对指定对象
     * @param file excel文件
     * @param tClass 对象class
     * @return 对象列表
     */
     List<T> standardExcelToList(File file, Class<T> tClass);

    /**
     * excel转对指定对象
     * @param file excel文件流
     * @param tClass 对象class
     * @return 对象列表
     */
     List<T> standardExcelToList(InputStream file, Class<T> tClass);

    /**
     * excel转对指定对象
     * @param list 已通过poi解析的字符串对象
     * @param tClass 对象class
     * @return 对象列表
     */
     List<T> standardExcelToList(List<List<String>> list, Class<T> tClass);

    /**
     * excel转对指定对象
     * @param list 已通过poi解析的字符串对象
     * @param tClass 对象class
     * @return 对象Set
     */
     Set<T> standardExcelToSet(List<List<String>> list, Class<T> tClass);
}
