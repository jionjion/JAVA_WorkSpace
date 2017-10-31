package MicroServices.collector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
@RunWith(SpringRunner.class)
@SpringBootTest	
@AutoConfigureMockMvc	
public class UserControllerTest{

	@Autowired
	private MockMvc mvc;
	
	@Test
	public void testUserGet() {
		try {
			mvc.perform(MockMvcRequestBuilders.get("/user/users/1"))				//请求的url映射,为类验证+方法验证
							.andExpect(MockMvcResultMatchers.status().isOk());	//期望状态码为OK
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
