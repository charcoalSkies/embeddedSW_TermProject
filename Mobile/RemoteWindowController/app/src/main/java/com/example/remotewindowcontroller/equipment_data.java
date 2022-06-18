package com.example.remotewindowcontroller;

public class equipment_data {
    private String equipment_name;
    private String window_state;

    public equipment_data(){}

    public String getEquipment_name() {
        return equipment_name;
    }

    public void setEquipment_name(String equipment_name) {
        this.equipment_name = equipment_name;
    }

    public String getWindow_state() {
        return window_state;
    }

    public void setWindow_state(String window_state) {
        this.window_state = window_state;
    }

    public equipment_data(String equipment_name, String window_state)
    {
        this.equipment_name = equipment_name;
        this.window_state = window_state;
    }
}
