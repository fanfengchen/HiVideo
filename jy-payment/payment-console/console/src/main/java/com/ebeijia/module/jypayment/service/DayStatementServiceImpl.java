package com.ebeijia.module.jypayment.service;

import com.ebeijia.module.jypayment.dao.DayStatementDao;
import com.ebeijia.util.common.PageParamUtils;
import net.sf.json.JSONArray;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with com.ebeijia.module.jypayment.service
 * User : zc
 * Date : 2016/10/25
 */
@Service
public class DayStatementServiceImpl implements DayStatementService {

    private static Logger log = LoggerFactory.getLogger(DayStatementServiceImpl.class);

    @Autowired
    private DayStatementDao dayStatementDao;

    public Map<String, Object> getDayStatement(String orderTimeStart,
                                               String orderTimeEnd,
                                               String orderType,
                                               String aoData) throws  Exception{
        //source为0表示需要分页参数
        Map<String, Object> resMap = getDayStatement(orderTimeStart, orderTimeEnd, orderType, aoData, "0");
        return resMap;
    }

    public Map<String, Object> getDayStatement(String orderTimeStart,
                                               String orderTimeEnd,
                                               String orderType,
                                               String aoData,
                                               String source) throws  Exception{
        Map<String, Object> resMap = new HashMap<String, Object>();

        Map<String, Object> cond = new HashMap<String, Object>();
        cond.put("orderType", orderType);
        if("0".equals(source)){
            if(StringUtils.isEmpty(aoData)){
                resMap.put("code", "-1");
                return resMap;
            }
            //分页参数
            Map<String, Integer> pageMap = PageParamUtils.getPageParam(aoData);
            cond.put("page", pageMap.get("page"));
            cond.put("size", pageMap.get("size"));
            //用于判断是否需要分页参数
            cond.put("source", source);
        }

        //-----------------初始化时间开始------------------
        //初始化选择条件时间，当没有选择时间时会使用
        Date endDate = new Date();
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(endDate);
        startCal.add(Calendar.DAY_OF_YEAR, -90);
        Date startDate = startCal.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        //获取选择条件时间
        if(!(StringUtils.isEmpty(orderTimeStart) || StringUtils.isEmpty(orderTimeEnd))){
            //判断时间的范围是否大于三个月
            Map<String, Object> tempMap = compareMonth(orderTimeStart, orderTimeEnd);
            String code = (String)tempMap.get("code");
            if("0".equals(code)){
                //如果不大于三个月的话则将输入的条件赋值
                startDate = format.parse(orderTimeStart);
                endDate = format.parse(orderTimeEnd);
            }else if("-1".equals(code)){
                //输入的日期范围大于三个月
                resMap.put("code", "-2");
                return resMap;
            }else{
                //输入的日期开始时间大于结束时间
                resMap.put("code", "-3");
                return resMap;
            }
        }
        //-----------------初始化时间结束------------------

        cond.put("orderTimeStart", format.format(startDate));
        cond.put("orderTimeEnd", format.format(endDate));

        //获取分页数据
        Map<String, Object> map = dayStatementDao.queryDayStatement(cond);

        //获取分页数据统计
        if("0".equals(source)){
            Map<String, Object> mapCount = dayStatementDao.queryDayStatementCount(cond);
            map.put("count", mapCount.get("count"));
        }

        //获取合计数据
        Map<String, Object> totalMap = dayStatementDao.queryDayStatementTotal(cond);
        map.put("totalData", totalMap.get("data"));

        //用于excel的文件名称
        resMap.put("dateStr", format.format(startDate) + " 至 " + format.format(endDate));

        resMap.put("code", "0");
        resMap.put("data", map);
        return resMap;
    }

    public Map<String, Object> getMonthStatement(String orderTimeStart, String
            orderTimeEnd, String orderType, String aoData) throws Exception{
        Map<String, Object> resMap = getMonthStatement(orderTimeStart, orderTimeEnd, orderType, aoData, "0");
        return resMap;
    }

