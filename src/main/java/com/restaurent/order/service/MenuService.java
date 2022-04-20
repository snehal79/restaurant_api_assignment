package com.restaurent.order.service;

import com.restaurent.order.model.MenuItem;
import org.springframework.stereotype.Service;

@Service
public interface MenuService {
    int registerMenuItem(MenuItem menuItem);
}
