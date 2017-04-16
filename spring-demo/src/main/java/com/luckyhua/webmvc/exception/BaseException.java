package com.luckyhua.webmvc.exception;

import com.luckyhua.webmvc.context.MsgConfig;

/**
 * the server base exception class<p>
 * notice that the base interceptor noly handle this class<p>
 * so if make the specific exception must extends this class<p>
 * @author luckyhua
 *
 */
public class BaseException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private String msg;

	private Integer msgCode;

	public BaseException() {
		super();
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(Integer msgCode) {
		this.msgCode = msgCode;
		this.msg = MsgConfig.getMsg(msgCode);
	}
	
	public BaseException(Integer msgCode, Object ... params){
		super(MsgConfig.getMsg(msgCode, params));
		this.msgCode = msgCode;
		this.msg = MsgConfig.getMsg(msgCode, params);
	}

	public String getMsg() {
		return msg;
	}

	public Integer getMsgCode() {
		return msgCode;
	}

}
