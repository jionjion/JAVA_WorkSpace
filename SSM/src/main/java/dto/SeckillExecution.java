package dto;

import bean.SuccessSeckill;
import enums.SeckillStatEnum;

/**
 *	秒杀执行的结果
 */
public class SeckillExecution {

	private long seckillId;  				//秒杀对象ID
	
	private int state;						//秒杀商品状态code
	
	private String stateInfo;				//秒杀商品状态信息
	
	private SuccessSeckill successSeckill;	//秒杀成功对象

	/**
	 * 秒杀成功的状态
	 * @param seckillId	秒杀对象
	 * @param SeckillStatEnum: 包含 state 秒杀结果状态   stateInfo 状态信息
	 * @param successSeckill 秒杀成功对象
	 */
	public SeckillExecution(long seckillId, SeckillStatEnum statEnum, SuccessSeckill successSeckill) {
		super();
		this.seckillId = seckillId;
		this.state = statEnum.getState();
		this.stateInfo = statEnum.getStateInfo();
		this.successSeckill = successSeckill;
	}

	/**
	 * 秒杀失败的状态
	 * @param seckillId	秒杀对象
	 * @param SeckillStatEnum: 包含 state 秒杀结果状态   stateInfo 状态信息
	 */
	public SeckillExecution(long seckillId, SeckillStatEnum statEnum) {
		super();
		this.seckillId = seckillId;
		this.state = statEnum.getState();
		this.stateInfo = statEnum.getStateInfo();
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SuccessSeckill getSuccessSeckill() {
		return successSeckill;
	}

	public void setSuccessSeckill(SuccessSeckill successSeckill) {
		this.successSeckill = successSeckill;
	}

	@Override
	public String toString() {
		return "SeckillExecution [seckillId=" + seckillId + ", state=" + state + ", stateInfo=" + stateInfo
				+ ", successSeckill=" + successSeckill + "]";
	}
}
