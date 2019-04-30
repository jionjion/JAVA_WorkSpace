package commons.collections;

import org.apache.commons.collections4.OrderedMap;
import org.apache.commons.collections4.map.LinkedMap;
import org.junit.Before;
import org.junit.Test;

/**
 * @author 14345
 *	OrderedMap 类的例子
 *	继承自 org.apache.commons.collections4.IterableMap 有父类大部分方法,并提供特有的方法
 */
public class OrderedMapExample {

	
	private OrderedMap<String, String> orderedMap;
	
	@Before
	public void initData() {
		orderedMap = new LinkedMap<String, String>();
		orderedMap.put("a", "A");
		orderedMap.put("b", "B");
		orderedMap.put("c", "C");
		orderedMap.put("d", "D");
	}
	
	@Test
	public void test() {
		System.out.println(orderedMap);
	}
	
	@Test
	public void testFirstKey() {
		
	}
	
	public void testLastKey() {
		
	}
	
	public void testMapIterator() {
		
	}
	
	public void testNextKey() {
		
	}

	public void testPreviousKey(){
		
	}
	
}
