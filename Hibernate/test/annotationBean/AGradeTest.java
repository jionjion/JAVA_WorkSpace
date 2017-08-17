package annotationBean;


import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *	���һ,һ���Ĳ���
 */
public class AGradeTest {

	private  SessionFactory sessionFactory;
	private  Session session;
	private  Transaction transaction;
	/*
	 * 	���Է���ǰ
	 */
	@Before
	public void init(){
		//����Configuration����,��ȡHibernate��Ŷ�����ļ�
		Configuration configuration = new Configuration().configure();
		
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		//�����Ự����
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		//�����Ự����
		session = sessionFactory.openSession();
		//��������
		transaction = session.beginTransaction();
		
	}

	
	/*
	 * 	���Է�����ִ��
	 */
	@After
	public void destory() {
		//�����ύ
		transaction.commit();
		//�رջỰ 
		session.close();
		//�رչ���
		sessionFactory.close();
	}
	
	/*����ѧ��*/
	@Test
	public void	 addAPupil() {
		//�����༶
		AGrade grade = new AGrade();
		grade.setGid(1);
		grade.setGname("��һ");
		grade.setGdescribe("������С����");
		
		//����ѧ��
		APupil pupil1 = new APupil(1,"����","��");
		APupil pupil2 = new APupil(2,"����","��");
		Set<APupil> pupils = new HashSet<APupil>();
		pupils.add(pupil1);				
		pupils.add(pupil2);
		
		//����ѧ����
		grade.setPupils(pupils);
		
		//����
		session.save(pupil1);
		session.save(pupil2);
		session.save(grade);							
	}
	
	
	/*��ѯѧ������Ϣ,ͨ���෽��ѯ��������Ϣ,���е�����ѯ*/
	@Test
	public void findPupilByGrade() {
		AGrade grade = (AGrade)session.get(AGrade.class, 1);
		System.out.println("��ѯ���༶"+grade.toString());
		Set<APupil> pupils = grade.getPupils();
		for (APupil pupil : pupils) {
			System.out.println("ÿ��ѧ����Ϣ"+pupil.toString());
		}
	}
	
	/*�޸�ѧ������һ����,��Ϊ�����꼶*/
	@Test
	public void updatePupil(){
		//�������꼶
		AGrade grade = new AGrade();
		grade.setGid(2);
		grade.setGname("���");
		grade.setGdescribe("�����������");
		//���ѧ��
		APupil pupil = (APupil) session.get(APupil.class, 1);
		//����ѧ�������꼶
		grade.getPupils().add(pupil);
		//���������ʹӱ�
		session.save(grade);
		session.save(pupil);
	}
	
	/*ɾ��ѧ��������Ϣ,ɾ���ӱ�*/
	@Test
	public void deletePupil(){
		APupil pupil = (APupil) session.get(APupil.class, 2);
		session.delete(pupil);
	}
}