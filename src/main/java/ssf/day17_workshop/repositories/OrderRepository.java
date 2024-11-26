package ssf.day17_workshop.repositories;

import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;
import ssf.day17_workshop.model.Order;

@Repository
public class OrderRepository {

    private static final Logger logger = Logger.getLogger(OrderRepository.class.getName());

    @Autowired
    private RedisTemplate<String, Object> template;

    // set orderId "{...}"
    public void save(String orderId, Order order) {

        JsonObject json = order.toJson();
        ValueOperations<String, Object> valueOps = template.opsForValue();
        valueOps.set(orderId, json.toString());
    }

    public Set<String> getOrderIds() {
        return template.keys("*");
    }

    public Optional<Order> getOrder(String orderId) {

        ValueOperations<String, Object> valueOps = template.opsForValue();
        if(!template.hasKey(orderId)) {
            return Optional.empty();
        }
        @SuppressWarnings("null")
        String json = valueOps.get(orderId).toString();
        logger.info("[Repo] Values: " + json);
        Order order = Order.toOrder(json);

        return Optional.of(order);
    }
    
}
