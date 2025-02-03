package com.feirinha.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feirinha.api.dtos.ItemDTO;
import com.feirinha.api.models.ItemsModel;
import com.feirinha.api.services.ItemsService;

import jakarta.validation.Valid;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/items")
public class ItemsController {

  final ItemsService itemsService;

  ItemsController(ItemsService itemsService) {
    this.itemsService = itemsService;
  }

  @PostMapping()
  public ResponseEntity<Object> postUser(@RequestBody @Valid ItemDTO body) {
    Optional<ItemsModel> item = itemsService.saveItem(body);

    if (!item.isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Um item com este nome já existe.");
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(item.get());
  }

  @GetMapping()
  public ResponseEntity<Object> getUsers() {
    return ResponseEntity.status(HttpStatus.OK).body(itemsService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getMethodName(@PathVariable("id") Long id) {
    Optional<ItemsModel> item = itemsService.findById(id);

    if (!item.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não existe um item com o id fornecido.");
    }
    return ResponseEntity.status(HttpStatus.OK).body(item.get());
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> putMethodName(@PathVariable Long id, @RequestBody @Valid ItemDTO body) {
    Optional<ItemsModel> item = itemsService.updateItem(id, body);

    if (!item.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi encontrado um item com este id.");
    }
    if (item.get().getName().equals(body.getName()) && !item.get().getId().equals(id)) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Um item com este nome já existe.");
    }

    return ResponseEntity.status(HttpStatus.OK).body(item.get());
  }

}
