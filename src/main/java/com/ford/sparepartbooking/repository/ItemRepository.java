package com.ford.sparepartbooking.repository;

import com.ford.sparepartbooking.entity.Item;
import com.ford.sparepartbooking.service.ItemService.ItemQuery;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {

  class ItemSpecification implements Specification<Item> {
    private ItemQuery itemQuery;

    public ItemSpecification(ItemQuery itemQuery) {
      this.itemQuery = itemQuery;
    }

    @Override
    public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query,
        CriteriaBuilder criteriaBuilder) {
      List<Predicate> predicates = new ArrayList<>();
      if (itemQuery.getId() != null) {
        predicates.add(criteriaBuilder.equal(root.get("id"), itemQuery.getId()));
      }
      if (itemQuery.getName() != null) {
        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), itemQuery.getName()));
      }

      return query
          .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
          .distinct(true)
          .getRestriction();
    }
  }
}
