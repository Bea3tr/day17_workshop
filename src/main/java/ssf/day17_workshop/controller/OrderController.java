package ssf.day17_workshop.controller;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
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

    @GetMapping(path="/order/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<JsonObject> getOrder(@PathVariable String id) {

        Optional<Order> opt = orderSvc.getOrder(id);
        if(opt.isEmpty()) {
            return ResponseEntity.status(404)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(null);
        }
        Order order = opt.get();
        JsonObject resp = order.toJson();

        return ResponseEntity.status(200)
                       .contentType(MediaType.APPLICATION_JSON)
                       .body(resp);
    }

    @GetMapping(path="/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<JsonObject> getOrders() {

        Set<String> orderIds = orderSvc.getOrderIds();

        JsonArrayBuilder ids = Json.createArrayBuilder();
        for(String id : orderIds) {
            ids.add(id);
        }

        JsonObject resp = Json.createObjectBuilder()
                            .add("orderIds", ids.build())
                            .build();

        return ResponseEntity.ok(resp);
    }
}
