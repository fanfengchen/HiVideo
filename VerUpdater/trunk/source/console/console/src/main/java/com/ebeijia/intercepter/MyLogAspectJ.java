package com.ebeijia.intercepter;

import com.ebeijia.annotation.MyLog;
import com.ebeijia.entity.system.log.TblSysTxnLog;
import com.ebeijia.entity.system.system.TblSysOperatorInf;
import com.ebeijia.module.system.service.OperTask.OperTaskSerivce;
import com.ebeijia.module.system.service.txnLog.SysTxnLogService;
import com.ebeijia.util.common.IpUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.ebeijia.tools.DateTime4J;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

@Component
@Aspect
public class MyLogAspectJ {
	
	@Autowired
	private  HttpServletRequest request;  
	@Autowired  
	private  HttpSession session;  
	@Autowired
	private SysTxnLogService txnLogService;
	@Autowired
	private OperTaskSerivce operTaskSerivce;
	
	@Pointcut("@annotation(com.ebeijia.annotation.MyLog)")
	public void methodCachePointcut() {
	}

	@Around("methodCachePointcut()")
	public Object methodCacheHold(ProceedingJoinPoint joinPoint)throws Throwable {
		long start = System.currentTimeMillis();
		String methodRemark = getMthodRemark(joinPoint);
		Object result = null;
		String chl = (methodRemark.indexOf("接口") == -1)? "1":"0";
		// 记录操作日志...调用日志记录方法
		try {
			result = joinPoint.proceed();
			this.save(start, methodRemark, null);
//			log.info(methodRemark+"操作成功");
		} catch (Exception e) {
			this.save(start, methodRemark, e);
			result = joinPoint.proceed();
		}
		return result;
	}

	private static String getMthodRemark(ProceedingJoinPoint joinPoint)
			throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] method = targetClass.getMethods();
		String methode = "";
		for (Method m : method) {
			if (m.getName().equals(methodName)) {
				Class[] tmpCs = m.getParameterTypes();
				if (tmpCs.length == arguments.length) {
					MyLog methodCache = m.getAnnotation(MyLog.class);
					methode = methodCache.remark();
					break;
				}
			}
		}
		return methode;
	}
	

	
	//获取客户端ip
	private String getRemoteHost(javax.servlet.http.HttpServletRequest request){
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}
	
	//保存日志
	public void save(long start, String methodRemark, Exception e){
//		String txnNo = getTxnNo();
		TblSysTxnLog tblTxnLog = new TblSysTxnLog();
		if(e == null){
			tblTxnLog.setTxnStatus("0");
		}else{
			tblTxnLog.setTxnStatus("1");
			String error = e.toString().length()>128?e.toString().substring(0, 128):e.toString();
			tblTxnLog.setTxnError(error);
		}
//		tblTxnLog.setTxnNo(txnNo);
		if(operTaskSerivce.getOper() == null){
		}else{
//			TblSysOperatorInf data =(TblSysOperatorInf) session.getAttribute("operator");
			tblTxnLog.setOperator(operTaskSerivce.getOper());
		}
		tblTxnLog.setRemoteAddr(IpUtil.getRemoteHost(request));
		tblTxnLog.setTxnDate(DateTime4J.getCurrentDateTime());
		tblTxnLog.setOperaTime(Long.toString(System.currentTimeMillis() - start));
		String chl = (methodRemark.indexOf("接口") == -1)? "1":"0";
		tblTxnLog.setTxnChl(chl);
		tblTxnLog.setTxnName(methodRemark);
		txnLogService.addTxnLog(tblTxnLog);
	}
}