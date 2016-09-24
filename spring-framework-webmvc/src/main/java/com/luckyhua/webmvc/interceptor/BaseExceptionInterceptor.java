package com.luckyhua.webmvc.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luckyhua.webmvc.context.Logable;
import com.luckyhua.webmvc.exception.BaseException;
import com.luckyhua.webmvc.result.ResultUtils;
import com.luckyhua.webmvc.result.json.JsonResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * base exception interceptor that use spring mvc interceptor<p>
 * for now only have this one interceptor
 * @see {@link HandlerExceptionResolver}
 * @author luckyhua
 */
public class BaseExceptionInterceptor implements HandlerExceptionResolver,Logable {

	// 自定义消息转换器，负责将异常信息转换为json
	private HttpMessageConverter<JsonResult> jsonMessageConverter;
	
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		ModelAndView model = resolveExceptionJson(request,response,handler,ex);
		return model;
	}

	// 异常解析方法
	// 形参是捕获的异常
	// 返回的结果就是解析后的异常
	private BaseException exceptionResolver(Exception ex) {

		if(log.isDebugEnabled())
			log.error("error!",ex);
		
		BaseException BaseException = null;
		// 如果是本系统自定义的异常直接获取
		if (ex instanceof BaseException) {
			BaseException = (BaseException) ex;
			// TODO to do some thing
		}else if(ex instanceof MultipartException){
			BaseException = new BaseException(9001);
		}else if(ex instanceof DataIntegrityViolationException){
			BaseException = new BaseException(9009);
		}else {
			if(log.isInfoEnabled()){
				log.error("error:", ex);
			}
			// 如果不是本系统自定义的异常构造一个未知错误异常
			BaseException = new BaseException(9000);
		}
		return BaseException;

	}

	// 此方法对异常信息转json输出到客户端
	private ModelAndView resolveExceptionJson(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

		// 异常解析
		BaseException baseException = exceptionResolver(ex);
		if(log.isInfoEnabled()){
			log.info("request ip:" + request.getRemoteAddr());
		}
		
		// http的消息输出处理器
		response.setContentType("application/json;charset=utf-8");
//		response.setCharacterEncoding("Utf-8");

		HttpOutputMessage httpOutputMessage = new ServletServerHttpResponse(
				response);
		// 通过消息转换器将异常信息转换成json
		
		String callback = request.getParameter("callback");
		String jsonp = request.getParameter("jsonp");
		try {
			// 将异常信息转json输出到客户端
			if(StringUtils.isBlank(callback) && StringUtils.isBlank(jsonp)){
				jsonMessageConverter.write(
						ResultUtils.getErrorResult(baseException),
						MediaType.APPLICATION_JSON, httpOutputMessage);
			}else{
				String outJsonpStr = "";
				if(StringUtils.isNotBlank(callback))
					outJsonpStr = callback;
				else if(StringUtils.isNotBlank(jsonp))
					outJsonpStr = jsonp;
				String jsonpStr = outJsonpStr + "(" + new ObjectMapper().writeValueAsString(ResultUtils.getErrorResult(baseException)) + ");";
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().write(jsonpStr);
				response.getWriter().flush();
			}
		} catch (HttpMessageNotWritableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ModelAndView();
	}

	public HttpMessageConverter<JsonResult> getJsonmessageConverter() {
		return jsonMessageConverter;
	}

	public void setJsonmessageConverter(
			HttpMessageConverter<JsonResult> jsonMessageConverter) {
		this.jsonMessageConverter = jsonMessageConverter;
	}

}
