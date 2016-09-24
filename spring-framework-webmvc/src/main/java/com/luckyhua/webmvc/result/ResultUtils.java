package com.luckyhua.webmvc.result;

import com.luckyhua.webmvc.context.model.PageInfo;
import com.luckyhua.webmvc.exception.BaseException;
import com.luckyhua.webmvc.result.easyui.DataGridResult;
import com.luckyhua.webmvc.result.json.JsonResult;

import java.util.List;
import java.util.Map;


/**
 * 
 * @author luckyhua
 * @Description: return result format
 */
public class ResultUtils {

	public static final int DEFAULT_SUCCESS = 200;

	private static JsonResult buildJsonResult(Integer code) {
		return new JsonResult(code);
	}

	public static JsonResult getSuccessResult(){
		return getSuccessResult(DEFAULT_SUCCESS);
	}

	public static JsonResult getSuccessResult(Integer code){
		return buildJsonResult(code);
	}

	public static JsonResult getSuccessResult(String token){
		JsonResult json = getSuccessResult();
		json.getData().put("token", token);
		return json;
	}

	@Deprecated
	public static JsonResult getSuccessResult(Map<String,Object> data){
		JsonResult jsonResult = getSuccessResult();
		if(data != null)
			jsonResult.setData(data);
		return jsonResult;
	}

	public static JsonResult getErrorResult(Integer code){
		return buildJsonResult(code);
	}
	
	public static JsonResult getErrorResult(Exception e){
		if(e instanceof BaseException){
			Integer msgCode = ((BaseException) e).getMsgCode();
			return getErrorResult(msgCode);
		}
		return null;
	}
	
	public static DataGridResult getDataGridResult(PageInfo pageInfo, List<?> data){
		return new DataGridResult(pageInfo.getTotalCount(), data);
	}
}
