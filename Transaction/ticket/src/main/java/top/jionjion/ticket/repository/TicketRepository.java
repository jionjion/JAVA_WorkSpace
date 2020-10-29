package top.jionjion.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.jionjion.ticket.bean.Ticket;

import java.util.List;

/**
 * @author Jion
 */
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    /**根据所有者查询 */
    List<Ticket> findByOwner(Long owner);
}
