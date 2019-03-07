package com.shuo747.ilnpu.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="userinfo")
public class Userinfo implements Serializable{
    @Id
    private String openid;
    private String nickName;
    private String gender;
    private String language;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;


    public Userinfo() {
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Userinfo userinfo = (Userinfo) o;

        if (openid != null ? !openid.equals(userinfo.openid) : userinfo.openid != null) return false;
        if (nickName != null ? !nickName.equals(userinfo.nickName) : userinfo.nickName != null) return false;
        if (gender != null ? !gender.equals(userinfo.gender) : userinfo.gender != null) return false;
        if (language != null ? !language.equals(userinfo.language) : userinfo.language != null) return false;
        if (city != null ? !city.equals(userinfo.city) : userinfo.city != null) return false;
        if (province != null ? !province.equals(userinfo.province) : userinfo.province != null) return false;
        if (country != null ? !country.equals(userinfo.country) : userinfo.country != null) return false;
        return avatarUrl != null ? avatarUrl.equals(userinfo.avatarUrl) : userinfo.avatarUrl == null;
    }

    @Override
    public int hashCode() {
        int result = openid != null ? openid.hashCode() : 0;
        result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (avatarUrl != null ? avatarUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Userinfo{" +
                "openid='" + openid + '\'' +
                ", nickName='" + nickName + '\'' +
                ", gender='" + gender + '\'' +
                ", language='" + language + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
