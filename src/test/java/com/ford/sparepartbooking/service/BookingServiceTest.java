package com.ford.sparepartbooking.service;

import static org.mockito.Mockito.*;

import com.ford.sparepartbooking.dto.OrderDTO;
import com.ford.sparepartbooking.dto.OrderItemDTO;
import com.ford.sparepartbooking.entity.User;
import com.ford.sparepartbooking.repository.OrderItemRepository;
import com.ford.sparepartbooking.repository.OrderRepository;
import com.ford.sparepartbooking.repository.UserRepository;
import com.ford.sparepartbooking.service.impl.BookingServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

  @InjectMocks private BookingServiceImpl bookingService;

  @Mock
  private  OrderRepository orderRepository;
  @Mock
  private  OrderItemRepository orderItemRepository;
  @Mock
  private  UserRepository userRepository;
  @Mock private ItemService itemService;

  @Test
  void test_OutOfStockException() {
    OrderDTO dto = new OrderDTO();
    OrderItemDTO itemDTO = new OrderItemDTO();
    itemDTO.setItemId(1L);
    itemDTO.setQuantity(2);
    dto.setItems(List.of(itemDTO));
    bookingService.placeOrder(mock(Authentication.class), dto);

  }
}
