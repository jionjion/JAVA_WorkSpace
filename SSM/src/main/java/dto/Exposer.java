package dto;
/**
 *	暴露秒杀接口的DTO
 */
public class Exposer {

	private boolean exposed;
	
	private String md5;
	
	private long seckillId;
	
	private long now;
	
	private long start;
	
	private long end;

	
	
	/**
	 * 开启秒杀
	 * @param exposed 是否开启秒杀
	 * @param md5  验证用户
	 * @param seckillId 秒杀物品ID
	 */
	public Exposer(boolean exposed, String md5, long seckillId) {
		super();
		this.exposed = exposed;
		this.md5 = md5;
		this.seckillId = seckillId;
	}


	
	/**
	 * 没有开始秒杀,返回系统时间
	 * @param exposed 是否开启秒杀
	 * @param now 现在时间
	 * @param start 开始时间
	 * @param end 结束时间
	 */
	public Exposer(boolean exposed,long seckillId, long now, long start, long end) {
		super();
		this.exposed = exposed;
		this.seckillId = seckillId;
		this.start = start;
		this.end = end;
	}



	/**
	 * 返回当前是否可以秒杀
	 * @param exposed 是否开启秒杀
	 * @param seckillId 秒杀物品ID
	 */
	public Exposer(boolean exposed, long seckillId) {
		super();
		this.exposed = exposed;
		this.seckillId = seckillId;
	}

	public boolean isExposed() {
		return exposed;
	}

	public void setExposed(boolean exposed) {
		this.exposed = exposed;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public long getNow() {
		return now;
	}

	public void setNow(long now) {
		this.now = now;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}



	@Override
	public String toString() {
		return "Exposer [exposed=" + exposed + ", md5=" + md5 + ", seckillId=" + seckillId + ", now=" + now + ", start="
				+ start + ", end=" + end + "]";
	}

	
	
}
