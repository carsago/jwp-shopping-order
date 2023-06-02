package cart.ui;

import cart.application.OrderService;
import cart.domain.Member;
import cart.dto.request.OrderItemRequest;
import cart.dto.response.OrderResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderApiController {

    private final OrderService orderService;

    public OrderApiController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> orders(Member member, @RequestBody List<OrderItemRequest> request) {
        orderService.order(request, member.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(Member member, @PathVariable Long orderId) {
        OrderResponse orderResponse = orderService.findById(orderId);
        return ResponseEntity.ok(orderResponse);
    }
}
