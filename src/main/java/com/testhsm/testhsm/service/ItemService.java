package com.testhsm.testhsm.service;

import com.testhsm.testhsm.dto.ItemRequestDTO;
import com.testhsm.testhsm.dto.ItemResponseDTO;
import com.testhsm.testhsm.entity.Item;
import com.testhsm.testhsm.entity.Satuan;
import com.testhsm.testhsm.repository.ItemRepository;
import com.testhsm.testhsm.repository.SatuanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private SatuanRepository satuanRepository;

    @Autowired
    private ItemRepository itemRepository;

    public Item createItem(ItemRequestDTO itemRequestDTO) {
        Satuan satuan = satuanRepository.findById(itemRequestDTO.getSatuanId())
                .orElseThrow(() -> new RuntimeException("Satuan ID: " + itemRequestDTO.getSatuanId() + " does not exist."));

        Item item = new Item();
        item.setSatuan(satuan);
        item.setBarcode(itemRequestDTO.getBarcode());
        item.setItemCode(itemRequestDTO.getItemCode());
        item.setItemName(itemRequestDTO.getItemName());

        return itemRepository.save(item);
    }

    public List<Item> getAllItem() {
        return itemRepository.findAll();
    }

    public Page<ItemResponseDTO> findPaginatedItem(int pageNo, int pageSize, Sort sort) {
        int adjustedPageNo = Math.max(pageNo, 1);
        Pageable pageable = PageRequest.of(adjustedPageNo-1, pageSize, sort);
        Page<Item> itemPage = itemRepository.findAll(pageable);
        return itemPage.map(this::convertItemToResponseDTO);
    }

    private ItemResponseDTO convertItemToResponseDTO(Item item) {
        return ItemResponseDTO.builder()
                .id(item.getId())
                .barcode(item.getBarcode())
                .createdAt(item.getCreatedAt())
                .itemCode(item.getItemCode())
                .itemName(item.getItemName())
                .satuanId(item.getSatuan().getId())
                .updatedAt(item.getUpdatedAt())
                .build();
    }

    public Item findItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item ID: " + id + " does not exist."));
    }

    public Item updateItem(Item item) {
        Item existingItem = itemRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("Item ID: " + item.getId() + " does not exist."));

        if (item.getBarcode() != null) {
            existingItem.setBarcode(item.getBarcode());
        }

        if (item.getItemCode() != null) {
            existingItem.setItemCode(item.getItemCode());
        }

        if (item.getItemName() != null) {
            existingItem.setItemName(item.getItemName());
        }

        if (item.getSatuan() != null) {
            existingItem.setSatuan(item.getSatuan());
        }

        return itemRepository.save(existingItem);
    }


    public void deleteItem(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new RuntimeException("Item ID: " + id + " does not exist.");
        }

        itemRepository.deleteById(id);
    }
}
