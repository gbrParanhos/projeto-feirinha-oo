package com.feirinha.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feirinha.api.models.ItemsModel;
import com.feirinha.api.repositories.ItemsRepository;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/items")
public class ItemsController {
  final ItemsRepository itemsRepository;

  ItemsController(ItemsRepository itemsRepository) {
    this.itemsRepository = itemsRepository;
  }

  @GetMapping()
  public List<ItemsModel> getItems() {
    return itemsRepository.findAll();
  }

}
