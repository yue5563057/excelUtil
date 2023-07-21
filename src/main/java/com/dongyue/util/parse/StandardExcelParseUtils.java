package com.dongyue.util.parse;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import com.dongyue.util.StringUtil;
import com.dongyue.util.anno.StandardExcel;
import com.dongyue.util.anno.StandardExcelAttr;
import com.dongyue.util.exception.MyException;

/**
 * @author 东岳
 * @param <T> 需要成为的对象
 */
public class StandardExcelParseUtils<T> implements StandardExcelParse<T> {

    @Override
    public List<T> standardExcelToList(File file, Class<T> tClass) {
        try {
            StandardExcel standardExcel = tClass.getAnnotation(StandardExcel.class);
            List<List<String>> lists ;
            if(standardExcel==null){
                lists = ParseExcelUtil.parseExcel(file);
            }else {
                int startRow = standardExcel.startRow();
                int sheetNumber = standardExcel.sheetNumber();
                lists = ParseExcelUtil.parseExcel(file,startRow,sheetNumber);
            }
            return standardExcelToList(lists,tClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
   public List<T> standardExcelToList(InputStream inputStream, Class<T> tClass){
        try {
            StandardExcel standardExcel = tClass.getAnnotation(StandardExcel.class);
            List<List<String>> lists ;
            if(standardExcel==null){
                lists = ParseExcelUtil.parseExcel(inputStream);
            }else {
                int startRow = standardExcel.startRow();
                int sheetNumber = standardExcel.sheetNumber();
                lists = ParseExcelUtil.parseExcel(inputStream,startRow,sheetNumber);
            }
            return standardExcelToList(lists,tClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析excle成为对象
     * @param list 经过poi解析后的数据集合
     * @param tClass 需要转换的对象
     * @return 转换成功的对象集合
     */
    @Override
    public List<T> standardExcelToList(List<List<String>> list, Class<T> tClass)  {
        List<T> returnList = new ArrayList<>();
        Field[] declaredFields = tClass.getDeclaredFields();
        Map<Integer, String> fieldOrder = new HashMap<>();
        for (Field declaredField : declaredFields) {
            StandardExcelAttr annotation = declaredField.getAnnotation(StandardExcelAttr.class);
            if (annotation == null) {
                continue;
            }
            int value = annotation.excelColumn();
            fieldOrder.put(value, declaredField.getName());
        }
        int row = 0;
        for (List<String> strings : list) {
            try {
                //行 先要获取构造方法
                Constructor constructor = tClass.getConstructor();
                // 实例化
                T t = (T) constructor.newInstance();
                for (int i = 0; i < strings.size(); i++) {
                    String fileName = fieldOrder.get(i);
                    if(StringUtil.isNull(fileName)){
                        continue;
                    }
                    Field field = tClass.getDeclaredField(fileName);
                    field.setAccessible(true);
                    Class<?> type = field.getType();
                    if (type.isAssignableFrom(Integer.class)) {
                        field.set(t, Integer.valueOf(strings.get(i)));
                    } else if (type.isAssignableFrom(LocalDateTime.class)) {
                        StandardExcelAttr annotation = field.getAnnotation(StandardExcelAttr.class);
                        DateTimeFormatter isoLocalDateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                        if (StringUtil.isNotNull(annotation.formart())) {
                            isoLocalDateTime = DateTimeFormatter.ofPattern(annotation.formart());
                        }
                        field.set(t, LocalDateTime.parse(strings.get(i), isoLocalDateTime));
                    } else if (type.isAssignableFrom(LocalDate.class)) {
                        StandardExcelAttr annotation = field.getAnnotation(StandardExcelAttr.class);
                        DateTimeFormatter isoLocalDate = DateTimeFormatter.ISO_LOCAL_DATE;
                        if (StringUtil.isNotNull(annotation.formart())) {
                            isoLocalDate = DateTimeFormatter.ofPattern(annotation.formart());
                        }
                        field.set(t, LocalDateTime.parse(strings.get(i), isoLocalDate));
                    } else if (type.isAssignableFrom(Date.class)) {
                        StandardExcelAttr annotation = field.getAnnotation(StandardExcelAttr.class);
                        SimpleDateFormat format = new SimpleDateFormat();
                        if (StringUtil.isNotNull(annotation.formart())) {
                            format = new SimpleDateFormat(annotation.formart());
                        }
                        try {
                            field.set(t, format.parse(strings.get(i)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else if (type.isAssignableFrom(Boolean.class)) {
                        field.set(t, Boolean.valueOf(strings.get(i)));
                    } else if (type.isAssignableFrom(BigDecimal.class)) {
                        field.set(t, new BigDecimal(strings.get(i)));
                    } else if (type.isAssignableFrom(Long.class)) {
                        field.set(t, Long.valueOf(strings.get(i)));
                    } else if (type.isAssignableFrom(Short.class)) {
                        field.set(t, Short.valueOf(strings.get(i)));
                    } else if (type.isAssignableFrom(Byte.class)) {
                        field.set(t, Byte.valueOf(strings.get(i)));
                    } else if (type.isAssignableFrom(Float.class)) {
                        field.set(t, Float.valueOf(strings.get(i)));
                    } else if (type.isAssignableFrom(Double.class)) {
                        field.set(t, Double.valueOf(strings.get(i)));
                    } else {
                        field.set(t, strings.get(i));
                    }
                }
                returnList.add(t);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return returnList;
    }

    @Override
    public Set<T> standardExcelToSet(List<List<String>> list, Class<T> tClass) {
        List<T> ts = standardExcelToList(list,tClass);
        return new HashSet<>(ts);
    }


}
