package ssf.day17_workshop.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssf.day17_workshop.repositories.CartRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;

    public List<String> getCartIds() {
        return cartRepo.getCartIds();
    }

    public String generateId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public void insertId(String cartId) {
        cartRepo.insertId(cartId);
    }
    
}
