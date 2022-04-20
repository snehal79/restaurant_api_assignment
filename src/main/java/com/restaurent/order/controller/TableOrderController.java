package com.restaurent.order.controller;

import com.restaurent.order.model.TableItem;
import com.restaurent.order.model.TableOrderDetails;
import com.restaurent.order.service.TableOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TableOrderController class is an entrypoint to all the incoming requests to manage incoming orders from serving staff.
 * It handles requests based on request parameters and processes them
 */
@RestController
public class TableOrderController {

    @Autowired
    private TableOrderService orderService;

    @PostMapping("/addOrder")
    public ResponseEntity registerOrder(@RequestBody TableOrderDetails tableOrderDetails) {
        if (orderService.addOrder(tableOrderDetails) != 0) {
            return new ResponseEntity<>("order added successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Can not add order, Check request parameters", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("order/{tableNo}/{itemId}")
    public ResponseEntity deleteItem(@PathVariable("tableNo") int tableNo, @PathVariable("itemId") int itemId) {
        if (orderService.deleteItem(tableNo, itemId) != 0) {
            return new ResponseEntity<>("Item deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Can not delete item, Check request parameters", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("order/{tableNo}")
    public List<TableItem> getItemsByTableNo(@PathVariable("tableNo") int tableNo) {
        List<TableItem> itemList = orderService.getItemsByTableNo(tableNo);
        return itemList;
    }

    @GetMapping("order/{tableNo}/{itemId}")
    public TableItem getItemsByTableNoAndItemId(@PathVariable("tableNo") int tableNo, @PathVariable("itemId") int itemId) {
        TableItem item = orderService.getItemByTableNoAndItemId(tableNo, itemId);
        return item;
    }

    @PutMapping("order/{tableNo}/{itemId}/{preparationStatus}")
    public ResponseEntity updatePreparationStatusOfItem(@PathVariable("tableNo") int tableNo, @PathVariable("itemId") int itemId, @PathVariable("preparationStatus") int preparationStatus) {
        if (orderService.updatePreparationStatusOfItem(tableNo, itemId, preparationStatus) != 0) {
            return new ResponseEntity<>("Item preparation status updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Can not update item preparation status, Check request parameters", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("orders")
    public List<TableItem> getItemsByTableNoAndPreparationStatus(@RequestParam("tableNo") int tableNo, @RequestParam("preparationStatus") int preparationStatus) {
        List<TableItem> remainingItem = orderService.getItemsByTableNoAndPreparationStatus(tableNo, preparationStatus);
        return remainingItem;
    }

}
