package com.luckyhua.webmvc.result.json;

import com.luckyhua.webmvc.context.MsgConfig;
import com.luckyhua.webmvc.context.model.PageInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author luckyhua
 * @Description:  global return json result
 */
public class JsonResult implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final String Page_Data_Key = "pageData";
	public static final String Page_Info_Key = "pageInfo";

	private Integer code;
	private String msg;
	private Map<String,Object> data = new HashMap<>();

	public JsonResult(Integer code) {
		this.code = code;
		this.msg = MsgConfig.getMsg(code);
	}

	public void setPageInfo(PageInfo pageInfo){
		if(pageInfo == null)
			return;
		this.data.put(Page_Info_Key, pageInfo);
	}
	
	/**
	 * this method will put list to return json data using key<p>
	 * pageData,and the fields is that bean's field need to be convert to<p>
	 * json witch in list<p>
	 * @param pageData return data
	 * @param fields return fields
	 */
	public void setPageData(List<?> pageData, String ...fields){
		this.putListData(Page_Data_Key, pageData, fields);
	}

	public void setPage(PageInfo pageInfo, List<?> pageData, String ...fields){
		this.putListData(Page_Data_Key, pageData, fields);
		this.data.put(Page_Info_Key, pageInfo);
	}

	public void putData(String key, Object value){
		if(value instanceof Describle){
			this.data.put(key, ((Describle) value).describe());
			return;
		}
		this.data.put(key, value);
	}
	/**
	 * use the bean class simple name to key witch the first work is lower case
	 * @param bean the field that return to app
	 * @param fields
	 */
	public void putBeanData(Object bean, String ...fields){
		String key = ResultDataManager.getBeanName(bean);
		Map<String, Object> values = ResultDataManager.getValues(bean, fields);
		this.data.put(key, values);
	}

	/**
	 * use the user key to put bean <p>
	 * @see #putBeanData(Object, String...)
	 * @param key key in return json
	 * @param bean data
	 * @param fields fields is bean data
	 */
	public void putBeanData(String key,Object bean,String ...fields){
		if(bean instanceof Map){
			data.put(key, bean);
			return;
		}

		if(fields==null||fields.length==0){
			ResultDataManager.initIntValue(bean);
			data.put(key, bean);
			return;
		}
		Map<String, Object> values = ResultDataManager.getValues(bean, fields);
		data.put(key, values);
	}
	/**
	 * put list into data use customer key
	 * @see #setPageData(List, String...)
	 * @param key out json key
	 * @param listBean out json list bean
	 * @param inFields out json bean fields
	 */
	@SuppressWarnings("unchecked")
	public void putListData(String key, List<?> listBean, String ...inFields){

		List<Map<String,Object>> retData = new ArrayList<>();

		if(listBean == null){
			data.put(key, new ArrayList<>());
			return;
		}

		for(Object bean:listBean){
			if(bean instanceof Map){
				retData.add((Map<String, Object>) bean);
				continue;
			}

			if(bean instanceof Describle){
				Map<String, Object> values = ((Describle) bean).describe();
				retData.add(values);
				continue;
			}
			Map<String, Object> values = ResultDataManager.getValues(bean, inFields);
			retData.add(values);
		}
		data.put(key, retData);
	}

	/**
	 * not suggest to use this method,this method will return the<p>
	 * field witch not in exclude fields
	 * @param bean
	 * @param excludeFields the field not return
	 */
	@Deprecated
	public void putBeanDataExcludeFields(Object bean, String ...excludeFields){
		String key = ResultDataManager.getBeanName(bean);
		Map<String, Object> values = ResultDataManager.getValuesExcludeFields(bean, excludeFields);
		data.put(key, values);
	}
	
	public void putBeanDataAll(Object bean){
		String key = ResultDataManager.getBeanName(bean);
		data.put(key, bean);
	}

	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "JsonResult{" +
				"code='" + code + '\'' +
				", msg='" + msg + '\'' +
				", data=" + data +
				'}';
	}
}
