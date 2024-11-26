package ssf.day17_workshop.repositories;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepository {

    @Autowired @Qualifier("redis-0")
    private RedisTemplate<String, String> template;

    private static final Logger logger = Logger.getLogger(CartRepository.class.getName());

    public List<String> getCartIds() {
        ListOperations<String, String> listOps = template.opsForList();
        logger.info("[Repo] Getting cartIds from database");

        return listOps.range("cartId", 0, -1);
    }

    public void insertId(String cartId) {
        ListOperations<String, String> listOps = template.opsForList();
        logger.info("[Repo] Inserted cart ID (%s)".formatted(cartId));
        listOps.leftPush("cartId", cartId);
    }

    
}
