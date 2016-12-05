package com.ebeijia.module.coffee.service;

import com.ebeijia.entity.coffee.TblCoupon;
import com.ebeijia.module.coffee.dao.CouponDao;
import com.ebeijia.util.core.SystemProperties;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.ebeijia.tools.DateTime4J;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lf on 2016/9/19.
 */
@Service
public class CouponServiceImpl implements CouponService {

    private Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);

    @Autowired
    private CouponDao couponDao;

    /**
     * 添加奖品类型
     * @param tblCoupon
     * @return
     */

    @Transactional
    public Map<String , Object> addCoupon(TblCoupon tblCoupon ) {
        Map<String ,Object> map =new HashMap<String ,Object>();
        if(!fieldVerification(tblCoupon)){
            map.put("resCode","-2");
            return map;
        }

        if(tblCoupon.getActiveDate().getTime() > tblCoupon.getExpireDate().getTime()){
            map.put("resCode","-3");
            return map;
        }
        TblCoupon coupon = new TblCoupon();
        coupon.setId(tblCoupon.getId());
        List<TblCoupon> list = couponDao.addCoupon(coupon);
        if(list != null && list.size()>0){
            map.put("resCode","-1");
            return map;
        }
        tblCoupon.setRecCount(0);
        tblCoupon.setState("0");
        tblCoupon = (TblCoupon) couponDao.save(tblCoupon);
        map.put("resCode","0");
        map.put("id",tblCoupon.getId());
        return map;
    }

    /**
     * 修改奖品
     * @param tblCoupon
     * @return
     */
    @Transactional
    public Map<String, Object> updateCoupon(TblCoupon tblCoupon) {
        Map<String ,Object> map = new HashMap<String, Object>();
        if(tblCoupon.getActiveDate().getTime() > tblCoupon.getExpireDate().getTime()){
            map.put("resCode","-3");
            return map;
        }
        if( tblCoupon.getId() == 0){
            map.put("resCode","-2");
            return map;
        }
        TblCoupon coupon = new TblCoupon();
        coupon.setId(tblCoupon.getId());
        List<TblCoupon> list = couponDao.addCoupon(tblCoupon);
        if(list != null && list.size()>0){
            if(list.size()>1){
                map.put("resCode","-1");
                return map;
            }
            if(list.get(0).getId() != tblCoupon.getId()){
                map.put("resCode","-1");
                return map;
            }

        }

        couponDao.updateCoupon(tblCoupon);
        map.put("resCode","0");
        return map;
    }

    @Transactional
    public Map<String ,Object> deleteCoupon(String id) {
        Map<String ,Object> map  = new HashMap<String, Object>();
        if(id != null || "".equals(id)){
            couponDao.deteleById(id);
            map.put("resCode","0");
            return map;
        }
        map.put("resCode","-1");
        return map;
    }

    /**
     * 查询奖品类型
     * @param aoData
     * @return
     */
    public Map<String, Object> queryCouponList(String aoData) {
        if(aoData == null || "".equals(aoData) || aoData.indexOf("[{") != 0){
            Map<String, Object> res = new HashMap<String, Object>();
            res.put("resCode", "-1");
            return res;
        }
        StringBuffer sql = new StringBuffer();
        sql.append(" from TblCoupon where state = '0' ");
        Map<String, Object> data = couponDao.queryByPage(sql.toString(), aoData);
        return dataConversion(data);
    }

    private Map<String, Object> dataConversion(Map<String, Object> map){
        Map<String, Object> res = new HashMap<String, Object>();
        List<TblCoupon> list = (List<TblCoupon>)map.get("list");
        JSONArray jsonArr = new JSONArray();
        for(TblCoupon coupon : list){
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("id",coupon.getId());
            jsonObj.put("activeDate",DateTime4J.dateTimeFormat(coupon.getActiveDate()));
            jsonObj.put("expireDate",DateTime4J.dateTimeFormat(coupon.getExpireDate()));
            jsonObj.put("couponName",coupon.getCouponName());
            jsonObj.put("couponIntegral",coupon.getCouponIntegral());
            jsonObj.put("couponDesc",coupon.getCouponDesc());
            jsonObj.put("imageUrl",coupon.getImageUrl());
            jsonObj.put("couponType",coupon.getCouponType());
            jsonArr.add(jsonObj);
        }
        res.put("list", jsonArr);
        res.put("count", map.get("total"));
        return res;
    }

    public boolean fieldVerification(TblCoupon tblCoupon){
        if(tblCoupon == null){
            return false;
        }
        if(StringUtils.isEmpty(tblCoupon.getActiveDate())){
            return false;
        }
        if(StringUtils.isEmpty(tblCoupon.getExpireDate())){
            return false;
        }
        if(StringUtils.isEmpty(tblCoupon.getCouponName())){
            return false;
        }
        if(StringUtils.isEmpty(tblCoupon.getCouponIntegral())){
            return false;
        }
        if(StringUtils.isEmpty(tblCoupon.getCouponDesc())){
            return false;
        }
        if(StringUtils.isEmpty(tblCoupon.getImageUrl())){
            return false;
        }
        if(StringUtils.isEmpty(tblCoupon.getCouponType())){
            return false;
        }
        return true;
    }

    public Map<String, Object> updateLoadImg(HttpServletRequest request, HttpServletResponse response){
        CommonsMultipartResolver multipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        Map<String, Object> resMap = new HashMap<String, Object>();
        if(multipartResolver.isMultipart(request)){ //判断request是否有文件上传
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            Iterator<String> ite = multiRequest.getFileNames();
            while(ite.hasNext()){
                MultipartFile file = multiRequest.getFile(ite.next());
                if(file!=null){

                    String path = SystemProperties.getProperties("coffee_images_path");

                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

                    String name = format.format(new Date());
                    String fileName = file.getOriginalFilename();
                    String newFileName = name + getRandStr() + fileName.substring(fileName.indexOf("."));

                    String fullFileName = path + newFileName;

                    File localFile = new File(fullFileName);
                    try {
                        file.transferTo(localFile); //将上传文件写到服务器上指定的文件
                        resMap.put("code", "0");
                        resMap.put("name", newFileName);
                        return resMap;
                    } catch (Exception e) {
                        logger.error("上传文件失败：" + e);
                    }
                }
            }
        }
        resMap.put("code", "-1");
        return resMap;
    }

    private String getRandStr(){
        int max = 99999;
        int min = 1;
        Random random = new Random();
        int c = random.nextInt(max)%(max-min+1) + min;
        return String.valueOf(c);
    }

    public TblCoupon getCouponByRequest(HttpServletRequest request) throws Exception {
        String id = request.getParameter("id");
        String activeDate = request.getParameter("activeDate");
        String expireDate = request.getParameter("expireDate");
        String couponName = request.getParameter("couponName");
        String couponIntegral = request.getParameter("couponIntegral");
        String recCount = request.getParameter("recCount");
        String couponDesc = request.getParameter("couponDesc");
        //为图片上传，需要从上传的文件中得到文件的名称
        //String imageUrl = request.getParameter("id");
        String couponType = request.getParameter("couponType");
        String state = request.getParameter("state");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        TblCoupon tbl = new TblCoupon();
        tbl.setId(id == null ? 0 : Integer.parseInt(id));
        tbl.setActiveDate(StringUtils.isEmpty(activeDate) ? null : format.parse(activeDate));
        tbl.setExpireDate(StringUtils.isEmpty(expireDate) ? null : format.parse(expireDate));
        tbl.setCouponName(couponName);
        tbl.setCouponIntegral(StringUtils.isEmpty(couponIntegral) ? null : Integer.parseInt(couponIntegral));
        tbl.setRecCount(StringUtils.isEmpty(recCount) ? null : Integer.parseInt(recCount));
        tbl.setCouponDesc(couponDesc);
        tbl.setCouponType(couponType);
        tbl.setState(state);

        return tbl;
    }
}
