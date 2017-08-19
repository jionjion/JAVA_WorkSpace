package service.imp;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import bean.Seckill;
import bean.SuccessSeckill;
import dao.SeckillDao;
import dao.SuccessSeckillDao;
import dto.Exposer;
import dto.SeckillExecution;
import enums.SeckillStatEnum;
import exception.RepeatKillException;
import exception.SeckillCloseException;
import exception.SeckillException;
import service.SeckillService;

@Service
public class SeckillServiceImp implements SeckillService {

	//日志对象,不需要使用Spring的IOC容器
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final String salt = "Jion is mine";		//MD5消息摘要加密的盐
	
	@Autowired
	private SeckillDao seckillDao;
	@Autowired
	private SuccessSeckillDao successSeckillDao;

	
	@Override
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 10);
	}

	@Override
	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	@Override
	public Exposer exportSeckillUrl(long seckillId) {
		Seckill seckill = seckillDao.queryById(seckillId);
		//如果没有该秒杀物品,返回空
		if (seckill == null) {	
			return new Exposer(false, seckillId);
		}
		//获得秒杀物的时间信息,并比较
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		Date nowTime = new Date();
		if (nowTime.getTime()<startTime.getTime() || nowTime.getTime()>endTime.getTime()) {
			return new Exposer(false,seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
		}
		//返回md5的加密结果
		String md5 = getMD5(seckillId);
		
		//暴露秒杀,返回消息摘要
		return new Exposer(true, md5, seckillId);
	}
	
	/*	注解事务的优点
	 *	1.开发团队达成一致约定,明确标注事务方法的编程风格
	 *	2.保证事务方法的执行时间尽可能短,不要穿插其他网络操作,如缓存/请求.(可以将其剥离到事务方法外)
	 *	3.明确方法的事务控制,只有在需要的时候声明其为事务方法
	 */
	@Override
	@Transactional
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws RepeatKillException, SeckillCloseException, SeckillException {
		try {
			//将用户携带的md5和服务器端的进行比较
			if(md5 == null || !md5.equals(getMD5(seckillId))) {
				throw new SeckillException("秒杀地址异常,请从新进入提交");
			}
			//执行秒杀事务过程	1.首先进行减库存
			int updateResult = seckillDao.reduceNumber(seckillId, new Date());
			if (updateResult <= 0) {
				//没有库存减少,秒杀失败
				throw new SeckillCloseException("很抱歉,秒杀活动已经结束");
			}
			//2.记录结果
			int insertResult = successSeckillDao.insertSuccessKill(seckillId, userPhone);
			if (insertResult <= 0) {
				//插入秒杀成功记录,秒杀失败
				throw new RepeatKillException("抱歉,你已经秒杀成功,本次秒杀无效");
			}
			SuccessSeckill successSeckill = successSeckillDao.queryByIdIdWithSeckill(seckillId, userPhone);
			return new SeckillExecution(seckillId,SeckillStatEnum.SUCCESS,successSeckill);
		} catch (SeckillCloseException e) {
			throw e;
		} catch (RepeatKillException e) {
			throw e;
		} catch (Exception e) {
			logger.error("秒杀事务出现异常:"+e.getMessage());
			throw new SeckillException("很抱歉,秒杀过程中,服务器出现问题");
		}
	}
	
	/**
	 * 秒杀物品结合盐进行加密算法
	 * 根于的秒杀物生成消息摘要作为服务器一端的验证,结合用户携带的进行验证
	 * @param SeckillId	获得所需要的
	 * @return 
	 */
	private String getMD5(long SeckillId) {
		String base = SeckillId + '/' + salt;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

}
