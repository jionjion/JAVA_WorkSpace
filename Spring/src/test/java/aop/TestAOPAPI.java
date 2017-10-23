package aop;
/**对早起的通知方式进行测试**/
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import aop.api.BizLogic;
import base.UnitTestBase;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestAOPAPI extends UnitTestBase {
	
	public TestAOPAPI() {
		super("classpath:spring-aop-api.xml");
	}
	
	@Test
	public void testSave() {
		BizLogic logic = (BizLogic)super.getBean("bizLogicImpl");
		logic.save();
	}

}
