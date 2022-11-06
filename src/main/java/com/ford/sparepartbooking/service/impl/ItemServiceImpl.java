package com.ford.sparepartbooking.service.impl;

import com.ford.sparepartbooking.dto.ItemDTO;
import com.ford.sparepartbooking.entity.Item;
import com.ford.sparepartbooking.exception.ItemNotFoundException;
import com.ford.sparepartbooking.repository.ItemRepository;
import com.ford.sparepartbooking.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

  private final ItemRepository itemRepository;

  @Override
  public void updateItem(Long id, ItemDTO dto) {
    Item itemById = getItemById(id);
    itemById.update(dto);
    itemRepository.save(itemById);
  }

  @Override
  public void updateItem(Item item) {
    itemRepository.save(item);
  }

  @Override
  public Item addItem(ItemDTO dto) {
    return itemRepository.save(new Item(dto));
  }

  @Override
  public Page<ItemDTO> getItems(ItemQuery itemQuery) {
    if (itemQuery == null) {
      itemQuery = new ItemQuery();
      return itemRepository
          .findAll(itemQuery.toPageable())
          .map(ItemDTO::new);
    }
    return itemRepository
        .findAll(itemQuery.toSpecification(), itemQuery.toPageable())
        .map(ItemDTO::new);
  }

  @Override
  public Item getItemById(Long id) {
    return itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
  }

  @Override
  public ItemDTO getItemDTOById(Long id) {
    return new ItemDTO(getItemById(id));
  }

  @Override
  public void updateQuantity(Long itemId, Integer quantity) {
    Item itemById = getItemById(itemId);
    itemById.updateQuantity(quantity);
    itemRepository.save(itemById);
  }

  @Override
  public void deleteById(Long id) {
    Item itemById = getItemById(id);
    itemRepository.delete(itemById);
  }
}
