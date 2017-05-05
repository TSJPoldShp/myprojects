package com.education.zfr.common.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ExcelFileUtil {

    /**
     * 导入Excel
     * 
     * @param book Excel文件
     * @return
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static List<List<String>> importExcel(Workbook book)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        if (book != null) {
            List<List<String>> contentList = new ArrayList<List<String>>();
            // 得到第一页
            Sheet sheet = book.getSheetAt(0);
            // 获取总行数
            int rowNum = sheet.getPhysicalNumberOfRows();
            List<String> rowList = null;
            int columnNum = 0;
            if (sheet.getRow(0) != null)// 取出title的count
                columnNum = sheet.getRow(0).getPhysicalNumberOfCells();

            for (int rowIndex = 0; rowIndex < rowNum; rowIndex++) {
                if (rowIndex == 0) // 过掉title
                    continue;
                rowList = new ArrayList<String>();
                Row _row = sheet.getRow(rowIndex);
                for (int columnIndex = 0; columnIndex < columnNum; columnIndex++) {
                    rowList.add(getCellFormatValue(_row.getCell(columnIndex)));
                }
                contentList.add(rowList);
            }
            return contentList;
        }
        return null;
    }

    /**
     * 根据HSSFCell类型设置数据
     * 
     * @param cell
     * @return
     */
    private static String getCellFormatValue(Cell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case Cell.CELL_TYPE_NUMERIC:
                case Cell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (DateUtil.isCellDateFormatted(cell)) {
                        // 如果是Date类型则，转化为Data格式

                        // 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                        cellvalue = cell.getDateCellValue().toLocaleString();

                        // 方法2：这样子的data格式是不带带时分秒的：2011-10-12
                        // Date date = cell.getDateCellValue();
                        // cellvalue = SysDate.getDate(date);

                    } else {
                        // 取得当前Cell的数值
                        if (cell.getNumericCellValue() % 1 == 0) {
                            DecimalFormat format = new DecimalFormat("#");
                            cellvalue = String.valueOf(format.format(cell.getNumericCellValue()));
                        } else {
                            cellvalue = String.valueOf(cell.getNumericCellValue());
                        }
                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case Cell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                // 默认的Cell值
                default:
                    cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }
}
