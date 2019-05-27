package com.wsg.go.entity;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2018-09-05 10:18
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class HomeResourceData {

    public HomeResourceData() {
    }

    public HomeResourceData(String title, int drwableResource) {
        this.title = title;
        this.drwableResource = drwableResource;
    }

    private String title;
    private int drwableResource;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDrwableResource() {
        return drwableResource;
    }

    public void setDrwableResource(int drwableResource) {
        this.drwableResource = drwableResource;
    }
}
