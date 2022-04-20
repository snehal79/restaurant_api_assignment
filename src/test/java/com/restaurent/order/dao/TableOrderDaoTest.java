package com.restaurent.order.dao;

import com.restaurent.order.model.TableItem;
import com.restaurent.order.model.TableOrderDetails;
import com.restaurent.order.util.DBUtil;
import com.restaurent.order.util.ItemPreparationStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TableOrderDaoTest {

    int testTableNo = Integer.MAX_VALUE;

    TableOrderDao tableOrderDao = new TableOrderDao();

    @BeforeEach
    void addTestData() {
        TableOrderDetails tableOrderDetails = new TableOrderDetails();
        tableOrderDetails.setTableNo(testTableNo);
        tableOrderDetails.setItemIds(Arrays.asList(1, 2));
        tableOrderDetails.setCreatedAt(LocalDateTime.now());
        tableOrderDetails.setPreparationStatus(Arrays.asList(0, 0));
        tableOrderDao.addOrder(tableOrderDetails);
    }

    @AfterEach
    void deleteTestData() {
        Connection connection = DBUtil.getConnection();
        String sql = "DELETE FROM table_order_details where table_no=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, testTableNo);
            stmt.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Test
    void addOrder() {
        int beforeCount = tableOrderDao.getItemsByTableNo(testTableNo).size();
        TableOrderDetails tableOrderDetails = new TableOrderDetails();
        tableOrderDetails.setTableNo(testTableNo);
        tableOrderDetails.setItemIds(Arrays.asList(1, 2, 3, 4));
        tableOrderDetails.setCreatedAt(LocalDateTime.now());
        tableOrderDetails.setPreparationStatus(Arrays.asList(0, 0, 0, 0));
        tableOrderDao.addOrder(tableOrderDetails);
        int afterCount = tableOrderDao.getItemsByTableNo(testTableNo).size();
        assertEquals(beforeCount + 4, afterCount);
    }

    @Test
    void deleteItem() {
        tableOrderDao.deleteItem(testTableNo, 2);
        assertNull(tableOrderDao.getItemByTableNoAndItemId(testTableNo, 2));
    }

    @Test
    void getItemsByTableNo() {
        List<TableItem> itemList = tableOrderDao.getItemsByTableNo(testTableNo);
        assertEquals(2, itemList.size());
    }

    @Test
    void getItemByTableNoAndItemId() {
        TableItem tableItem = tableOrderDao.getItemByTableNoAndItemId(testTableNo, 2);
        assertEquals(2, tableItem.getItemId());
    }

    @Test
    void updatePreparationStatusOfItem() {
        int res = tableOrderDao.updatePreparationStatusOfItem(testTableNo, 2, 1);
        TableItem item = tableOrderDao.getItemByTableNoAndItemId(testTableNo, 2);
        assertEquals(String.valueOf(ItemPreparationStatus.getValue(1)), item.getPreparationStatus());
    }

    @Test
    void getItemsByTableNoAndPreparationStatus() {
        List<TableItem> itemList = tableOrderDao.getItemsByTableNoAndPreparationStatus(testTableNo, 0);
        assertThat(itemList).size().isGreaterThan(0);
    }
}