package com.restaurent.order.dao;

import com.restaurent.order.model.MenuItem;
import com.restaurent.order.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MenuItemDao DB manipulation for adding new Menu items
 */
public class MenuItemDao {
    private static final Logger logger = LoggerFactory.getLogger(MenuItemDao.class);

    public int registerMenu(MenuItem menuItem) {
        Connection connection = DBUtil.getConnection();
        int count = 0;
        if (connection != null) {
            String query = "INSERT INTO menu_items(item_name,item_price,preparation_time) values(?,?,?)";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, menuItem.getItemName());
                ps.setInt(2, menuItem.getItemPrice());
                ps.setInt(3, menuItem.getPreparationTime());
                count = ps.executeUpdate();
            } catch (SQLException exception) {
                logger.error("SQL Exception " + exception.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("SQL Exception registerMenu()" + e.getMessage());
                }
            }
        }

        return count;
    }

    public int getMenuItemCount() {
        Connection connection = DBUtil.getConnection();
        int count = 0;
        if (connection != null) {
            String query = "select count(*) as count from  menu_items";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            } catch (SQLException exception) {
                logger.error("SQL Exception " + exception.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("SQL Exception getMenuItemCount()" + e.getMessage());
                }
            }
        }
        return count;
    }

}
