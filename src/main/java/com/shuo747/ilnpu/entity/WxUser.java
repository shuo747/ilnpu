package com.shuo747.ilnpu.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class WxUser implements Serializable {
    @Id
    private String openid;
    private String session_key;
    private Long sid;
    private String unionid;

    public WxUser(String openid, String session_key) {
        this.openid = openid;
        this.session_key = session_key;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public WxUser(String openid, Long sid) {
        this.openid = openid;
        this.sid = sid;
    }

    public WxUser() {
    }

    @Override
    public String toString() {
        return "WxUser{" +
                "openid='" + openid + '\'' +
                ", session_key='" + session_key + '\'' +
                ", sid='" + sid + '\'' +
                ", unionid='" + unionid + '\'' +
                '}';
    }

    public WxUser(String openid, String session_key, Long sid, String unionid) {
        this.openid = openid;
        this.session_key = session_key;
        this.sid = sid;
        this.unionid = unionid;
    }
}
