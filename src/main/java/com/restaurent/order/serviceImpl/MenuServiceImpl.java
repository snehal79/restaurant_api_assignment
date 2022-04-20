package com.restaurent.order.serviceImpl;

import com.restaurent.order.dao.MenuItemDao;
import com.restaurent.order.model.MenuItem;
import com.restaurent.order.service.MenuService;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {
    private MenuItemDao menuItemDao = new MenuItemDao();

    @Override
    public int registerMenuItem(MenuItem menuItem) {
        int count = menuItemDao.registerMenu(menuItem);
        return count;
    }
}
