package ssf.day17_workshop.services;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssf.day17_workshop.model.Order;
import ssf.day17_workshop.repositories.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    public String save(Order order) {
        String orderId = UUID.randomUUID().toString().substring(0, 8);
        orderRepo.save(orderId, order);

        return orderId;
    }

    public Set<String> getOrderIds() {
        return orderRepo.getOrderIds();
    }
    
    public Optional<Order> getOrder(String orderId) {
        return orderRepo.getOrder(orderId);
    }
}
