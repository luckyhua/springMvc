package com.luckyhua.webmvc.context.model;

/**
 * 分页查询参数类
 * @author luckyhua
 *
 */
@SuppressWarnings("unused")
public class PageInfo {

	public static final int Default_PageSize = 20;
	
	// 当前页码
	private int currentPage = 1;

	// 总页数
	private int totalPage;

	// 总记录数
	private int totalCount;

	// 每页显示个数
	private int pageSize = Default_PageSize;
	
	// 开始坐标
	private int pageBegin = 0;
	
	// 结束坐标
    private int pageEnd = 20;

	
	public static final String PageQuery_classname = "pageInfo";
	

	/**
	 * 将分布参数传入处理，最终计算出当前页码PageQuery_currPage，开始坐标PageQuery_star，结束坐标PageQuery_end，总页数PageQuery_Psize
	 * @param totalCount 记录总数
	 * @param pageSize 每页显示个数
	 * @param currentPage 当前页码
	 */
	public void setPageParams(int totalCount, int pageSize, int currentPage) {

		this.totalPage = pageSize == 0 ? 1 : (int) Math.ceil((double) totalCount / (double) pageSize);;
		
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		
		float pageSize_l = totalCount / (float) (this.pageSize);
		if (currentPage < 2) {
			currentPage = 1;
			pageBegin = 0;
		} else if (currentPage > pageSize_l) {
			if(pageSize_l==0){
				currentPage=1;
			}else{
				currentPage = (int) Math.ceil(pageSize_l);
			}
			
			pageBegin = (currentPage - 1) * this.pageSize;
		} else {
			pageBegin = (currentPage - 1) * this.pageSize;
		}
		pageSize = (int) Math.ceil(pageSize_l);
		this.pageEnd = currentPage*this.pageSize;
		
		if(this.currentPage<=0||this.currentPage>this.totalPage)
			this.pageSize = 0;
//		if(this.pageBegin<=0)
//			this.pageBegin = 0;
	}

	
	/**
	 * 将分布参数传入处理，最终计算出当前页码PageQuery_currPage，开始坐标PageQuery_star，结束坐标PageQuery_end，总页数PageQuery_Psize
	 * @param totalCount 记录总数
	 */
	public void setPageParams(int totalCount) {
		this.setPageParams(totalCount, this.pageSize, this.currentPage);
	}

	public int getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(Integer currentPage) {
		if(currentPage==null||currentPage==0)
			return;
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}


	public int getPageBegin() {
		return pageBegin;
	}


	public void setPageBegin(int pageBegin) {
		this.pageBegin = pageBegin;
	}


	public int getPageEnd() {
		return pageEnd;
	}


	public void setPageEnd(int pageEnd) {
		this.pageEnd = pageEnd;
	}


	public void setPageSize(Integer pageSize) {
		if(pageSize==null||pageSize==0)
			return;
		this.pageSize = pageSize;
	}
}
