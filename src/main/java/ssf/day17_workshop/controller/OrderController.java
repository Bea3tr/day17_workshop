package ssf.day17_workshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import ssf.day17_workshop.model.Order;
import ssf.day17_workshop.services.OrderService;

@Controller
@RequestMapping
public class OrderController {

    @Autowired 
    private OrderService orderSvc;

    @PutMapping(path="/order", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> postOrder(
        @RequestBody String payload) {
            
        System.out.printf(">>> PAYLOAD: %s\n", payload);
        Order order = Order.toOrder(payload);
        String orderId = orderSvc.save(order);

        JsonObject resp = Json.createObjectBuilder()
            .add("orderId", orderId)
            .build();

        return ResponseEntity.ok(resp.toString());
    }
    
}
