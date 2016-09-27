package com.luckyhua.webmvc.context;

import org.apache.commons.lang.StringUtils;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 
 * ClassName:MsgConfig
 * Description:reader config file
 *
 * @author luckyhua
 * @Date 2015年6月14日下午8:08:34
 *
 */
public class MsgConfig implements Logable{

	private static Map<String, String> msgMap = new HashMap<>();
	private static Map<String, Map<String,String>> msgCache = new HashMap<>();
	
	static{
		try {
			msgMap = loadMapByConfigPath("config/messages", msgMap);
		} catch (Exception e) {
			log.error("msg init fail!", e.getMessage());
		}
	}
	
	/**
	 * 
	  * @Title: getMsg
	  * @Description: get value by code
	  * @param code properties code
	  * @return String
	  *
	 */
	public static String getMsg(String code){
		return StringUtils.isBlank(code) ? null : msgMap.get(code);
	}

	public static String getMsg(Integer code){
		return code == null ? null : getMsg(code + "");
	}
	
	/**
	 * 
	  * @Title: getMsg
	  * @Description: get value by code and param array
	  * @param code properties code
	  * @param params properties params
	  * @return String
	  *
	 */
	public static String getMsg(String code, Object ... params){
		return MessageFormat.format(getMsg(code), params);
	}

	public static String getMsg(Integer code, Object ... params){
		return MessageFormat.format(getMsg(code), params);
	}
	
	/**
	 * this method to get one value from properties find by the path<P>
	 * this will cache the all key-value in properties to mem <P>
	 * that mean one path only read one times
	 * @param path	properties bundle location,like core/message
	 * @param key properties key
	 * @return String
	 */
	public static String getMsgFromResource(String path, String key){

		Map<String, String> oldMap = msgCache.get(path);
		if(oldMap != null){
			String value = oldMap.get(key);
			if(StringUtils.isNotBlank(value))
				return value;
		}

		try {
			Map<String, String> newMsg = loadMapByConfigPath(path, new HashMap<String, String>());
			msgCache.put(path, newMsg);
			return newMsg.get(key);
		} catch (Exception e) {
			log.error("msg init fail!", e.getMessage());
		}
		return null;
	}

	private static Map loadMapByConfigPath(String path, Map<String, String> msgMap_temp) {

		ResourceBundle bundle = ResourceBundle.getBundle(path);
		Enumeration<String> keys = bundle.getKeys();
		while(keys.hasMoreElements()){
			String key = keys.nextElement();
			msgMap_temp.put(key, bundle.getString(key));
			if (log.isDebugEnabled()) {
				log.debug("init one msg key:{}---------msg:{}", key, bundle.getString(key));
			}
		}
		return msgMap_temp;
	}
}
