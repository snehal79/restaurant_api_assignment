package com.restaurent.order.dao;

import com.restaurent.order.model.MenuItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MenuDaoTest {
    MenuItemDao menuDao = new MenuItemDao();

    @Test
    void registerMenu() {
        int beforeCount = menuDao.getMenuItemCount();
        MenuItem menuItem = new MenuItem();
        menuItem.setItemName("Sandwich");
        menuItem.setItemPrice(300);
        menuItem.setPreparationTime(5);
        menuDao.registerMenu(menuItem);
        int afterCount = menuDao.getMenuItemCount();
        assertEquals(beforeCount + 1, afterCount);

    }
}