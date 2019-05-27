package com.wsg.go.dbentity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2019-01-15 14:57
 * 描    述：
 * 修订历史：
 * ================================================
 */
@Entity
public class CoordinatePoint {
    @Id(autoincrement = true)
    private Long id;
    private Double latitude; //维度
    private Double longitude; //经度
    private Float speed; //速度
    private Float bearing; //方向角
    private Long time; //定位时间
    @Generated(hash = 299336800)
    public CoordinatePoint(Long id, Double latitude, Double longitude, Float speed,
            Float bearing, Long time) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.bearing = bearing;
        this.time = time;
    }
    @Generated(hash = 1450465170)
    public CoordinatePoint() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Double getLatitude() {
        return this.latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return this.longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public Float getSpeed() {
        return this.speed;
    }
    public void setSpeed(Float speed) {
        this.speed = speed;
    }
    public Float getBearing() {
        return this.bearing;
    }
    public void setBearing(Float bearing) {
        this.bearing = bearing;
    }
    public Long getTime() {
        return this.time;
    }
    public void setTime(Long time) {
        this.time = time;
    }
}
