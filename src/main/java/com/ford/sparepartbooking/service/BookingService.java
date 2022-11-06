package com.ford.sparepartbooking.service;

import com.ford.sparepartbooking.dto.OrderDTO;
import com.ford.sparepartbooking.entity.Order;
import com.ford.sparepartbooking.repository.OrderRepository.OrderSpecification;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface BookingService {

  Order placeOrder(Authentication authentication, OrderDTO dto);

  Page<OrderDTO> allOrders(OrderQuery orderQuery);

  @Data
  class OrderQuery{
    private Long orderId;
    private Long userId;
    private Long itemId;
    private String userName;
    private String itemName;
    private String itemQuantity;
    private Integer page = 0;
    private Integer pageSize = 10;

    public OrderSpecification toSpecification() {
      return new OrderSpecification(this);
    }

    public Pageable toPageable() {
      return PageRequest.of(page, pageSize);
    }
  }
}
