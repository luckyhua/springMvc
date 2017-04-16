package com.luckyhua.webmvc.result.easyui;

import java.util.ArrayList;
import java.util.List;

public class DataGridResult {

	private Integer total;
	
	private List<?> rows = new ArrayList<>();
	
	public DataGridResult(Integer total,List<?> rows){
		this.total = total;
		this.rows = rows;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	
}
