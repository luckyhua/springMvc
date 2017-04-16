package com.luckyhua.webmvc.context;

import com.luckyhua.webmvc.context.model.PageInfo;
import com.luckyhua.webmvc.data.QueryDetails;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractQueryService<T, Q> implements BaseQueryService<T,Q>, Logable {

	public List<T> findByDetails(PageInfo pageInfo, Q queryVo) {
		QueryDetails<Q> details = QueryDetails.create(queryVo);

		Integer count = getMapper().countByDetails(details);
		if (log.isInfoEnabled())
			log.info("find by: {} size:{}", queryVo.getClass().getSimpleName(), count + "");
		
		if(count==0)
			return new ArrayList<>();

		pageInfo.setPageParams(count);
		details.setPageInfo(pageInfo);

		List<T> data = getMapper().selectByDetails(details);

		return data;

	}

	@SuppressWarnings("rawtypes")
	public abstract QueryMapper getMapper();
}
