package com.craigjperry.dashbutton;

public interface Action {
    /**
     * Called when a DashButton is pressed
     * @param source DashButton that was pressed
     * @return true if the next action should be invoked, false if this should be the last action
     */
    boolean perform(DashButton source);
}
