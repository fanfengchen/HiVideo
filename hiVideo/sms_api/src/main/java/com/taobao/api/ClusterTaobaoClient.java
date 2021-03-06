package com.taobao.api;

import com.taobao.api.internal.cluster.ClusterManager;
import com.taobao.api.internal.cluster.DnsConfig;
import com.taobao.api.internal.util.WebUtils;

/**
 * 异地多活自动分配集群客户端。
 * 
 * @author fengsheng
 * @since Sep 7, 2015
 */
public class ClusterTaobaoClient extends DefaultTaobaoClient {

	static {
		WebUtils.setIgnoreHostCheck(true);
	}

	public ClusterTaobaoClient(String serverUrl, String appKey, String appSecret) throws ApiException {
		super(serverUrl, appKey, appSecret);
		ClusterManager.initRefreshThread(this);
	}

	public ClusterTaobaoClient(String serverUrl, String appKey, String appSecret, String format) throws ApiException {
		super(serverUrl, appKey, appSecret, format);
		ClusterManager.initRefreshThread(this);
	}

	public ClusterTaobaoClient(String serverUrl, String appKey, String appSecret, String format, int connectTimeout, int readTimeout) throws ApiException {
		super(serverUrl, appKey, appSecret, format, connectTimeout, readTimeout);
		ClusterManager.initRefreshThread(this);
	}

	public ClusterTaobaoClient(String serverUrl, String appKey, String appSecret, String format, int connectTimeout, int readTimeout, String signMethod) throws ApiException {
		super(serverUrl, appKey, appSecret, format, connectTimeout, readTimeout, signMethod);
		ClusterManager.initRefreshThread(this);
	}

	protected String getServerUrl(String serverUrl, String apiName, String session) {
		DnsConfig dnsConfig = ClusterManager.GetDnsConfigFromCache();
		if (dnsConfig == null) {
			return serverUrl;
		} else {
			return dnsConfig.getBestVipUrl(serverUrl, apiName, session);
		}
	}

	protected String getSdkVersion() {
		return Constants.SDK_VERSION_CLUSTER;
	}
}
