package com.ebeijia.controller.school.Ttuition;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ebeijia.tools.DateTime4J;
import com.ebeijia.entity.ttuition.TnurseryTuition;
import com.ebeijia.entity.ttuition.Ttuition;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * DowloadExecl
 * excel 工具类
 * @author xiong.wang
 *  16/6/17
 */
public class DowloadExecl {
    public List<TnurseryTuition> readXls(String path) throws Exception {
        InputStream is = new FileInputStream(path);
        List<TnurseryTuition> list = new ArrayList<TnurseryTuition>();
        List<String> listpro = new ArrayList<String>();
        Workbook wb  = null;
        if(path.endsWith("xlsx")){
            wb = new XSSFWorkbook(is);
        }else{
            wb = new HSSFWorkbook(is);
        }

        TnurseryTuition tt = null;

        // 循环工作表Sheet
        Sheet hssfSheet = wb.getSheetAt(0);
        if (hssfSheet == null) {
            return null;
        }
        // 循环行Row
        for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++)
        {
            Row hssfRow = hssfSheet.getRow(rowNum);
            if (hssfRow == null) {
                continue;
            }
            tt = new TnurseryTuition();
            // 循环列Cell

            Cell studentId = hssfRow.getCell(0);
                if (studentId == null) {
                tt.setStudentId(null);
                //studentName;
            }else{
                tt.setStudentId(getValue(studentId));
                    listpro.add(getValue(studentId));
            }
            Cell studentName = hssfRow.getCell(1);
            if (studentName == null) {
                tt.setStudentName(null);
               // continue;
            }else{
                tt.setStudentName(getValue(studentName));
            }

            Cell tuition = hssfRow.getCell(2);
            if (tuition == null) {
                tt.setTuition(0);
              //  continue;
            }else{
                tt.setTuition(Double.valueOf(getValue(tuition)));
            }

            Cell accommodation = hssfRow.getCell(3);
            if (accommodation == null) {
                tt.setAccommodation(0);
              //  continue;
            }else{
                tt.setAccommodation(Double.valueOf(getValue(accommodation)));
            }

//            Cell payStatus = hssfRow.getCell(4);
//            if (payStatus == null) {
//                tt.setPayStatus(0);
//               // continue;
//            }else{
//            tt.setPayStatus(Double.valueOf(getValue(payStatus)).intValue());
//           }


            Cell reserve1 = hssfRow.getCell(4);
            if (reserve1 == null) {
                tt.setReserve1("0");
                //continue;
            }else{
                String value=getValue(reserve1);;
                tt.setReserve1(String.valueOf(value));
            }
            tt.setCreateAt(DateTime4J.getCurrentDateTime());
            tt.setPayStatus(0);
            list.add(tt);

        }
        Boolean flag = check(listpro);
        if(!flag){
            return null;
        }
             return list;
    }

    /**
     * 得到Excel表中的值
     *
     * @param hssfCell
     *            Excel中的每一个格子
     * @return Excel中每一个格子中的值
     */
    private String getValue(Cell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            // 返回数值类型的值
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            // 返回字符串类型的值
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }
    //验证导入菜品名是否重复
    public Boolean check(List<String> alist) {
        // 记录重复数据
        List<String> cplist = new ArrayList<String>();
        List<String> cflist = new ArrayList<String>();
        String theIndex = "";
        // 复制一个list
        List<String> blist = alist;
        if(alist.size() == 0){
            return false;
        }else{
            for (int i = 0; i < alist.size(); i++) {
                boolean b = false;
                for (int j = 0; j < blist.size(); j++) {
                    // 不和本身比较
                    if (j != i) {
                        // 找到相同的值
                        if (alist.get(i).equals(blist.get(j))) {
                            // 不存在重复数据
                            if (!cflist.isEmpty()) {
                                boolean bo = true;
                                // 遍历重复数据集
                                for (int k = 0; k < cflist.size(); k++) {
                                    // 取出第k行的重复数据
                                    String[] cf = cflist.get(k).toString()
                                            .split("-");
                                    // 取出第k行的第二个索引（复制数据的索引+1）
                                    int s = Integer.parseInt(cf[1].toString());
                                    // 比较当前顺序和重复中的顺序，如果相等则标记为false
                                    if (s == (i + 1)) {
                                        bo = false;
                                    }
                                }
                                // 判断是否添加到重复数据集中（true表示重复数据集里面不存在该数据，false反之）
                                if (bo) {
                                    cflist.add((i + 1) + "-" + (j + 1));
                                    if (b) {
                                        theIndex += ",";
                                    }
                                    theIndex += (j + 1);
                                    // 记录重复数据时修改b的值，表示和第i行和第j行存在重复数据
                                    b = true;
                                }
                            } else {
                                // 记录重复的数据，格式为2-3（第一个数字为原重复数据的索引+1，第二个为复制数据的索引+1）
                                cflist.add((i + 1) + "-" + (j + 1));
                                theIndex += (j + 1);
                                b = true;
                            }
                        }
                    }
                }
                if (b) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Ttuition> readXls1(String path) throws Exception {
        InputStream is = new FileInputStream(path);
        List<Ttuition> list = new ArrayList<Ttuition>();
        List<String> listpro = new ArrayList<String>();
        Workbook wb  = null;
        if(path.endsWith("xlsx")){
            wb = new XSSFWorkbook(is);
        }else{
            wb = new HSSFWorkbook(is);
        }

        Ttuition tt = null;

        // 循环工作表Sheet
        Sheet hssfSheet = wb.getSheetAt(0);
        if (hssfSheet == null) {
            return null;
        }
        // 循环行Row
        for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++)
        {
            Row hssfRow = hssfSheet.getRow(rowNum);
            if (hssfRow == null) {
                continue;
            }
            tt = new Ttuition();
            // 循环列Cell

            Cell studentId = hssfRow.getCell(0);
            if (studentId == null) {
                tt.setStudentId(null);
                //studentName;
            }else{
                tt.setStudentId(getValue(studentId));
                listpro.add(getValue(studentId));
            }
            Cell studentName = hssfRow.getCell(1);
            if (studentName == null) {
                tt.setStudentName(null);
                // continue;
            }else{
                tt.setStudentName(getValue(studentName));
            }

            Cell tuition = hssfRow.getCell(2);
            if (tuition == null) {
                tt.setTuition(0);
                //  continue;
            }else{
                tt.setTuition(Double.valueOf(getValue(tuition)));
            }

            Cell accommodation = hssfRow.getCell(3);
            if (accommodation == null) {
                tt.setAccommodation(0);
                //  continue;
            }else{
                tt.setAccommodation(Double.valueOf(getValue(accommodation)));
            }

//            Cell payStatus = hssfRow.getCell(4);
//            if (payStatus == null) {
//                tt.setPayStatus(0);
//                // continue;
//            }else{
//                tt.setPayStatus(Double.valueOf(getValue(payStatus)).intValue());
//            }


            Cell reserve1 = hssfRow.getCell(4);
            if (reserve1 == null) {
                tt.setReserve1("0");
                //continue;
            }else{
                String value=getValue(reserve1);;
                tt.setReserve1(String.valueOf(value));
            }
            tt.setCreateAt(DateTime4J.getCurrentDateTime());
            tt.setPayStatus(0);
            list.add(tt);

        }
        Boolean flag = check(listpro);
        if(!flag){
            return null;
        }
        return list;
    }
}
