package entity;
/**分页类,放在与表无关的包下*/
public class Page {

	/**总条数*/
	private int totalNumber;
	/**当前页*/
	private int currentPage;
	/**总页数*/
	private int totalPage;
	/**每页显示条数,默认5页*/
	private int pageNumber = 5;
	/**开始查询条数*/
	private int start;	
	/**偏移量*/
	private int pace;
	
	/**计算总页数*/
	public void count() {
		int totalPageTemp = (totalNumber / pageNumber) + ((totalNumber % pageNumber) ==0 ? 0 : 1); 
		//当前页数小于1
		if (totalPageTemp <=0 ) {
			totalPageTemp = 1;
		}
		this.totalPage = totalPageTemp;
		//总页数小于当前页数
		if(this.totalPage < this.currentPage){
			this.currentPage = this.totalPage;
		}
		//当前页数小于1时,设置为1
		if (this.currentPage < 1) {
			this.currentPage = 1;
		}
		//计算limit传入分页参数
		this.start = (this.currentPage -1) * this.pageNumber;
		this.pace = this.pageNumber;
	}
	
	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getPace() {
		return pace;
	}
	public void setPace(int pace) {
		this.pace = pace;
	}
}
