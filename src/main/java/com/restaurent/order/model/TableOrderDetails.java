package com.restaurent.order.model;

import java.time.LocalDateTime;
import java.util.List;

public class TableOrderDetails {
    int id;
    int tableNo;
    List<Integer> itemIds;
    List<Integer> preparationStatus;
    LocalDateTime createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Integer> itemIds) {
        this.itemIds = itemIds;
    }

    public List<Integer> getPreparationStatus() {
        return preparationStatus;
    }

    public void setPreparationStatus(List<Integer> preparationStatus) {
        this.preparationStatus = preparationStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

}
