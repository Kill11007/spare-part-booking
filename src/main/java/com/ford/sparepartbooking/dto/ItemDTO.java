package com.ford.sparepartbooking.dto;

import com.ford.sparepartbooking.entity.Item;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
  private Long id;
  private String name;
  private Integer quantity = 0;
  private BigDecimal price;

  public ItemDTO(Item item) {
    setId(item.getId());
    setName(item.getName());
    setPrice(item.getPrice());
    setQuantity(item.getQuantity());
  }
}