    public Map<String, Object> getMonthStatement(String orderTimeStart, String
            orderTimeEnd, String orderType, String aoData, String source) throws Exception{
        Map<String, Object> resMap = new HashMap<String, Object>();

        Map<String, Object> cond = new HashMap<String, Object>();
        if("0".equals(source)){
            if(StringUtils.isEmpty(aoData)){
                resMap.put("code", "-1");
                return resMap;
            }
            //分页参数
            Map<String, Integer> pageMap = PageParamUtils.getPageParam(aoData);
            //判断是否需要分页
            cond.put("source", source);
            cond.put("page", pageMap.get("page"));
            cond.put("size", pageMap.get("size"));
        }

        if(!StringUtils.isEmpty(orderTimeStart)){
            cond.put("orderTimeStart", orderTimeStart);
        }
       if(!StringUtils.isEmpty(orderTimeEnd)){
           cond.put("orderTimeEnd", orderTimeEnd);
       }

        cond.put("orderType", orderType);

        //获取分页数据和统计
        Map<String, Object> map = dayStatementDao.queryMonthStatement(cond);
        if("0".equals(source)){
            Map<String, Object> mapCount = dayStatementDao.queryMonthStatementCount(cond);
            map.put("count", mapCount.get("count"));
        }

        //获取合计数据
        Map<String, Object> totalMap = dayStatementDao.queryMonthStatementTotal(cond);
        map.put("totalData", totalMap.get("data"));

        resMap.put("code", "0");
        resMap.put("data", map);
        return resMap;
    }

    //比较输入的时间
    private Map<String, Object> compareMonth(String start, String end) throws Exception{
        Map<String, Object> resMap = new HashMap<String, Object>();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(format.parse(start));
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(format.parse(end));

        int compare = endCal.get(Calendar.DAY_OF_YEAR) - startCal.get(Calendar.DAY_OF_YEAR);
        if(compare > 90){
            //时间超过90天
            resMap.put("code", "-1");
            return  resMap;
        }
        if(compare < 0){
            //开始时间大于结束时间
            resMap.put("code", "-2");
            return  resMap;
        }
        resMap.put("code", "0");
        return resMap;
    }

    public Map<String, Object> downloadPayDetail(String orderTimeStart,
                                                 String orderTimeEnd,
                                                 String orderType,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        map = this.getDayStatement(orderTimeStart, orderTimeEnd, orderType, "", "1");
        String code = (String)map.get("code");
        if("0".equals(code)){
            //返回数据成功
            Map<String, Object> data = (Map<String, Object>)map.get("data");
            data.put("dateStr", map.get("dateStr"));
            String title = "日报表";
            String fileNameStr ="日报表.xls";
            createFile(data,title,fileNameStr, request, response);
        }
        return map;

    }

    public Map<String, Object> downloadMonthDetail(String orderTimeStart, String orderTimeEnd, String orderType,
                                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map = this.getMonthStatement(orderTimeStart, orderTimeEnd, orderType, "", "1");
        String code = (String)map.get("code");
        if("0".equals(code)){
            //返回数据成功
            Map<String, Object> data = (Map<String, Object>)map.get("data");
            data.put("dateStr", map.get("dateStr"));
            String title = "月报表";
            String fileNameStr ="月报表.xls";
            createFile(data,title,fileNameStr, request, response);
        }
        return map;

    }

