package com.ford.sparepartbooking.service;

import com.ford.sparepartbooking.dto.ItemDTO;
import com.ford.sparepartbooking.entity.Item;
import com.ford.sparepartbooking.repository.ItemRepository.ItemSpecification;
import javax.persistence.criteria.CriteriaBuilder.In;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface ItemService {

  void updateItem(Long id, ItemDTO dto);

  void updateItem(Item item);

  Item addItem(ItemDTO dto);
  Page<ItemDTO> getItems(ItemQuery itemQuery);

  Item getItemById(Long id);

  ItemDTO getItemDTOById(Long id);

  void updateQuantity(Long itemId, Integer quantity);

  void deleteById(Long id);

  @Data
  class ItemQuery{
    private Long id;
    private String name;
    private Integer page = 0;
    private Integer pageSize = 10;
    public ItemSpecification toSpecification() { return new ItemSpecification(this);}

    public Pageable toPageable() {
      return PageRequest.of(page, pageSize);
    }

  }
}
