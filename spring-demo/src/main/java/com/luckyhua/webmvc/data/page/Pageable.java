package com.luckyhua.webmvc.data.page;

import com.luckyhua.webmvc.context.model.PageInfo;

public interface Pageable {

	PageInfo getPageInfo();
	
	void setPageInfo(PageInfo pageInfo);
}
