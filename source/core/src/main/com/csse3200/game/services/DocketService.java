package com.csse3200.game.services;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.csse3200.game.components.ordersystem.Docket;
import com.csse3200.game.components.ordersystem.MainGameOrderTicketDisplay;

import java.util.ArrayList;
import java.util.List;

/**
 * Service responsible for managing the shifting and movement of dockets.
 */
public class DocketService {
    private List<Docket> dockets;
    private Table docketTable;
    private MainGameOrderTicketDisplay orderTicketDisplay;

    public DocketService() {
        this.dockets = new ArrayList<>();
        createDocketTable();
    }

    /**
     * Creates the table that will hold and display the Dockets.
     * The table is set to align to the top-left corner and fill its parent container.
     */
    private void createDocketTable() {
        docketTable = new Table();
        docketTable.top().left();
        docketTable.setFillParent(true);
    }

    /**
     * Provides access to the table that holds the Dockets.
     */
    public Table getDocketTable() {
        return docketTable;
    }

    /**
     * Shifts all Dockets to the left, moving the first Docket to the end of the list.
     * Updates the positions of all Dockets afterward.
     */
    public void shiftDocketsLeft() {
        if (dockets.isEmpty()) return;

        Docket firstDocket = dockets.remove(0);
        dockets.add(firstDocket);
        updateDocketPositions();
    }

    /**
     * Shifts all Dockets to the right, moving the last Docket to the beginning of the list.
     * Updates the positions of all Dockets afterward.
     */
    public void shiftDocketsRight() {
        if (dockets.isEmpty()) return;

        Docket lastDocket = dockets.remove(dockets.size() - 1);
        dockets.add(0, lastDocket);
        updateDocketPositions();
    }

    public void addDocket(Docket docket) {
        dockets.add(docket);
        updateDocketPositions();
    }

    public void removeDocket(Docket docket) {
        dockets.remove(docket);
        updateDocketPositions();
    }

    /**
     * Updates the visual positions and sizes of all Dockets in the table.
     * The last Docket is enlarged and positioned prominently, while others are displayed in standard size.
     */
    private void updateDocketPositions() {
        docketTable.clear();

        for (int i = 0; i < dockets.size(); i++) {
            Docket docket = dockets.get(i);
            if (i == dockets.size() - 1) {
                // Enlarge the last docket
                docket.getImage().setSize(200, 300); 
            } else {
                docket.getImage().setSize(100, 150);
            }
            docketTable.add(docket.getImage()).expandX().pad(5).top();
            docketTable.row();
        }

        docketTable.invalidateHierarchy(); // Recalculate layout

        // Notify the order ticket display that the dockets have been updated
        if (orderTicketDisplay != null) {
            orderTicketDisplay.updateDocketDisplay();
        }
    }

     /**
     * Gets the reference to the MainGameOrderTicketDisplay instance.
     */
    public MainGameOrderTicketDisplay getOrderTicketDisplay() {
        return orderTicketDisplay;
    }

    /**
     * Sets the reference to the MainGameOrderTicketDisplay instance.
     */
    public void setOrderTicketDisplay(MainGameOrderTicketDisplay orderTicketDisplay) {
        this.orderTicketDisplay = orderTicketDisplay;
    }
}
