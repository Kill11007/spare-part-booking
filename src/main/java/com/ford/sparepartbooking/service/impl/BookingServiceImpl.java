package com.ford.sparepartbooking.service.impl;

import com.ford.sparepartbooking.dto.OrderDTO;
import com.ford.sparepartbooking.dto.OrderItemDTO;
import com.ford.sparepartbooking.entity.Item;
import com.ford.sparepartbooking.entity.Order;
import com.ford.sparepartbooking.entity.OrderItem;
import com.ford.sparepartbooking.entity.User;
import com.ford.sparepartbooking.exception.OrderNotCreatedException;
import com.ford.sparepartbooking.exception.OutOfStockException;
import com.ford.sparepartbooking.exception.UserNotFoundException;
import com.ford.sparepartbooking.repository.OrderItemRepository;
import com.ford.sparepartbooking.repository.OrderRepository;
import com.ford.sparepartbooking.repository.UserRepository;
import com.ford.sparepartbooking.service.BookingService;
import com.ford.sparepartbooking.service.ItemService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;
  private final UserRepository userRepository;
  private final ItemService itemService;

  @Override
  public Order placeOrder(Authentication authentication, OrderDTO dto) {
    User user = (User) authentication.getPrincipal();
    User finalUser = user;
    user =
        userRepository
            .findById(user.getId())
            .orElseThrow(() -> new UserNotFoundException(finalUser.getId()));
    Order order = new Order();
    order.setUser(user);
    if (dto == null || dto.getItems().size() == 0) {
      throw new OrderNotCreatedException("No items are present");
    }
    List<OrderItem> orderItems = getOrderItems(order, dto.getItems());
    order.setOrderItems(new HashSet<>(orderItems));
    order.setTotal(
        orderItems.stream()
            .map(OrderItem::getAmount)
            .reduce(BigDecimal.valueOf(0), BigDecimal::add));
    user.setOrder(order);
    orderRepository.save(order);
    orderItemRepository.saveAll(orderItems);
    return order;
  }

  private List<OrderItem> getOrderItems(Order savedOrder, List<OrderItemDTO> dtos) {
    List<OrderItem> orderItems = new ArrayList<>();
    for(OrderItemDTO dto: dtos){
      OrderItem orderItem = new OrderItem();
      Item item = itemService.getItemById(dto.getItemId());
      if (item.getQuantity() < dto.getQuantity()) {
        throw new OutOfStockException();
      }
      orderItem.setItem(item);
      orderItem.setOrder(savedOrder);
      orderItem.setQuantity(dto.getQuantity());
      BigDecimal amount = item.getPrice().multiply(new BigDecimal(dto.getQuantity()));
      orderItem.setAmount(amount);
      orderItems.add(orderItem);
      updateItemInventory(item, dto.getQuantity());
      item.addOrderItem(orderItem);
    }
    return orderItems;
  }

  private void updateItemInventory(Item item, Integer quantity) {
    item.setQuantity(item.getQuantity() - quantity);
    itemService.updateItem(item);
  }

  @Override
  public Page<OrderDTO> allOrders(OrderQuery orderQuery) {
    if (orderQuery == null) {
      orderQuery = new OrderQuery();
    }
    return orderRepository
        .findAll(orderQuery.toSpecification(), orderQuery.toPageable())
        .map(OrderDTO::new);
  }

}
