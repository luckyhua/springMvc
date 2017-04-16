package com.luckyhua.webmvc.context;

import com.luckyhua.webmvc.context.model.PageInfo;

import java.util.List;

public interface BaseQueryService <T, Q>{

	List<T> findByDetails(PageInfo pageInfo, Q queryVo);
	
}
