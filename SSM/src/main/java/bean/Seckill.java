package bean;

import java.util.Date;

/**秒杀表,单方*/
public class Seckill {

	private long seckillId;
	
	private String name;
	
	private int number;
	
	private Date createTime;
	
	private Date startTiem;
	
	private Date endTiem;

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

	public Date getStartTiem() {
		return startTiem;
	}

	public void setStartTiem(Date startTiem) {
		this.startTiem = startTiem;
	}

	public Date getEndTiem() {
		return endTiem;
	}

	public void setEndTiem(Date endTiem) {
		this.endTiem = endTiem;
	}

	@Override
	public String toString() {
		return "Seckill [seckillId=" + seckillId + ", name=" + name + ", number=" + number + ", createTime="
				+ createTime + ", startTiem=" + startTiem + ", endTiem=" + endTiem + "]";
	}
	
}
