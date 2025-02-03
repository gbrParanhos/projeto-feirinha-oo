package com.feirinha.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.feirinha.api.dtos.ItemDTO;
import com.feirinha.api.models.ItemsModel;
import com.feirinha.api.repositories.ItemsRepository;

@Service
public class ItemsService {
  final ItemsRepository itemsRepository;

  ItemsService(ItemsRepository itemsRepository) {
    this.itemsRepository = itemsRepository;
  }

  public Optional<ItemsModel> saveItem(ItemDTO dto) {
    if (itemsRepository.existsByName(dto.getName())) {
      return Optional.empty();
    }
    ItemsModel item = new ItemsModel(dto);
    itemsRepository.save(item);
    return Optional.of(item);
  }

  public List<ItemsModel> findAll() {
    return itemsRepository.findAll();
  }

  public Optional<ItemsModel> findById(Long id) {
    return itemsRepository.findById(id);
  }

  public Optional<ItemsModel> updateItem(Long id, ItemDTO dto) {
    if (!itemsRepository.existsById(id)) {
      return Optional.empty();
    }

    Optional<ItemsModel> item = itemsRepository.findByName(dto.getName());
    if (item.isPresent()) {
      return item;
    }

    ItemsModel newItem = new ItemsModel(dto);
    newItem.setId(id);
    itemsRepository.save(newItem);
    return Optional.of(newItem);
  }

}
