package top.jionjion.demo.controller;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.jionjion.demo.dao.DepartmentDao;
import top.jionjion.demo.dao.EmployeeDao;
import top.jionjion.demo.entities.Department;
import top.jionjion.demo.entities.Employee;
import top.jionjion.demo.exception.UserNotExitsException;
import top.jionjion.demo.service.EmployeeService;

import java.util.Collection;
import java.util.Objects;

/**
 *  @author Jion
 *      用户请求
 */
@Controller
@RequestMapping("/emp")
public class EmployeeController {

    /*
        URI:               /资源名称/资源标识              请求方式区分对应的CRUD操作

        查询页面            /emp/emps
        查询所有            /emp/list                         get
        新增               /emp/{id}                      get
        添加页面            /emp                          get
        添加                /emp                          post
        修改页面            /emp/{id}                       get
        修改                /emp                          put
        删除                /emp/{id}                     delete
     */

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentDao departmentDao;

    /** 查看员工列表,跳转链接 */
    @GetMapping("/emps")
    public String list(Model model){
        Collection<Employee> employees = employeeService.getAll();

        // 放在请求域中
        model.addAttribute("emps", employees);
        // 列表页面
        return "list";
    }

    /** 跳转到员工添加页面 */
    @GetMapping("/emp")
    public String toAddPage(Model model){

        // 部门信息,下来选框
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts", departments);

        // 添加页面
        return "add";
    }

    /** 添加员工,通过请求参数的key与Bean的属性名一致,进行自动绑定. */
    @PostMapping("/emp")
    public String addEmp(Employee employee){

        System.out.println("获得参数" + employee.toString());
        employeeService.save(employee);

        // 列表页面  redirect:重定向  forward:转发       参考:ThymeleafViewResolver类
        return "redirect:/emp/emps";
    }


    /** 跳转修改页面 */
    @GetMapping("/emp/{id}")
    public String toEditPage(@PathVariable Integer id,
                             Model model){
        // 员工信息
        Employee employee = employeeService.get(id);
        if(Objects.isNull(employee)){
            //throw new UserNotExitsException();
            throw new RuntimeException();
        }
        model.addAttribute("emp",employee);

        // 删除员工
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts", departments);

        // 返回修改|添加页面
        return "add";
    }

    /** 修改员工修改信息 */
    @PutMapping("/emp")
    public String updateEmp(Employee employee){

        System.out.println("获得修改员工信息:" + employee.toString());
        employeeService.save(employee);

        return "redirect:/emp/emps";
    }

    /** 删除员工 */
    @DeleteMapping("/emp/{id}")
    public String delEmp(@PathVariable Integer id){

        employeeService.delete(id);

        return "redirect:/emp/emps";
    }
}
