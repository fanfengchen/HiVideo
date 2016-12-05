package com.ebeijia.videocore.web.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.ebeijia.videocore.constant.ApiResultCode;
import com.ebeijia.videocore.constant.CommonConstant;
import com.ebeijia.videocore.util.LoggerUtil;
import com.ebeijia.videocore.util.Md5;
import com.ebeijia.videocore.util.PropertiesUtils;
import com.ebeijia.videocore.util.TokenUtil;
import com.ebeijia.videocore.web.ResponseMessage;

public class AuthFilter implements Filter {

	public void destroy() {

	}

	private boolean isWorthyRequest(HttpServletRequest request) {
		Pattern excludeUrls = Pattern.compile("^.*/(jpg|bmp|png)/.*$",
				Pattern.CASE_INSENSITIVE);
		String url = request.getRequestURI().toString();
		Matcher m = excludeUrls.matcher(url);
		return (!m.matches());
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (isWorthyRequest((HttpServletRequest) request)) {
			chain.doFilter(request, response);

		} else {
			String authToken = request.getParameter("authToken");
			String signType = request.getParameter("signType");
			PrintWriter pw = new PrintWriter(response.getOutputStream());
			boolean hasError = false;
			try {
				if (StringUtils.isEmpty(authToken)) {
					pw.write(ResponseMessage.error("无效的请求").toString());
					hasError = true;
					return;

				}
				// 判定是否超时
				String defToken = PropertiesUtils
						.getProperties("token.authToken");
				if (!defToken.equals(authToken)
						&& TokenUtil.getToken(authToken) == null) {
					pw.write(ResponseMessage.error(
							ApiResultCode.Err_1003.toString(), "请重新登录")
							.toString());
					hasError = true;
					return;
				}
				// 验证签名
				String sign = request.getParameter("sign");
				String sendTime = request.getParameter("sendTime");
				String data = request.getParameter("data");

				LoggerUtil.info("AuthFilter :  sign " + sign+"||data:"+data+"||sendTime:"+sendTime);			

				StringBuffer sb = new StringBuffer();
				sb.append("sendTime=").append(sendTime).append("data=")
						.append(data);

				String waitSign = null;
				switch (signType == null ? CommonConstant.SIGN_TYPE_MD5
						: signType) {
				case CommonConstant.SIGN_TYPE_MD5:
					waitSign = Md5.md5Upper(sb.toString());
					break;

				default:
					waitSign = Md5.md5Upper(sb.toString());
					break;
				}
				if (!waitSign.equals(sign)) {
					pw.write(ResponseMessage.error(
							ApiResultCode.Err_3001.toString(), "请求不合法")
							.toString());
					hasError = true;
					return;
				}

			} finally {
				if (hasError) {
					pw.flush();
					pw.close();
				}
			}

			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
