package top.jionjion.demo.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Jion
 *  员工表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private Integer id;

    private String lastName;

    private String email;

    private String gender;

    private Department department;

    /** 日期格式,默认格式yyyy/MM/dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;
}
