package com.ford.sparepartbooking.controller;

import com.ford.sparepartbooking.dto.OrderDTO;
import com.ford.sparepartbooking.entity.Order;
import com.ford.sparepartbooking.service.BookingService;
import com.ford.sparepartbooking.service.BookingService.OrderQuery;
import com.ford.sparepartbooking.service.IAuthenticationFacade;
import com.ford.sparepartbooking.service.impl.AuthenticationFacade;
import javax.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
@CrossOrigin("*")
public class BookingController {

  private final IAuthenticationFacade authenticationFacade;
  private final BookingService bookingService;

  @GetMapping
  @RolesAllowed("ADMIN")
  public ResponseEntity<Page<OrderDTO>> getAll(@RequestBody(required = false)OrderQuery orderQuery) {
    return ResponseEntity.ok(bookingService.allOrders(orderQuery));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Page<OrderDTO>> getAll(@PathVariable Long id) {
    OrderQuery orderQuery = new OrderQuery();
    orderQuery.setOrderId(id);
    return ResponseEntity.ok(bookingService.allOrders(orderQuery));
  }

  @PostMapping
  @RolesAllowed({"USER", "ADMIN"})
  public ResponseEntity<Void> placeOrder(
      @RequestBody OrderDTO orderDTO,
      UriComponentsBuilder uriComponentsBuilder) {
    Order order = bookingService.placeOrder(authenticationFacade.getAuthentication(), orderDTO);
    return ResponseEntity.created(
            uriComponentsBuilder.path("/bookings/{id}").buildAndExpand(order.getId()).toUri())
        .build();
  }
}
