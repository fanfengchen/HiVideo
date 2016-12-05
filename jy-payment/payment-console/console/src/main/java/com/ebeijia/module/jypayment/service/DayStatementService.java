package com.ebeijia.module.jypayment.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created with com.ebeijia.module.jypayment.service
 * User : zc
 * Date : 2016/10/25
 */
public interface DayStatementService {

    public Map<String, Object> getDayStatement(String orderTimeStart,
                                               String orderTimeEnd,
                                               String orderType,
                                               String aoData) throws  Exception;

    Map<String,Object> getMonthStatement(String orderTimeStart,
                                         String orderTimeEnd,
                                         String orderType,
                                         String aoData) throws Exception;

    public Map<String, Object> downloadPayDetail(String orderTimeStart,
                                                 String orderTimeEnd,
                                                 String orderType,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception;

    Map<String, Object> downloadMonthDetail(String orderTimeStart,
                                          String orderTimeEnd,
                                          String orderType,
                                          HttpServletRequest request,
                                          HttpServletResponse response) throws Exception;
}
