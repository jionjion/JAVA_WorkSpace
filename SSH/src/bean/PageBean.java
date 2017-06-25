package bean;

import java.util.List;

/***
 * 	分页封装的类
 * @param <T>
 */
public class PageBean<T> {

	//当前页
	private int currentPage;
	//每页显示条数
	private int pageSize;
	//总条数
	private int totalCount;
	//总页数
	private int totalPage;
	//每页显示的
	private List<T> list;
	public int getCurrentPage() {
		return currentPage;
	}
	//分页,针对于当前页在另外的情况
	public void setCurrentPage(int currentPage) {
		if (currentPage < 1) {
			this.currentPage = 1;
		}else if (currentPage>totalPage){
			this.currentPage = totalCount;
		}else {
			this.currentPage = currentPage;
		}
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	
	
}
