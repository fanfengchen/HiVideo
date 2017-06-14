package com.ebeijia.robot.core.web;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataMap<K, V> extends LinkedHashMap<K, V> {

	public String getValueAsString(K key) {
		return (String) this.get(key);
	}

	public Object getValue(K key) {
		return this.get(key);
	}

	public List<Map<String, String>> getValueAsList(K key) {
		return (List<Map<String, String>>) this.get(key);
	}
	
	public List<Map<String, Object>> getValueObAsList(K key) {
		return (List<Map<String, Object>>) this.get(key);
	}

}
