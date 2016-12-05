package com.ebeijia.util.wechat;

import com.ebeijia.entity.wechat.base.TblWechatArticlesInf;
import com.ebeijia.util.core.RespCode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * 微信工具类
 */
public class WechatUtil {
	private static Logger log = LoggerFactory.getLogger(WechatUtil.class);
	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String ACCESS_TOKEN_H5_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=APPSECRET&code=CODE&grant_type=authorization_code";
	// 菜单创建（POST） 限100（次/天）
	public static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// 菜单查询（GET） 限100（次/天）
	public static String MENU_GET_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	// 菜单删除（GET） 限100（次/天）
	public static String MENU_DEL_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	//一个公共号最多100个分组
	public static String GROUPS_CREAT_URL = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";
	//查询所有分组
	public static String GROUPS_GET_URL = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
	//修改分组名
	public static String GROUP_MOD_URL = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN";
	//删除分组
	public static String GROUP_DEL_URL = "https://api.weixin.qq.com/cgi-bin/groups/delete?access_token=ACCESS_TOKEN";
	//移动用户分组
	public static String USR_GROUP_URL = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN";
	//批量移动用户分组
	public static String USRS_GROUP_URL = "https://api.weixin.qq.com/cgi-bin/groups/members/batchupdate?access_token=ACCESS_TOKEN";
	//修改用户备注名
	public static String USR_UPD_REMARK = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=ACCESS_TOKEN";
	//获取用户信息
	public static String USR_INF_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	// 获取关注者列表（第一页）
	public static String SUBSCRIBE_FIRST_PAGE_URL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN";
	//获取关注者列表(下一页)
	public static String SUBSCRIBE_NEXT_PAGE_URL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
	//请求二维码
	public static String QR_CODE_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
	//上传临时素材
	public static String MEDIA_UPLOAD_TMP_URL="https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	//获取临时素材
	public static String MEDIA_DOWLOAD_TMP_URL="https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
	//获取永久素材
	public static String MEDIA_DOWLOAD_URL="https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";
	//上传永久图文素材
	public static String MEDIA_UPLOAD_GT_URL="https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=ACCESS_TOKEN";
	//修改图文
	public static String MEDIA_NEWS_UPDATE = "https://api.weixin.qq.com/cgi-bin/material/update_news?access_token=ACCESS_TOKEN";
	//上传永久其他素材
	public static String MEDIA_UPLOAD_URL="https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN";
	//获取素材总数
	public static String MEDIA_COUNTALL_URL="https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=ACCESS_TOKEN";
	//删除永久素材
	public static String MEDIA_DEL_URL = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=ACCESS_TOKEN";
	//获取图文列表
	public static String MEDIA_NEWS_GET = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";

	//群发 上传图文消息素材
	public static String MASS_UP_NEWS ="https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
	//群发消息上传视频
	public static String MASS_VIDEO_SEND ="https://file.api.weixin.qq.com/cgi-bin/media/uploadvideo?access_token=ACCESS_TOKEN";
	//群发所有，组别
	public static String MASS_All ="https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
	//群发用户
	public static String MASS_USR ="https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
	//删除群发
	public static String MASS_DEL ="https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token=ACCESS_TOKEN";
	//群发预览
	public static String MASS_PREVIEW ="https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=ACCESS_TOKEN";
	//查询群发发送消息状态
	public static String MASS_STATUS ="https://api.weixin.qq.com/cgi-bin/message/mass/get?access_token=ACCESS_TOKEN";
	//添加多客服
	public static String KF_ADD ="https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN";
	//修改多客服
	public static String KF_UPD ="https://api.weixin.qq.com/customservice/kfaccount/update?access_token=ACCESS_TOKEN";
	//删除多客服
	public static String KF_DEL ="https://api.weixin.qq.com/customservice/kfaccount/del?access_token=ACCESS_TOKEN&kf_account=KFACCOUNT";
	//设置多客服头像
	public static String KF_UPHEAD ="http://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?access_token=ACCESS_TOKEN&kf_account=KFACCOUNT";
	//设置模板所属行业
	public static String TEMPLATE_SET ="https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN";
	//设置模板id
	public static String TEMPLATE_ADD ="https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN";
	//发送模板消息
	public static String TEMPLATE_SEND ="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	//获取jsTicket
	public static String JSTICKET_GET ="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	//自动回复规则
	public static String CURRENT_AUTOREPLY_INFO = "https://api.weixin.qq.com/cgi-bin/get_current_autoreply_info?access_token=ACCESS_TOKEN";

