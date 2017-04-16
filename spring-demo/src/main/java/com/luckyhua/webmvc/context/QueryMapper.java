package com.luckyhua.webmvc.context;

import com.luckyhua.webmvc.data.QueryDetails;

import java.util.List;

public interface QueryMapper<T,Q> {

	List<T> selectByDetails(QueryDetails<Q> details);
	
	Integer countByDetails(QueryDetails<Q> details);
	
}
