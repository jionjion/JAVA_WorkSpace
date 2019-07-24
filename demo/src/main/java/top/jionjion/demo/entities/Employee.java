package top.jionjion.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Date birth;
}
