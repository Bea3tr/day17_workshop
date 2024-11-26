package ssf.day17_workshop.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;
import ssf.day17_workshop.model.Order;

@Repository
public class OrderRepository {

    @Autowired
    private RedisTemplate<String, Object> template;

    // set orderId "{...}"
    public void save(String orderId, Order order) {

        JsonObject json = order.toJson();
        ValueOperations<String, Object> valueOps = template.opsForValue();
        valueOps.set(orderId, json.toString());
    }
    
}
