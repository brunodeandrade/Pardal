package com.modesteam.pardal;

import helpers.Category;

/**
 * Created by luis on 02/06/15.
 */
public interface ComparableCategory {
    public int getId();
    public int getTotalTickets();
    public Double getAverageExceded();
    public Double getMaximumMeasuredVelocity();
    public String toString();
    public Category getCategory();
}
