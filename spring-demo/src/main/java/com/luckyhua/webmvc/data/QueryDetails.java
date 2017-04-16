package com.luckyhua.webmvc.data;

import com.luckyhua.webmvc.context.model.SortInfo;
import com.luckyhua.webmvc.data.page.PageableBean;

public class QueryDetails <T> extends PageableBean {
	
	private SortInfo sortInfo = new SortInfo();

	private T queryVo;
	
	private DateDetails dateDetails;
	
	protected QueryDetails(){}
	
	public T getQueryVo() {
		return queryVo;
	}

	public void setQueryVo(T queryVo) {
		this.queryVo = queryVo;
	}
	
	public void addOrderByColumn(String column,String sort){
		this.sortInfo.addOrderByColumn(column, sort);
	}
	
	public String getOrderByColumn(){
		return this.sortInfo.getOrderByColumn();
	}
	
	public DateDetails getDateDetails() {
		return dateDetails;
	}

	public void setDateDetails(DateDetails dateDetails) {
		this.dateDetails = dateDetails;
	}

	public static <T> QueryDetails<T> create(T queryVo){
		QueryDetails<T> queryDetails = new QueryDetails<T>();
		queryDetails.setQueryVo(queryVo);
		return queryDetails;
	}
	
}
