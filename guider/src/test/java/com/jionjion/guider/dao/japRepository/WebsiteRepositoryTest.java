package com.jionjion.guider.dao.japRepository;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jionjion.guider.bean.Website;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebsiteRepositoryTest {

	@Autowired
	private WebsiteRepository repository;
	
	@Test
	public void testFindBysiteId() {
		Website website = repository.findBysiteId(1);
		System.out.println(website);
	}
	
	@Test
	public void testSave() {
		Website website = new Website();
		website.setSiteId(0);
		website.setSiteName("知乎");
		website.setSiteUri("www.zhihu.com");
		System.out.println(repository.save(website));
	}
	
	@Test
	public void testDeleteBysiteId() {
		System.out.println(repository.deleteBysiteId(0));
	}

}
