package jackson.annotation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Jion
 * \@JsonInclude 注解使用
 */
@Slf4j
public class JsonIncludeTest {

    /**
     * 内部类
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class Student {

        private Integer id;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String address;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    @Test
    public void test() throws JsonProcessingException {
        Student student = new Student();
        student.setId(1);
        student.setName("囧囧");
        student.setAddress(null);

        String result = new ObjectMapper().writeValueAsString(student);
        assertNotNull(result);
        log.info(result);
    }
}
