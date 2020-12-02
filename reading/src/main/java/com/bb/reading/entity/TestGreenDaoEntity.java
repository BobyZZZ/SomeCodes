package com.bb.reading.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/7
 * Time: 22:41
 */
@Entity
public class TestGreenDaoEntity {

    @Id
    private String id;
    private String url;

    @Generated(hash = 1595035533)
    public TestGreenDaoEntity(String id, String url) {
        this.id = id;
        this.url = url;
    }
    @Generated(hash = 1426372528)
    public TestGreenDaoEntity() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "TestGreenDaoEntity{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

}
