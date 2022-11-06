package com.ford.sparepartbooking.dto;

import com.ford.sparepartbooking.entity.OrderItem;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemDTO {
  private Long id;
  private Long orderId;
  private Long itemId;
  private Integer quantity = 1;
  private BigDecimal amount;

  public OrderItemDTO(OrderItem entity) {
    setId(entity.getId());
    setOrderId(entity.getOrder().getId());
    setItemId(entity.getItem().getId());
    setQuantity(entity.getQuantity());
    setAmount(entity.getAmount());
  }
}