    private Map<String, Object> createFile(Map<String, Object> map,String title,String fileNameStr,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws Exception{

        //excel文件名称
        String dateStr = (String)map.get("dateStr");
        String fileName=null;
        if(dateStr!=null&&dateStr.equals("")){
           fileName = dateStr +fileNameStr;
        }else{
            fileName = fileNameStr;
        }
        fileName = new String(fileName.getBytes("UTF-8"), "ISO_8859_1");
        HSSFWorkbook book =  createExcel(map,title);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        OutputStream out = null;
        try{
            out = response.getOutputStream();
            book.write(out);
            out.flush();
        }catch(Exception e){
            log.error(e.getMessage(), e);
        }finally{
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
        map.put("code", "0");
        return map;
    }



    private HSSFWorkbook createExcel(Map<String, Object> map,String title){

        HSSFWorkbook workbook = new HSSFWorkbook();                     // 创建工作簿对象
        HSSFSheet sheet = workbook.createSheet(title);                  // 创建工作表

        // 生成表格标题行
        HSSFRow rowm = sheet.createRow(0);
        HSSFCell cellTiltle1 = rowm.createCell(0);
        cellTiltle1.setCellValue("日期");

        HSSFCell cellTiltle2 = rowm.createCell(1);
        cellTiltle2.setCellValue("应缴金额");

        HSSFCell cellTiltle3 = rowm.createCell(2);
        cellTiltle3.setCellValue("预存金额");

        HSSFCell cellTiltle4 = rowm.createCell(3);
        cellTiltle4.setCellValue("实缴金额");

        HSSFCell cellTiltle5 = rowm.createCell(4);
        cellTiltle5.setCellValue("户数");

        HSSFCell cellTiltle6 = rowm.createCell(5);
        cellTiltle6.setCellValue("支付成功笔数");

        HSSFCell cellTiltle7 = rowm.createCell(6);
        cellTiltle7.setCellValue("缴费成功笔数");

        HSSFCell cellTiltle8 = rowm.createCell(7);
        cellTiltle8.setCellValue("退款笔数");

        int rowCount = 1;

        //详情数据
        List<Map<String, Object>> detailData = (List<Map<String, Object>>)map.get("detailData");

        for(Map<String, Object> detailMap : detailData){

            HSSFRow rowmTemp = sheet.createRow(rowCount);
            rowCount++;

            HSSFCell cell1 = rowmTemp.createCell(0);
            cell1.setCellValue((String)detailMap.get("orderTime"));

            HSSFCell cell2 = rowmTemp.createCell(1);
            cell2.setCellValue((String)detailMap.get("needPay"));

            HSSFCell cell3 = rowmTemp.createCell(2);
            cell3.setCellValue((String)detailMap.get("prestoreAmt"));

            HSSFCell cell4 = rowmTemp.createCell(3);
            cell4.setCellValue((String) detailMap.get("orderAmt"));

            HSSFCell cell5 = rowmTemp.createCell(4);
            cell5.setCellValue((String) detailMap.get("accountTotal"));

            HSSFCell cell6 = rowmTemp.createCell(5);
            cell6.setCellValue((String)detailMap.get("orderStatusSuccTotal"));

            HSSFCell cell7 = rowmTemp.createCell(6);
            cell7.setCellValue((String)detailMap.get("sentResultSuccTotal"));

            HSSFCell cell8 = rowmTemp.createCell(7);
            cell8.setCellValue((String)detailMap.get("refundTotal"));

        }

        rowCount = rowCount + 2;
        HSSFRow totalRowmInfo = sheet.createRow(rowCount);
        HSSFCell totalCellInfo = totalRowmInfo.createCell(0);
        totalCellInfo.setCellValue("合计");

        //统计行标题
        rowCount = rowCount + 1;
        HSSFRow totalRowm = sheet.createRow(rowCount);

        HSSFCell totalCellTiltle2 = totalRowm.createCell(0);
        totalCellTiltle2.setCellValue("应缴金额");

        HSSFCell totalCellTiltle3 = totalRowm.createCell(1);
        totalCellTiltle3.setCellValue("预存金额");

        HSSFCell totalCellTiltle4 = totalRowm.createCell(2);
        totalCellTiltle4.setCellValue("实缴金额");

        HSSFCell totalCellTiltle5 = totalRowm.createCell(3);
        totalCellTiltle5.setCellValue("户数");

        HSSFCell totalCellTiltle6 = totalRowm.createCell(4);
        totalCellTiltle6.setCellValue("支付成功笔数");

        HSSFCell totalCellTiltle7 = totalRowm.createCell(5);
        totalCellTiltle7.setCellValue("缴费成功笔数");

        HSSFCell totalCellTiltle8 = totalRowm.createCell(6);
        totalCellTiltle8.setCellValue("退款笔数");

        //统计行
        Map<String, Object> totalData = (Map<String, Object>)map.get("totalData");

        if(totalData != null){

            rowCount = rowCount + 1;
            HSSFRow total = sheet.createRow(rowCount);

            HSSFCell totalCell1 = total.createCell(0);
            totalCell1.setCellValue((String)totalData.get("needPay"));

            HSSFCell totalCell2 = total.createCell(1);
            totalCell2.setCellValue((String)totalData.get("prestoreAmt"));

            HSSFCell totalCell3 = total.createCell(2);
            totalCell3.setCellValue((String)totalData.get("orderAmt"));

            HSSFCell totalCell4 = total.createCell(3);
            totalCell4.setCellValue((String)totalData.get("accountTotal"));

            HSSFCell totalCell5 = total.createCell(4);
            totalCell5.setCellValue((String)totalData.get("orderStatusSuccTotal"));

            HSSFCell totalCell6 = total.createCell(5);
            totalCell6.setCellValue((String)totalData.get("sentResultSuccTotal"));

            HSSFCell totalCell7 = total.createCell(6);
            totalCell7.setCellValue((String)totalData.get("refundTotal"));
        }

        return workbook;

    }

}
