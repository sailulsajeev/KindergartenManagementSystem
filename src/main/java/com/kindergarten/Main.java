package com.kindergarten;

import com.kindergarten.gui.DashboardGUI;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new DashboardGUI();
        });
    }
}