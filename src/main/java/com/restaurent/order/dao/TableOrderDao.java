package com.restaurent.order.dao;

import com.restaurent.order.model.TableItem;
import com.restaurent.order.model.TableOrderDetails;
import com.restaurent.order.util.DBUtil;
import com.restaurent.order.util.ItemPreparationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class performs DB manipulation operations for all the orders based query parameters such as adding orders,
 * removing item from an order,get items for a table number etc.
 */
@Service
public class TableOrderDao {
    private static final Logger logger = LoggerFactory.getLogger(TableOrderDao.class);
    int PREPARATION_STATUS_ORDERED = 0;

    public int addOrder(TableOrderDetails tableOrderDetails) {
        Connection connection = DBUtil.getConnection();
        int countOrderDetail = 0;
        if (connection != null) {
            String queryTableOrder = "INSERT INTO table_order_details(table_no, item_id, preparation_status, created_at) VALUES(?,?,?,?)";
            try {
                connection.setAutoCommit(false);
                PreparedStatement preparedStatementOrdersDetails = connection.prepareStatement(queryTableOrder);
                int size = tableOrderDetails.getItemIds().size();
                for (int i = 0; i < size; i++) {
                    preparedStatementOrdersDetails.setString(1, tableOrderDetails.getTableNo() + "");
                    preparedStatementOrdersDetails.setString(2, tableOrderDetails.getItemIds().get(i).toString());
                    preparedStatementOrdersDetails.setInt(3, PREPARATION_STATUS_ORDERED);
                    preparedStatementOrdersDetails.setString(4, LocalDateTime.now().toString());
                    preparedStatementOrdersDetails.addBatch();
                    countOrderDetail++;
                }
                if (countOrderDetail == size) {
                    preparedStatementOrdersDetails.executeBatch();
                }
                connection.commit();
                if (countOrderDetail == size) {
                    logger.info("addOrder(): Order Added Successfully");
                } else {
                    countOrderDetail = 0;
                    logger.error("addOrder(): Order Not Added");
                }
            } catch (SQLException exception) {
                countOrderDetail = 0;
                logger.error("addOrder(): SQL Exception Occurred " + exception.getMessage());
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    countOrderDetail = 0;
                    logger.error("addOrder(): SQL Exception Occurred " + e.getMessage());
                }
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("addOrder(): SQL Exception Occurred " + e.getMessage());
                }
            }
        }
        return countOrderDetail;
    }

    public int deleteItem(int tableNo, int itemId) {
        Connection connection = DBUtil.getConnection();
        String sql = "DELETE FROM table_order_details where table_no=? and item_Id=?";
        int res = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, tableNo);
            stmt.setInt(2, itemId);
            res = stmt.executeUpdate();
            logger.info("deleteItem() : Record deleted successfully");
        } catch (SQLException e) {
            logger.error("deleteItem() SQL Exception: " + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("deleteItem() SQL Exception: " + e.getMessage());
            }
        }
        return res;
    }

    public List<TableItem> getItemsByTableNo(int tableNo) {
        Connection connection = DBUtil.getConnection();
        List<TableItem> itemList = new ArrayList<>();
        try {
            String sql = "SELECT tod.item_id as item_id, tod.preparation_status as preparation_status, tod.created_at as created_at, mi.item_name as item_name, mi.preparation_time as preparation_time FROM table_order_details as tod INNER JOIN menu_items as mi on tod.item_id = mi.item_id  where tod.table_no=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, tableNo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TableItem tableItem = new TableItem();
                tableItem.setItemId(rs.getInt("item_id"));
                int status = (rs.getInt("preparation_status"));
                tableItem.setPreparationStatus(String.valueOf(ItemPreparationStatus.getValue(status)));
                tableItem.setCreatedAt((rs.getTimestamp("created_at")).toLocalDateTime());
                tableItem.setItemName(rs.getString("item_name"));
                tableItem.setCookingTime(rs.getString("preparation_time") + " Minutes");
                itemList.add(tableItem);
            }
        } catch (Exception e) {
            logger.error("getItemsByTableNo() SQL Exception: " + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("getItemsByTableNo() SQL Exception: " + e.getMessage());
            }
        }
        return itemList;
    }

    public TableItem getItemByTableNoAndItemId(int tableNo, int itemId) {
        Connection connection = DBUtil.getConnection();
        TableItem tableItem = null;
        try {
            String sql = "SELECT tod.item_id as item_id, tod.preparation_status as preparation_status, tod.created_at as created_at, mi.item_name as item_name, mi.preparation_time as preparation_time FROM table_order_details as tod INNER JOIN menu_items as mi on tod.item_id = mi.item_id  where tod.table_no=? and tod.item_id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, tableNo);
            stmt.setInt(2, itemId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tableItem = new TableItem();
                tableItem.setItemId(rs.getInt("item_id"));
                int status = (rs.getInt("preparation_status"));
                tableItem.setPreparationStatus(String.valueOf(ItemPreparationStatus.getValue(status)));
                tableItem.setCreatedAt((rs.getTimestamp("created_at")).toLocalDateTime());
                tableItem.setItemName(rs.getString("item_name"));
                tableItem.setCookingTime(rs.getString("preparation_time") + " Minutes");
            }
        } catch (Exception e) {
            logger.error("getItemByTableNoAndItemId() SQL Exception: " + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("updatePreparationStatusOfItem() SQL Exception: " + e.getMessage());
            }
        }
        return tableItem;
    }

    public int updatePreparationStatusOfItem(int tableNo, int itemId, int preparationStatus) {
        Connection connection = DBUtil.getConnection();
        int res = 0;
        String sql = "UPDATE table_order_details set preparation_status=? where table_no=? and item_Id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, preparationStatus);
            stmt.setInt(2, tableNo);
            stmt.setInt(3, itemId);
            res = stmt.executeUpdate();
            logger.info("updatePreparationStatusOfItem(): Record updated successfully");
        } catch (SQLException e) {
            logger.error("updatePreparationStatusOfItem() SQL Exception: " + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("updatePreparationStatusOfItem() SQL Exception: " + e.getMessage());
            }
        }
        return res;
    }

    public List<TableItem> getItemsByTableNoAndPreparationStatus(int tableNo, int preparationStatus) {
        Connection connection = DBUtil.getConnection();
        List<TableItem> itemList = new ArrayList<>();
        try {
            String sql = "SELECT tod.item_id as item_id, tod.preparation_status as preparation_status, tod.created_at as created_at, mi.item_name as item_name, mi.preparation_time as preparation_time FROM table_order_details as tod INNER JOIN menu_items as mi on tod.item_id = mi.item_id  where tod.table_no=? and tod.preparation_status=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, tableNo);
            stmt.setInt(2, preparationStatus);
            logger.info("getItemsByTableNoAndPreparationStatus(): " + stmt);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TableItem tableItem = new TableItem();
                tableItem.setItemId(rs.getInt("item_id"));
                int status = (rs.getInt("preparation_status"));
                tableItem.setPreparationStatus(String.valueOf(ItemPreparationStatus.getValue(status)));
                tableItem.setCreatedAt((rs.getTimestamp("created_at")).toLocalDateTime());
                tableItem.setItemName(rs.getString("item_name"));
                tableItem.setCookingTime(rs.getString("preparation_time") + " Minutes");
                itemList.add(tableItem);
            }
        } catch (Exception e) {
            logger.error("getItemsByTableNoAndPreparationStatus() SQL Exception: " + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("getItemsByTableNoAndPreparationStatus() SQL Exception: " + e.getMessage());
            }
        }
        return itemList;
    }
}

