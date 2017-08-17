package bean;

import java.util.Date;

/**成功秒杀表,多方*/
public class SuccessSeckilled {

	private long seckilledId;
	
	private long userPhone;
	
	/**	-1:无效	0:成功	1:已发货	2:已收货	*/
	private short state;
	
	private Date createTime;

	private Seckill seckill;	//多对一,方便存取
	
	public Seckill getSeckill() {
		return seckill;
	}

	public void setSeckill(Seckill seckill) {
		this.seckill = seckill;
	}

	public long getSeckilledId() {
		return seckilledId;
	}

	public void setSeckilledId(long seckilledId) {
		this.seckilledId = seckilledId;
	}

	public long getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(long userPhone) {
		this.userPhone = userPhone;
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
