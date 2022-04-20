package com.restaurent.order.controller;

import com.restaurent.order.model.MenuItem;
import com.restaurent.order.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * MenuItemController used to accept request for adding new Menu Items
 */
@RestController
public class MenuItemController {
    @Autowired
    private MenuService menuService;

    @PostMapping("/registerMenu")
    public ResponseEntity registerMenuItem(@RequestBody MenuItem menuItem) {
        if (menuService.registerMenuItem(menuItem) != 0) {
            return new ResponseEntity<>("Item added successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Can not add Item, Check request parameters", HttpStatus.BAD_REQUEST);
        }
    }

}