	//	json转map
	@SuppressWarnings("rawtypes")
	public static Map jsonToMap(String json){
		ObjectMapper objMapper = new ObjectMapper();
		Map map =null;
		if(json !=null){
			try {
				map = objMapper.readValue(json, Map.class);
			} catch (JsonParseException e) {
				log.error("json:{} 转换map时出错,error:{}",json,e);
			} catch (JsonMappingException e) {
				log.error("json:{} 转换map时出错,error:{}",json,e);
			} catch (IOException e) {
				log.error("json:{} 转换map时出错,error:{}",json,e);
			}
		}
		return map;
	}

	/**
	 * 解析微信接口返回的状态
	 *
	 * @param result
	 * @return
	 */
	public static int getWechatCallBackStatus(String result) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Map<String, Object> map = objectMapper.readValue(result, Map.class);
			if (map.get("errcode") != null && (Integer) map.get("errcode") != 0) {
				return (Integer) map.get("errcode");
			}
		} catch (Exception e) {
			return RespCode.WECHAT_ERROR;
		}
		return RespCode.WECHAT_SUCCESS;
	}

    public static List<TblWechatArticlesInf> forBeanList(String articles) {
        String artStr = articles.replace("thumb_media_id","thumbMediaId");
        List<TblWechatArticlesInf> list = new ArrayList<TblWechatArticlesInf>();
        JSONObject artObj = JSONObject.fromObject(artStr);
        JSONArray artarr = JSONArray.fromObject(artObj.get("articles"));
        for (int i = 0; i < artarr.size(); i++) {
            JSONObject obj = (JSONObject) artarr.get(i);
            TblWechatArticlesInf twa = (TblWechatArticlesInf) obj.toBean(obj, TblWechatArticlesInf.class);
            list.add(twa);
        }
        return list;
    }

    public static String forArticles(String artStr,String path[]){
        JSONObject baseObj = new JSONObject();
        JSONArray basearr = new JSONArray();
        JSONObject artObj = JSONObject.fromObject(artStr);
        JSONArray artarr = JSONArray.fromObject(artObj.get("articles"));
        for (int i = 0; i < artarr.size(); i++) {
            JSONObject obj1 = JSONObject.fromObject(((JSONObject)artarr.get(i)).toString().replace("CONTENT_SOURCE_URL",path[i]));
            basearr.add(i,obj1);
        }
        baseObj.put("articles",basearr);
        return baseObj.toString();
    }

	public static String forArticle(String artStr,String path[]){
		JSONObject baseObj = new JSONObject();
		JSONObject artObj = JSONObject.fromObject(artStr);
		JSONArray artarr = JSONArray.fromObject(artObj.get("articles"));
		JSONObject obj1 = JSONObject.fromObject(((JSONObject)artarr.get(0)).toString().replace("CONTENT_SOURCE_URL",path[0]));
		baseObj.put("articles",obj1);
		return baseObj.toString();
	}
	//返回图文数组
	public static String[] forArticleArr(String artStr,String path[]){
		JSONObject baseObj = new JSONObject();
		JSONObject artObj = JSONObject.fromObject(artStr);
		JSONArray artarr = JSONArray.fromObject(artObj.get("articles"));
		String [] artArr = new String[artarr.size()];
		for (int i = 0; i < artarr.size(); i++) {
			JSONObject obj1 = JSONObject.fromObject(((JSONObject)artarr.get(i)).toString().replace("CONTENT_SOURCE_URL",path[i]));
			baseObj.put("articles",obj1);
			artArr[i] = baseObj.toString();
		}
		return artArr;
	}

    public static String[] forContents(String artStr) {
        JSONObject baseObj = new JSONObject();
        JSONArray basearr = new JSONArray();
        JSONObject artObj = JSONObject.fromObject(artStr);
        JSONArray artarr = JSONArray.fromObject(artObj.get("articles"));
		String contents []= new String[artarr.size()];
        for (int i = 0; i < artarr.size(); i++) {
            String content = ((JSONObject) artarr.get(i)).get("content").toString();
            contents[i] = content;
        }
        return contents;
    }

    public static void main(String args[]){
//        forBeanList(null);
//        String artStr = "{\"articles\":[{\"title\":\"新建图文\",\"author\":\"fdfd\",\"content\":\"<p>cp content</p>\",\"content_source_url\":\"CONTENT_SOURCE_URL\",\"thumb_media_id\":\"S6wP8msuH1aa-Ebvp0-LAEdEzl8Lk-9eB1or5DGCcDI\",\"show_cover_pic\":1,\"digest\":\"dd\"},{\"title\":\"新建图文\",\"author\":\"fdfd\",\"content\":\"<p>cp content</p>\",\"content_source_url\":\"CONTENT_SOURCE_URL\",\"thumb_media_id\":\"S6wP8msuH1aa-Ebvp0-LAEdEzl8Lk-9eB1or5DGCcDI\",\"show_cover_pic\":1,\"digest\":\"dd\"},{\"title\":\"新建图文\",\"author\":\"fdfd\",\"content\":\"<p>cp content</p>\",\"content_source_url\":\"CONTENT_SOURCE_URL\",\"thumb_media_id\":\"S6wP8msuH1aa-Ebvp0-LAEdEzl8Lk-9eB1or5DGCcDI\",\"show_cover_pic\":1,\"digest\":\"dd\"}]}";
//        JSONObject baseObj = new JSONObject();
//        JSONArray basearr = new JSONArray();
//        JSONObject artObj = JSONObject.fromObject(artStr);
//        JSONArray artarr = JSONArray.fromObject(artObj.get("articles"));
//        for (int i = 0; i < artarr.size(); i++) {
//            JSONObject obj =(JSONObject)artarr.get(i);
//            JSONObject obj1 = JSONObject.fromObject(obj.toString().replace("CONTENT_SOURCE_URL","http://www.baidu.com"));
//            basearr.add(i,obj1);
//        }
//        baseObj.put("articles",basearr);
//        System.out.print(baseObj.toString());

//        NewsInfoList<String> paths = new LinkedList<String>();
        String artStr = "{\"articles\":[{\"title\":\"新建图文\",\"author\":\"fdfd\",\"content\":\"<p>cp content</p>\",\"content_source_url\":\"CONTENT_SOURCE_URL\",\"thumb_media_id\":\"S6wP8msuH1aa-Ebvp0-LAEdEzl8Lk-9eB1or5DGCcDI\",\"show_cover_pic\":1,\"digest\":\"dd\"},{\"title\":\"新建图文\",\"author\":\"fdfd\",\"content\":\"<p>cp content</p>\",\"content_source_url\":\"CONTENT_SOURCE_URL\",\"thumb_media_id\":\"S6wP8msuH1aa-Ebvp0-LAEdEzl8Lk-9eB1or5DGCcDI\",\"show_cover_pic\":1,\"digest\":\"dd\"},{\"title\":\"新建图文\",\"author\":\"fdfd\",\"content\":\"<p>cp content</p>\",\"content_source_url\":\"CONTENT_SOURCE_URL\",\"thumb_media_id\":\"S6wP8msuH1aa-Ebvp0-LAEdEzl8Lk-9eB1or5DGCcDI\",\"show_cover_pic\":1,\"digest\":\"dd\"}]}";
//        JSONObject baseObj = new JSONObject();
//        JSONArray basearr = new JSONArray();
//        JSONObject artObj = JSONObject.fromObject(artStr);
//        JSONArray artarr = JSONArray.fromObject(artObj.get("articles"));
//        for (int i = 0; i < artarr.size(); i++) {
//            String path =((JSONObject)artarr.get(i)).get("content").toString();
//            paths.add(path);
//        }
//        baseObj.put("articles",basearr);

		JSONObject baseObj = new JSONObject();
		JSONArray basearr = new JSONArray();
		JSONObject artObj = JSONObject.fromObject(artStr);
		JSONArray artarr = JSONArray.fromObject(artObj.get("articles"));
		JSONObject obj1 = JSONObject.fromObject(((JSONObject)artarr.get(0)).toString().replace("CONTENT_SOURCE_URL","http://www.baidu.com"));
		baseObj.put("articles",obj1);
		System.out.print(baseObj.toString());
    }
}
