package com.restaurent.order.util;

public enum ItemPreparationStatus {
    ORDERED, PREPARING, FINISHED;

    public static ItemPreparationStatus getValue(int i) {
        if (i == 0)
            return ORDERED;
        else if (i == 1)
            return PREPARING;
        return FINISHED;
    }

}
