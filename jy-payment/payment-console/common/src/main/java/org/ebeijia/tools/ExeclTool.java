package org.ebeijia.tools;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.Region;

/**
 * Created by Administrator on 2015/12/22.
 */
public class ExeclTool {


    /**表格列头
     * */
    public static void sheetHead(String head, String[] headCells, HSSFSheet sheet)
    {
        if (sheet != null)
        {
            HSSFCellStyle cellStyle = null;
            HSSFRow headRow = sheet.createRow(0);
            headRow.setHeight((short)500);
            HSSFCell headCell = headRow.createCell(0);
            headCell.setCellValue(head);
            if ((headCells != null) && (headCells.length > 0)) {
                sheet.addMergedRegion(new Region(0, (short)0, 0, (short)(headCells.length - 1)));
            }
            cellStyle = sheet.getWorkbook().createCellStyle();
            cellStyle.setFillForegroundColor((short)29);
            cellStyle.setFillPattern((short)1);
            cellStyle.setAlignment((short)2);
            HSSFFont font2 = sheet.getWorkbook().createFont();
            font2.setFontName("仿宋_GB2312");
            font2.setBoldweight((short)700);
            font2.setFontHeightInPoints((short)20);
            cellStyle.setFont(font2);
            headCell.setCellStyle(cellStyle);
        }
    }

    /**
     * 表格列值
     * */
    public static void sheetHeadCells(String[] headCells, HSSFSheet sheet)
    {
        if ((headCells != null) && (headCells.length > 0) && (sheet != null))
        {
            HSSFRow headCellRow = sheet.createRow(1);
            HSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
            cellStyle.setFillForegroundColor((short)40);
            sheetCellStyle(cellStyle);
            for (int i = 0; i < headCells.length; i++)
            {
                HSSFCell cell = headCellRow.createCell(i);
                sheet.setColumnWidth(i, 6500);
                cell.setCellValue(headCells[i]);
                cell.setCellStyle(cellStyle);
            }
        }
    }

    public static void sheetCellStyle(HSSFCellStyle cellStyle)
    {
        cellStyle.setFillPattern((short)1);
        cellStyle.setBorderBottom((short)1);
        cellStyle.setBorderLeft((short)1);
        cellStyle.setBorderTop((short)1);
        cellStyle.setBorderRight((short)1);
        cellStyle.setAlignment((short)2);
    }
}
