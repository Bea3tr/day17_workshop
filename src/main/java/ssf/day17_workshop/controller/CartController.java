package ssf.day17_workshop.controller;

import java.io.StringReader;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonReader;
import ssf.day17_workshop.services.CartService;

@RestController
public class CartController {

    @Autowired
    private CartService cartSvc;

    private static final Logger logger = Logger.getLogger(CartController.class.getName());

    // GET /carts
    // Accept: text/csv
    @GetMapping(path="/carts", produces = "text/csv")
    public ResponseEntity<String> getCarts(@RequestBody String payload) {
        String cartId = "";
        if(!payload.equals("") || payload != null) {
            JsonReader reader = Json.createReader(new StringReader(payload));

            try {
                cartId = reader.readObject()
                                .getString("cartId");

            } catch (Exception ex) {
                logger.info("[Controller] No cartId");
            }
        }
        if(!cartId.equals("")) 
            cartSvc.insertId(cartId);

        String csv = "";
        List<String> idList = cartSvc.getCartIds();

        // get list of cartIds
        for(int i = 0; i < idList.size(); i++) {

            if (i != idList.size()-1)
                csv += idList.get(i) + ",";
            else 
                csv += idList.get(i);
        }

        return ResponseEntity.accepted()
                .header("cartId", csv)
                .body(csv);
    }
    
}
