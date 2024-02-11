package com.testhsm.testhsm.controller;

import com.testhsm.testhsm.dto.ItemRequestDTO;
import com.testhsm.testhsm.dto.ItemResponseDTO;
import com.testhsm.testhsm.entity.Item;
import com.testhsm.testhsm.entity.Satuan;
import com.testhsm.testhsm.service.ItemService;
import com.testhsm.testhsm.service.SatuanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private SatuanService satuanService;

    @Autowired
    private ItemService itemService;

    @PostMapping()
    public ResponseEntity<ItemResponseDTO> createItem(@RequestBody ItemRequestDTO itemRequestDTO) {
        Item savedItem = itemService.createItem(itemRequestDTO);

        ItemResponseDTO itemResponseDTO = ItemResponseDTO.builder()
                .id(savedItem.getId())
                .barcode(savedItem.getBarcode())
                .createdAt(savedItem.getCreatedAt())
                .itemCode(savedItem.getItemCode())
                .itemName(savedItem.getItemName())
                .updatedAt(savedItem.getUpdatedAt())
                .build();

        return ResponseEntity.ok(itemResponseDTO);
    }

    @GetMapping("/pagination")
    public Page<ItemResponseDTO> getPaginatedItem(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortby,
            @RequestParam(defaultValue = "asc") String direction)
    {
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortby).ascending() : Sort.by(sortby).descending();
        return itemService.findPaginatedItem(pageNo, pageSize, sort);
    }

    @GetMapping
    public List<ItemResponseDTO> getAllItem() {
        return itemService.getAllItem().stream()
                .map(item -> new ItemResponseDTO(item.getId(), item.getBarcode(),
                        item.getCreatedAt(), item.getItemCode(), item.getItemName(),
                        item.getSatuan().getId(), item.getUpdatedAt()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItemById(@PathVariable Long id) {
        try {
            Item item = itemService.findItemById(id);

            ItemResponseDTO itemResponseDTO = ItemResponseDTO.builder()
                    .id(item.getId())
                    .barcode(item.getBarcode())
                    .createdAt(item.getCreatedAt())
                    .itemCode(item.getItemCode())
                    .itemName(item.getItemName())
                    .satuanId(item.getSatuan().getId())
                    .updatedAt(item.getUpdatedAt())
                    .build();

            return ResponseEntity.ok(itemResponseDTO);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateItem(@PathVariable Long id, @RequestBody ItemRequestDTO itemRequestDTO) {
        try {
            Optional<Satuan> satuanOptional = itemRequestDTO.getSatuanId() != null ? Optional.ofNullable(satuanService.findSatuanById(itemRequestDTO.getSatuanId())) : Optional.empty();
            if (itemRequestDTO.getSatuanId() != null && !satuanOptional.isPresent()) {
                return new ResponseEntity<>("Satuan ID not found", HttpStatus.NOT_FOUND);
            }

            Item item = new Item();
            item.setId(id);
            item.setBarcode(itemRequestDTO.getBarcode());
            item.setItemCode(itemRequestDTO.getItemCode());
            item.setItemName(itemRequestDTO.getItemName());
            item.setSatuan(satuanOptional.orElse(null));

            Item updatedItem = itemService.updateItem(item);

            if (updatedItem == null) {
                return new ResponseEntity<>("Item ID not found ", HttpStatus.NOT_FOUND);
            }

            ItemResponseDTO itemResponseDTO = ItemResponseDTO.builder()
                    .id(updatedItem.getId())
                    .barcode(updatedItem.getBarcode())
                    .createdAt(updatedItem.getCreatedAt())
                    .itemCode(updatedItem.getItemCode())
                    .itemName(updatedItem.getItemName())
                    .satuanId(updatedItem.getSatuan() != null ? updatedItem.getSatuan().getId() : null)
                    .updatedAt(updatedItem.getUpdatedAt())
                    .build();

            return ResponseEntity.ok(itemResponseDTO);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        try {
            itemService.deleteItem(id);
            return ResponseEntity.ok("Item ID: " + id + " was successfully deleted.");
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
