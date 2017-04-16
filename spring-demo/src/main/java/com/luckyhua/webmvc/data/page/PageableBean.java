package com.luckyhua.webmvc.data.page;

import com.luckyhua.webmvc.context.model.PageInfo;

public abstract class PageableBean implements Pageable {

	protected PageInfo pageInfo = new PageInfo();

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		if(pageInfo==null)
			return;
		
		this.pageInfo = pageInfo;
	}
	
	
}
