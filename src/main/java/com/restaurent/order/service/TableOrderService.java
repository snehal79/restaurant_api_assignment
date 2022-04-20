package com.restaurent.order.service;

import com.restaurent.order.model.TableItem;
import com.restaurent.order.model.TableOrderDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TableOrderService {
    int addOrder(TableOrderDetails tableOrderDetails);

    int deleteItem(int tableNo, int itemId);

    List<TableItem> getItemsByTableNo(int tableNo);

    TableItem getItemByTableNoAndItemId(int tableNo, int itemId);

    int updatePreparationStatusOfItem(int tableNo, int itemId, int preparationStatus);

    List<TableItem> getItemsByTableNoAndPreparationStatus(int tableNo, int preparationStatus);
}
