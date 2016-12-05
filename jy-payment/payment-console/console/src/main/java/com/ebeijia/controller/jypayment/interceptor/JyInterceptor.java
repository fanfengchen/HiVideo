package com.ebeijia.controller.jypayment.interceptor;

import com.ebeijia.ajax.resp.AjaxResp;
import com.ebeijia.api.BaseValidRoleFunc;
import com.ebeijia.util.core.RespCode;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by zc on 2016/9/20.
 * 用于验证访问连接时是否已经登入
 */
public class JyInterceptor extends BaseValidRoleFunc implements HandlerInterceptor {
    private static Logger logger = LoggerFactory.getLogger(JyInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        logger.debug("--------------------------------------ticket interceptor------------------------------------------------");

        //下载操作不拦截
        String servletPath = request.getServletPath();
        if(servletPath != null && servletPath.indexOf("download") > -1){
            return true;
        }

        String userId = request.getParameter("usrId");
        String token = request.getParameter("token");
        String roleId = request.getParameter("roleId");

        //只判断token失效
        String resData = this.validRoleFunc(userId, token, roleId, "0");
        logger.debug("---------------------------------------------------------------" + resData);
        if(resData != null && "tokenLose".equals(resData)){
            Map<String, Object> map = AjaxResp.getReturn(RespCode.TOKEN_CODE, "token失效", "");
            PrintWriter out = null;
            try{
                out = response.getWriter();
                JSONObject js = JSONObject.fromObject(map);
                out.write(js.toString());
            }catch(Exception e){
                logger.error("ticket interceptor error : " + e);
            }finally {
                if(out != null){
                    out.close();
                }
            }
            return false;
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
