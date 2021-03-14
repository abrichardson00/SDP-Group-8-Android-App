package com.example.theostore;

import java.io.Serializable;

public class Tray implements Serializable {

    private static final String IMAGE_SERVER_ADDRESS = "http://10.0.2.2/";

    private int id;
    private String trayCode;
    private String userInfo;
    private String extractedInfo;
    private String status;
    private float capacity;

    public Tray(int id, String trayCode, String userInfo, String extractedInfo, String status, float capacity) {
        this.id = id;
        this.trayCode = trayCode;
        this.userInfo = userInfo;
        this.extractedInfo = extractedInfo;
        this.status = status;
        this.capacity = capacity;
    }

    public Tray() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrayCode() {
        return trayCode;
    }

    public void setTrayCode(String trayCode) {
        this.trayCode = trayCode;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getExtractedInfo() {
        return extractedInfo;
    }

    public void setExtractedInfo(String extractedInfo) {
        this.extractedInfo = extractedInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getCapacity() {
        return capacity;
    }

    public void setCapacity(float capacity) {
        this.capacity = capacity;
    }

    public String getURI() {
        return IMAGE_SERVER_ADDRESS + trayCode + ".jpg";
    }

    @Override
    public String toString() {
        return "Tray{" +
                "id=" + id +
                ", tray_code='" + trayCode + '\'' +
                ", user_info='" + userInfo + '\'' +
                ", extracted_info='" + extractedInfo + '\'' +
                ", status='" + status + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}
