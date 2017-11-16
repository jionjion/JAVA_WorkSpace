package springData.JPARepository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import springData.bean.Teacher;

/**
 *	使用JPARepository访问数据库
 *	通过继承CrudRepository接口或者使用@RepositoryDefinition 
 *	标识该类使用Spring-Data
 */

public interface TeacherCrudRepository extends CrudRepository<Teacher,Integer>{

	@Override
	default <S extends Teacher> S save(S entity) {
		return save(entity);
	}

	@Override
	default <S extends Teacher> Iterable<S> save(Iterable<S> entities) {
		return save(entities);
	}

	@Override
	default Teacher findOne(Integer id) {
		return findOne(id);
	}

	@Override
	default boolean exists(Integer id) {
		return exists(id);
	}

	@Override
	default Iterable<Teacher> findAll() {
		return findAll();
	}

	@Override
	default Iterable<Teacher> findAll(Iterable<Integer> ids) {
		return findAll(ids);
	}

	@Override
	@Transactional
	default long count() {
		return count();
	}

	@Override
	default void delete(Integer id) {
		delete(id);
	}

	@Override
	default void delete(Teacher entity) {
		delete(entity);
	}

	@Override
	default void delete(Iterable<? extends Teacher> entities) {
		delete(entities);
	}

	@Override
	default void deleteAll() {
		deleteAll();
	}

	
}
