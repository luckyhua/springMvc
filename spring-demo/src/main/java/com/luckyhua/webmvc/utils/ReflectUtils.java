package com.luckyhua.webmvc.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReflectUtils {
	private static Log log = LogFactory.getLog(ReflectUtils.class);
	
	public static Method getReadMethod(Class beanClass, String fieldName){
		PropertyDescriptor pd;
		try {
			pd = new PropertyDescriptor(fieldName, beanClass);
			return pd.getReadMethod();
		} catch (IntrospectionException e) {
			e.printStackTrace();
			log.warn("not such method:"+fieldName);
		}
		return null;
	}
	
	public static Method getWriteMethod(Class beanClass, String fieldName){
		PropertyDescriptor pd;
		try {
			pd = new PropertyDescriptor(fieldName, beanClass);
			return pd.getWriteMethod();
		} catch (IntrospectionException e) {
			e.printStackTrace();
			log.warn("not such method:"+fieldName);
		}
		return null;
	}
	
	public static void setValue(Object bean,String field,Object value){
		try {
			Method m = getWriteMethod(bean.getClass(), field);
			if(m==null)
				return;
			Class<?>[] pts = m.getParameterTypes();
			if(pts==null||pts.length!=1){
				return;
			}
//			if(value.getClass() != pts[0]){
//				return;
//			}
			BeanUtils.setProperty(bean, field, value);
//			Class<?> needType = pts[0];
//			
//			if(needType == String.class){
//				
//			}else if(needType == Integer.class){
//				Integer.
//			}else if(needType == Double.class){
//				
//			}else if(needType == Long.class){
//				
//			}else if(needType == String.class){
//				
//			}else if(needType == Date.class){
//				
//			}else{
//				
//			}
//			
//			m.invoke(bean, value);
		} catch (Exception e) {
			log.error("set value failed!");
			throw new RuntimeException("set value exception!");
		}
	}

	/**
	 * get value from bean witch the field name is given<p>
	 * notice that this method would not return null if the field is number<p>
	 * @param source
	 * @param fieldName
	 * @return
	 */
	public static Object getValue(Object source, String fieldName){
		return getValue(source,fieldName,false);
	}
	/**
	 * return value from source <p>
	 * if needNull is true this method will return null number<p>
	 * @param source
	 * @param fieldName
	 * @param needNull
	 * @return
	 */
	public static Object getValue(Object source, String fieldName,boolean needNull){
		if(source == null || fieldName == null)
			return null;
		try {
			PropertyDescriptor pd = new PropertyDescriptor(fieldName,
					source.getClass());
			Object invoke = pd.getReadMethod().invoke(source);
			if(invoke != null || needNull)
				return invoke;
			
			Class<?> returnType = pd.getReadMethod().getReturnType();
			if(returnType == Integer.class){
				return 0;
			}
			if(returnType == Double.class){
				return 0.0d;
			}
			if(returnType == Long.class){
				return 0.0d;
			}
			
			return invoke;
		} catch (Exception e) {
			log.warn("get value fail!:"+e.getMessage()+" fieldName:"+fieldName);
		}
		return null;
	}

	public static List<String> getFieldNames(Class beanClass){
		if(beanClass == null)
			return null;
		
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(beanClass, Object.class);
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			
			List<String> names = new ArrayList<String>();
			for (PropertyDescriptor pd : pds) {
				String name = pd.getName();
				names.add(name);
			}
			return names;
		} catch (IntrospectionException e) {
			log.warn("get names fails:"+e.getCause());
		}

		return null;
	}

	public static Map<String, Object> describe(Object bean,boolean needNull){
		if(bean == null)
			return null;
		
		Map<String, Object> des = new HashMap<String, Object>();

		List<String> names = getFieldNames(bean.getClass());
		for (String name : names) {
			Object value = getValue(bean, name);
			if (value!=null||(value == null)==needNull)
				des.put(name, value);
		}
		return des;
	}
	
	public static Map<String,Object> describe(Object bean){
		return describe(bean,false);
	}
	
//	@Test
//	public void testMap(){
//		User u = new User();
//		u.setMobile("13312341234");
//		
//		Map<String, Object> properties = describe(u);
//	}

}
