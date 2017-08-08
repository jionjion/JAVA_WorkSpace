package MicroServices.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import MicroServices.bean.Logs;

public interface LogsRepository extends JpaRepository<Logs, Integer> {

}
