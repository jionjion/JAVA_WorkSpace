package com.imooc.beanannotation.jsr;
/**模拟Dao层**/
import org.springframework.stereotype.Repository;

@Repository		//Dao层的接口
public class JsrDAO {
	
	public void save() {
		System.out.println("Dao层保存数据.");
	}
	
}
