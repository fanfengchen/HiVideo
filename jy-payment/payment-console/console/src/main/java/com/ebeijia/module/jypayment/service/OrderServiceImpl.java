package com.ebeijia.module.jypayment.service;

import com.ebeijia.entity.jypayment.OrderDto;
import com.ebeijia.module.jypayment.dao.OrderDao;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with com.ebeijia.module.jypayment.service
 * User : zc
 * Date : 2016/10/24
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    public Map<String, Object> queryPayDetail(OrderDto orderDto, String aoData) throws Exception{
        //source 为0表示需要分页参数
        Map<String, Object> resMap = this.queryPayDetail(orderDto, aoData, "0");
        return resMap;
    }

    /**
     * source判断是否需要分页数据
     */
    public Map<String, Object> queryPayDetail(OrderDto orderDto, String aoData, String source) throws Exception{
        Map<String, Object> resMap = new HashMap<String, Object>();

        Map<String, Object> cond = new HashMap<String, Object>();

        if("0".equals(source)){
            if(StringUtils.isEmpty(aoData)){
                //参数缺失
                resMap.put("code", "-1");
                return resMap;
            }

            Map<String, Integer> pageMap = PageParamUtils.getPageParam(aoData);
            cond.put("page", pageMap.get("page"));
            cond.put("size", pageMap.get("size"));
        }

        cond.put("source", source);

        Map<String, Object> map  = orderDao.queryOrderDetail(orderDto, cond);
        if("0".equals(source)){
            Map<String, Object> mapCount = orderDao.queryOrderDetailCount(orderDto, cond);
            map.put("count", mapCount.get("count"));
        }
        resMap.put("code", "0");
        resMap.put("data", map);
        return resMap;
    }

    /**
     * 导出excel
     */
    public Map<String, Object> downLoad(HttpServletRequest request,
                         HttpServletResponse response,
                         OrderDto orderDto) throws Exception{

        Map<String, Object> map = new HashMap<String, Object>();

        String fileName = "交易明细.xls";
        fileName = new String(fileName.getBytes("UTF-8"), "ISO_8859_1");

        Map<String, Object> resMap = queryPayDetail(orderDto, "", "1");
        String code = (String)resMap.get("code");
        if("-1".equals(code)){
            map.put("code", "-1");
            return map;
        }
        List<Map<String, Object>> list = (List<Map<String, Object>>)
                (((Map<String, Object>)resMap.get("data")).get("data"));
        HSSFWorkbook book =  createExcel(list);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        OutputStream out = null;
        try{
            out = response.getOutputStream();
            book.write(out);
            out.flush();
        }catch(Exception e){
            log.error(e.getMessage() , e);
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

    private HSSFWorkbook createExcel(List<Map<String, Object>> list){
        String title = "交易明细";
        HSSFWorkbook workbook = new HSSFWorkbook();                     // 创建工作簿对象
        HSSFSheet sheet = workbook.createSheet(title);                  // 创建工作表

        // 产生表格标题行
        HSSFRow rowm = sheet.createRow(0);
        HSSFCell cellTiltle1 = rowm.createCell(0);
        cellTiltle1.setCellValue("订单时间");

        HSSFCell cellTiltle2 = rowm.createCell(1);
        cellTiltle2.setCellValue("费用类型");

        HSSFCell cellTiltle3 = rowm.createCell(2);
        cellTiltle3.setCellValue("缴费号");

        HSSFCell cellTiltle4 = rowm.createCell(3);
        cellTiltle4.setCellValue("应缴金额");

        HSSFCell cellTiltle5 = rowm.createCell(4);
        cellTiltle5.setCellValue("实缴金额");

        HSSFCell cellTiltle6 = rowm.createCell(5);
        cellTiltle6.setCellValue("流水号");

        HSSFCell cellTiltle7 = rowm.createCell(6);
        cellTiltle7.setCellValue("订单号");

        HSSFCell cellTiltle8 = rowm.createCell(7);
        cellTiltle8.setCellValue("支付结果");

        HSSFCell cellTiltle9 = rowm.createCell(8);
        cellTiltle9.setCellValue("缴费结果");

        int rowCount = 1;

        for(Map<String, Object> map : list){

            HSSFRow rowmTemp = sheet.createRow(rowCount);
            rowCount++;

            HSSFCell cell1 = rowmTemp.createCell(0);
            cell1.setCellValue((String)map.get("orderTime"));

            HSSFCell cell2 = rowmTemp.createCell(1);
            cell2.setCellValue((String)map.get("orderType"));

            HSSFCell cell3 = rowmTemp.createCell(2);
            cell3.setCellValue((String)map.get("payAccountNo"));

            HSSFCell cell4 = rowmTemp.createCell(3);
            cell4.setCellValue((String) map.get("needPay"));

            HSSFCell cell5 = rowmTemp.createCell(4);
            cell5.setCellValue((String) map.get("orderAmt"));

            HSSFCell cell6 = rowmTemp.createCell(5);
            cell6.setCellValue((String)map.get("sentNo"));

            HSSFCell cell7 = rowmTemp.createCell(6);
            cell7.setCellValue((String)map.get("orderNo"));

            HSSFCell cell8 = rowmTemp.createCell(7);
            cell8.setCellValue((String)map.get("orderStatus"));

            HSSFCell cell9 = rowmTemp.createCell(8);
            cell9.setCellValue((String)map.get("sentResult"));

        }

        return workbook;

    }

}
