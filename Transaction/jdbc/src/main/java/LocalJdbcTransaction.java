import java.sql.*;

/**
 * JDBC 数据库事物
 * 尝试断点处,前一个SQL事物是否提交,不同SQL间事物隔离级别
 *
 * @author Jion
 */
public class LocalJdbcTransaction {

    private static String sqlA = "UPDATE t_user t SET t.amount = t.amount + 100 WHERE t.username = ?";

    private static String sqlB = "UPDATE t_user t SET t.amount = t.amount - 100 WHERE t.username = ?";

    private static String sqlC = "select t.id, t.username, t.amount from t_user t";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // 数据库及驱动信息
        String url = "jdbc:mysql://localhost:3306/springboot_transaction?serverTimezone=Asia/Shanghai&characterEncoding=UTF-8";
        String username = "root";
        String password = "123456";
        String driver = "com.mysql.cj.jdbc.Driver";
        // 加载类
        Class.forName(driver);
        // 获得连接
        Connection connection = DriverManager.getConnection(url, username, password);

        // 启用事物,禁用默认提交,必须手动提交/回滚当前连接
        connection.setAutoCommit(false);
        // 设置SQL参数并执行
        PreparedStatement preparedStatementA = connection.prepareStatement(sqlA);
        preparedStatementA.setString(1, "张三");
        preparedStatementA.executeUpdate();

        // 在事物内查询
        PreparedStatement preparedStatementC = connection.prepareStatement(sqlC);
        ResultSet resultSet = preparedStatementC.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("id"));
            System.out.println(resultSet.getString("username"));
            System.out.println(resultSet.getInt("amount"));
        }

        // 设置SQL参数并执行
        PreparedStatement preparedStatementB = connection.prepareStatement(sqlB);
        preparedStatementB.setString(1, "李四");
        preparedStatementB.executeUpdate();
        // 提交事务
        connection.commit();
        // 关闭连接
        connection.close();
    }
}
