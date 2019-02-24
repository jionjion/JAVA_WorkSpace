package com.jionjion.guider.dao.japRepository;

import org.springframework.data.repository.Repository;

import com.jionjion.guider.bean.HeaderImage;


/**
 * @author 14345
 * 	头像图片信息
 */
//@Transactional	// 仅在测试Repository时,保存事务
public interface HeaderImageRepository extends Repository<HeaderImage,String>{

	/** 通过UUID查询 */
	public HeaderImage findByUuid(String uuid);
	
	/** 保存 */
	public HeaderImage save(HeaderImage headerImage);
	
	/** 删除 */
	public Integer deleteByUuid(String uuid);
	
	
}
