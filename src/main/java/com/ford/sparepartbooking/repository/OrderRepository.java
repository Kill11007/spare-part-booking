package com.ford.sparepartbooking.repository;

import com.ford.sparepartbooking.entity.Item;
import com.ford.sparepartbooking.entity.Order;
import com.ford.sparepartbooking.entity.OrderItem;
import com.ford.sparepartbooking.entity.User;
import com.ford.sparepartbooking.service.BookingService.OrderQuery;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository
    extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
  @RequiredArgsConstructor
  class OrderSpecification implements Specification<Order> {
    private final OrderQuery orderQuery;

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query,
        CriteriaBuilder criteriaBuilder) {
      List<Predicate> predicates = new ArrayList<>();
      Join<OrderItem, Order> orderItems = root.join("orderItems");
      Join<Item, OrderItem> item = orderItems.join("item");
      Join<User, Order> user = root.join("user");
      if (orderQuery.getOrderId() != null) {
        predicates.add(criteriaBuilder.equal(root.get("id"), orderQuery.getOrderId()));
      }
      if (orderQuery.getUserId() != null) {
        predicates.add(criteriaBuilder.equal(user.get("id"), orderQuery.getUserId()));
      }

      if (orderQuery.getUserName() != null) {
        predicates.add(criteriaBuilder.equal(user.get("name"), orderQuery.getUserName()));
      }
      if (orderQuery.getItemId() != null) {
        predicates.add(criteriaBuilder.equal(item.get("id"), orderQuery.getItemId()));
      }

      if (orderQuery.getItemQuantity() != null) {
        predicates.add(
            criteriaBuilder.equal(orderItems.get("quantity"), orderQuery.getItemQuantity()));
      }
      if (orderQuery.getItemName() != null) {
        predicates.add(
            criteriaBuilder.equal(item.get("name"), orderQuery.getItemName()));
      }
      return query
          .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
          .distinct(true)
          .getRestriction();
    }
  }
}

