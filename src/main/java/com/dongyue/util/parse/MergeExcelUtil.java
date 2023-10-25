package com.dongyue.util.parse;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MergeExcelUtil implements MergeExcel {


    @Override
    public List<List<String>> mergeExcel(List<InputStream> inputStreamList) {
        List<List<List<String>>> dataset = new ArrayList<>();
        //第一步将多个list转换成1个
        for (InputStream inputStream : inputStreamList) {
            try {
                List<List<String>> lists = ParseExcelUtil.parseExcel(inputStream, 0, 0);
                dataset.add(lists);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<List<String>> combineDataset = combineDataset(dataset);
        return combineDataset;
    }

    @Override
    public List<List<String>> mergeStreetExcel(InputStream inputStream) {
        Workbook workbook = null;
        try {
//            workbook = WorkbookFactory.create(inputStream);
            workbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int numberOfSheets = workbook.getNumberOfSheets();
        List<List<String>> list = new ArrayList<>();
        for (int i = 0; i < numberOfSheets; i++) {
            List<List<String>> lists = ParseExcelUtil.parseExcel(workbook, 0, i);
            list.addAll(lists);
        }
        for (List<String> strings : list) {
            System.out.println(strings);
        }
        return list;
    }


    static List<List<String>> combineDataset(List<List<List<String>>> dataset) {
        List<List<String>> combinedDataset = new ArrayList<>();
        Integer startColumns = 0;
        int maxRows = getMaxRows(dataset);
        for (List<List<String>> table : dataset) {
            if (table.isEmpty()) {
                combinedDataset.add(new ArrayList<>()); // Add a placeholder row
            } else {
                int maxColumns = getMaxColumns(table);
                for (int row = 0; row < maxRows; row++) {
                    if(row<table.size()){
                        if(!combinedDataset.isEmpty()&&combinedDataset.size()>row&&combinedDataset.get(row)!=null){
                            List<String> combinedRow = combinedDataset.get(row);
                            for (int col = 0; col < maxColumns; col++) {
                                if (col < table.get(row).size()) {
                                    if(combinedRow.size()>startColumns){
                                        combinedRow.set(startColumns,table.get(row).get(col));
                                    }else{
                                        combinedRow.add(table.get(row).get(col));
                                    }
                                } else {
                                    combinedRow.add(""); // Add a placeholder value
                                }
                            }
                        }else{
                            List<String> combinedRow = new ArrayList<>(maxColumns);
                            for (int col = 0; col < maxColumns; col++) {
                                if (col < table.get(row).size()) {
                                    combinedRow.add(table.get(row).get(col));
                                } else {
                                    combinedRow.add(""); // Add a placeholder value
                                }
                            }
                            combinedDataset.add(combinedRow);
                        }
                    }else{
                        if(combinedDataset.size()>row&&combinedDataset.get(row)!=null){
                            combinedDataset.get(row).addAll(new ArrayList<>(Collections.nCopies(maxColumns, "")));
                        }else{
                            combinedDataset.add(new ArrayList<>(Collections.nCopies(maxColumns, "")));
                        }
                    }
                    startColumns += maxColumns;
                }
            }
        }

        return combinedDataset;
    }

    private static int getMaxRows(List<List<List<String>>> datas) {
        int maxRows = 0;
        for (List<List<String>> table : datas) {
            maxRows = Math.max(maxRows, table.size());
        }
        return maxRows;
    }

    private static int getMaxColumns(List<List<String>> table) {
        int maxColumns = 0;
        for (List<String> row : table) {
            maxColumns = Math.max(maxColumns, row.size());
        }
        return maxColumns;
    }

}
