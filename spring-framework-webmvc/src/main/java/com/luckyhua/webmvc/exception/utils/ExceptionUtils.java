package com.luckyhua.webmvc.exception.utils;

import com.luckyhua.webmvc.exception.BaseException;

/**
 * @author luckyhua
 * @Description: global exception
 */
public class ExceptionUtils {

	public static void throwSimpleEx(int code) throws BaseException {
		throw new BaseException(code);
	}
	
	public static void throwSimpleEx(int code, Object ... params) throws BaseException{
		throw new BaseException(code, params);
	}
	
	public static void throwError() throws BaseException{
		throw new BaseException(9000);
	}
	
}
