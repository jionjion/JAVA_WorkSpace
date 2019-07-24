package top.jionjion.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jion
 *  部门表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    private Integer id;

    private String departmentName;
}
