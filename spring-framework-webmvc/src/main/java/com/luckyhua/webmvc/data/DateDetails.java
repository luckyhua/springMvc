package com.luckyhua.webmvc.data;

public class DateDetails{
	
	private static final String Default_Mouth_Pattern = "yyyy-MM";
	
	private static final String Default_Day_Pattern = "yyyy-MM-dd";

	//1:one year,2:one mouth,3:one day,4:year to year ,5:mouth to mouth,6 day to day
	private Integer type;
	
	private String dateColumn;
	
	private String dateBeginValue;
	
	private String dateEndValue;
	
	private String pattern;
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDateColumn() {
		return dateColumn;
	}

	public void setDateColumn(String dateColumn) {
		this.dateColumn = dateColumn;
	}

	public String getDateBeginValue() {
		return dateBeginValue;
	}

	public void setDateBeginValue(String dateBeginValue) {
		this.dateBeginValue = dateBeginValue;
	}

	public String getDateEndValue() {
		return dateEndValue;
	}

	public void setDateEndValue(String dateEndValue) {
		this.dateEndValue = dateEndValue;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
