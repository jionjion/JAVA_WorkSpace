package controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jionjion.ticket.bean.Ticket;
import top.jionjion.ticket.repository.TicketRepository;

import javax.annotation.PostConstruct;

/**
 *
 * @author Jion
 */
@Slf4j
@RestController
@RequestMapping("/api/ticket")
public class TicketController{

    @Autowired
    private TicketRepository ticketRepository;

    /** 初始化数据 */
    @PostConstruct
    public void init(){
        log.info("初始化数据....");
        Ticket ticket = new Ticket();
        ticket.setName("火车票");
        ticket.setTickNum(12306);
        ticketRepository.save(ticket);
    }


//    /**
//     *  查询全部
//     */
//    @GetMapping("/all")
//    public List<Order> getAll() {
//        return orderRepository.findAll();
//    }
//
//    /**
//     *  保存
//     */
//    @PostMapping("/save")
//    public Order save(@RequestBody Order order) {
//        return orderRepository.save(order);
//    }
//
//    /**
//     *  查询我的
//     */
//    @GetMapping("/{id}")
//    public OrderDto getMyOrder(@PathVariable Long id) {
//        Order order = orderRepository.findById(id).get();
//        OrderDto result = new OrderDto();
//        result.setId(order.getId());
//        result.setTitle(order.getTitle());
//        result.setDetail(order.getDetail());
//        result.setAmount(order.getAmount());
//        return result;
//    }
}
