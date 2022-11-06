package com.ford.sparepartbooking.dto;

import com.ford.sparepartbooking.entity.Order;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDTO {
  private Long id;
  private Long userId;
  private List<OrderItemDTO> items;
  private BigDecimal total;

  public OrderDTO(Order entity) {
    setId(entity.getId());
    setItems(
        entity.getOrderItems().stream().map(OrderItemDTO::new).collect(Collectors.toList()));
    setUserId(entity.getUser().getId());
    setTotal(entity.getTotal());
  }
}
