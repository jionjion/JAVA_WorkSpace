package com.jionjion.guider.dao.japRepository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.Repository;

import com.jionjion.guider.bean.Website;


/**
 * @author 14345
 *	通过Spring-Data定义的JPA规范实现
 *	各种方法签名
 */
@Transactional
public interface WebsiteRepository extends Repository<Website,Integer>{

	/** 通过Id查询 */
	public Website findBysiteId(Integer siteId);
	
	/** 查询全部 */
	public List<Website> findAll();
	
	/** 保存 */
	public Website save(Website wWebsite);
	
	/** 删除 */
	public Integer deleteBysiteId(Integer siteId);
}
