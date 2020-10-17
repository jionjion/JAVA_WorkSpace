package responsibility;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 *  责任链模式测试
 * @author Jion
 */
public class ApproverTest {

    @Test
    public void test(){
        GoodOrder good = new GoodOrder("电脑", 40000);

        // 处理者, 链模式
        EmployeeApprover employeeApprover = new EmployeeApprover();
        // 尝试用员工审批
        employeeApprover.process(good);
    }
}