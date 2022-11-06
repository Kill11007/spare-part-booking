package com.ford.sparepartbooking.entity;

import com.ford.sparepartbooking.dto.ItemDTO;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.Hibernate;

@Entity
@Table(name = "items")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Item {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private Integer quantity = 0;
  private BigDecimal price;

  @OneToMany(mappedBy = "item")
  @Exclude
  private Set<OrderItem> orderItems = new HashSet<>();

  public void addOrderItem(OrderItem orderItem) {
    orderItems.add(orderItem);
    orderItem.setItem(this);
  }

  public Item(ItemDTO dto) {
    setName(dto.getName());
    setPrice(dto.getPrice());
    setQuantity(dto.getQuantity());
  }

  public void update(ItemDTO dto) {
    setName(dto.getName());
    setPrice(dto.getPrice());
    setQuantity(dto.getQuantity());
  }

  public void updateQuantity(Integer quantity) {setQuantity(quantity);}

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Item item = (Item) o;
    return id != null && Objects.equals(id, item.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
