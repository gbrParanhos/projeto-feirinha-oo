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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

}
