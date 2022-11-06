package com.ford.sparepartbooking.controller;

import com.ford.sparepartbooking.dto.ItemDTO;
import com.ford.sparepartbooking.entity.Item;
import com.ford.sparepartbooking.service.ItemService;
import com.ford.sparepartbooking.service.ItemService.ItemQuery;
import javax.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/items")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ItemController {
  private final ItemService service;

  @GetMapping("/{id}")
  @RolesAllowed({"USER", "ADMIN"})
  public ResponseEntity<ItemDTO> getById(@PathVariable Long id) {
    return ResponseEntity.ok(service.getItemDTOById(id));
  }

  @GetMapping
  @RolesAllowed({"USER", "ADMIN"})
  public ResponseEntity<Page<ItemDTO>> getBySpecification(@RequestBody(required = false) ItemQuery itemQuery) {
    return ResponseEntity.ok(service.getItems(itemQuery));
  }

  @PostMapping
  @RolesAllowed("ADMIN")
  public ResponseEntity<Void> addItem(
      @RequestBody ItemDTO dto, UriComponentsBuilder uriComponentsBuilder) {
    Item item = service.addItem(dto);
    return ResponseEntity.created(
            uriComponentsBuilder.path("/items/{id}").buildAndExpand(item.getId()).toUri())
        .build();
  }

  @PutMapping("/{id}")
  @RolesAllowed("ADMIN")
  public ResponseEntity<Void> updateItem(@PathVariable Long id, @RequestBody ItemDTO dto) {
    service.updateItem(id, dto);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/quantity/{quantity}")
  @RolesAllowed("ADMIN")
  public ResponseEntity<Void> updateQuantity(
      @PathVariable Long id, @PathVariable Integer quantity) {
    service.updateQuantity(id, quantity);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  @RolesAllowed("ADMIN")
  public ResponseEntity<Void> deleteQuantity(@PathVariable Long id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
