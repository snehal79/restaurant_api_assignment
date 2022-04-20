package com.restaurent.order.serviceImpl;

import com.restaurent.order.dao.TableOrderDao;
import com.restaurent.order.model.TableItem;
import com.restaurent.order.model.TableOrderDetails;
import com.restaurent.order.service.TableOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableOrderServiceImpl implements TableOrderService {

    @Autowired
    TableOrderDao tableOrderDao;

    @Override
    public int addOrder(TableOrderDetails tableOrderDetails) {
        int res = tableOrderDao.addOrder(tableOrderDetails);
        return res;
    }

    @Override
    public int deleteItem(int tableNo, int itemId) {
        int res = tableOrderDao.deleteItem(tableNo, itemId);
        return res;
    }

    @Override
    public List<TableItem> getItemsByTableNo(int tableNo) {
        List<TableItem> res = tableOrderDao.getItemsByTableNo(tableNo);
        return res;
    }

    @Override
    public TableItem getItemByTableNoAndItemId(int tableNo, int itemId) {
        TableItem res = tableOrderDao.getItemByTableNoAndItemId(tableNo, itemId);
        return res;
    }

    @Override
    public int updatePreparationStatusOfItem(int tableNo, int itemId, int preparationStatus) {
        int res = tableOrderDao.updatePreparationStatusOfItem(tableNo, itemId, preparationStatus);
        return res;
    }

    @Override
    public List<TableItem> getItemsByTableNoAndPreparationStatus(int tableNo, int preparationStatus) {
        List<TableItem> res = tableOrderDao.getItemsByTableNoAndPreparationStatus(tableNo, preparationStatus);
        return res;
    }

}
