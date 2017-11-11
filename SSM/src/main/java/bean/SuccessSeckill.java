package bean;

import java.util.Date;

/**成功秒杀表,多方*/
public class SuccessSeckill {

	private long seckillId;		//秒杀ID
	
	private long userPhone;		//用户电话
	
	/**	-1:无效	0:成功	1:已发货	2:已收货	*/
	private short state;		//商品状态
	
	private Date createTime;	//创建时间

	private Seckill seckill;	//多对一,方便存取
	
	public Seckill getSeckill() {
		return seckill;
	}

	public void setSeckill(Seckill seckill) {
		this.seckill = seckill;
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
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

	@Override
	public String toString() {
		return "SuccessSeckill [seckillId=" + seckillId + ", userPhone=" + userPhone + ", state=" + state
				+ ", createTime=" + createTime + ", seckill=" + seckill + "]";
	}
}
