package interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import entity.Page;


/**��ҳ������*/
@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})
public class PageInterceptor implements Interceptor{

	/**���غ�ķ���*/
	@SuppressWarnings("unchecked")
	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		//������صĶ���Ĵ���
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY,SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
		MappedStatement mappedStatement  = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
		//���SQL����ID
		String id = mappedStatement.getId();
		if (id.matches(".+ByPageInterceptor$")) {
			
			BoundSql boundSql = statementHandler.getBoundSql();
			String SQL = boundSql.getSql();	//���ԭʼ��SQL
			String countSQL = " select count(*) from (" + SQL + ") a";
			Map<String, Object> parameters = (Map<String, Object>) boundSql.getParameterObject();	//��ô����SQL����
			Page page = (Page) parameters.get("page");		//���pageʵ����
			
			Connection connection = (Connection) invocation.getArgs()[0];	//��ô������,ֻ��һ��,���ȡ��һ��
			PreparedStatement preparedStatement = connection.prepareStatement(countSQL);
			ParameterHandler parameterHandler = (ParameterHandler) metaObject.getValue("delegate.parameterHandler");
			parameterHandler.setParameters(preparedStatement);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				page.setTotalNumber(resultSet.getInt(1));	//���������
			}
			String pageSQL = SQL + " limit " + page.getStart() + " , " + page.getPace();
			metaObject.setValue("delegate.boundSql.sql", pageSQL);	//�޸�SQLΪ�Զ����
			
		}
		return invocation.proceed();
	}

	/**	ִ������ǰ�ķ���,��ע�����������������
	 * 	����Ƿ��������Ĵ�����,����ɷ������صĶ���Ĵ���*/
	@Override
	public Object plugin(Object target) {
		
		return Plugin.wrap(target, this);	//����,���ص���
	}

	/**	��������ļ�ִ��ʱ���������� */
	@Override
	public void setProperties(Properties properties) {
		String info = properties.getProperty("info");
		System.out.println(info);
	}

	
}