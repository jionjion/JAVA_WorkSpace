package bean;

import java.util.Date;

/**秒杀表,单方*/
public class Seckill {

	private long seckillId; 	//秒杀ID
	
	private String name;		//秒杀活动
	
	private int number;			//库存数量
	
	private Date createTime;	//创建时间
	
	private Date startTime;		//开始时间
	
	private Date endTime;		//结束时间

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "Seckill [seckillId=" + seckillId + ", name=" + name + ", number=" + number + ", createTime="
				+ createTime + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}
}
