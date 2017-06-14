package user;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ebeijia.robot.core.pojo.TAllocationUser;
import com.ebeijia.robot.core.util.PasswordHandler;
import com.ebeijia.robot.core.web.DataMap;
import com.ebeijia.robot.core.web.RequestMessage;
import com.ebeijia.robot.service.IAllocationUserService;

/**
 * 用户相关接口测试
 * @author lijm
 * @date 2016-12-19
 */
@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类  
@ContextConfiguration(locations = {"classpath:spring-console.xml"}) 
public class UserTest {
	
	@Autowired
	private IAllocationUserService userService;
	
	Map<String,Object> initParam = new HashMap<String, Object>();
	
	private static Logger logger = Logger.getLogger(UserTest.class);
	
	@Test
	public void testUser(){
		
		RequestMessage message = new RequestMessage();
		message.setSendTime("1478585569964");
		message.setAppType("02");
		message.setAuthToken("HIVIDEO8W2JJW4KYDP2YMKWFX36123");
		
		DataMap<String, Object> data = new DataMap<String, Object>();		
		data.put("user", "15000743278");
		message.setDataMap(data);
		
		Map<String,Object> map = message.getDataMap();
		
		String userId = "15837883627";//(String) data.get("userId");
		String password = "888888";//(String) data.get("pwd");
		String userName = "李保龙";//(String) data.get("userName");
		String mobile = "68329001";//(String) data.get("mobile");
		String branch = "01";//(String) data.get("branch");
		String state = "01";//(String) data.get("state");
		
		try {
			TAllocationUser tUser = new TAllocationUser();
			
			tUser.setUserId(userId);
			tUser.setPwd(PasswordHandler.getPassword(password));
			tUser.setUserName(userName);
			tUser.setMobile(mobile);
			tUser.setBranch(branch);
			tUser.setState(state);
			userService.addAllocationUser(tUser);
			
		} catch (Exception e) {
			
			logger.error("获取用户是否登陆y异常",e);
		}
		
	}
	 

}
