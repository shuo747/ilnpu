package com.shuo747.ilnpu.repository;


import com.shuo747.ilnpu.entity.Course;
import com.shuo747.ilnpu.entity.WxUser;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Qualifier("wxUserDao")
public interface WxUserDao extends CrudRepository<WxUser, String> {


    @CacheEvict(value = "WxUser", key = "'WxUser'+ '_' + #p0.openid")
    WxUser save(WxUser wxUser);


    @Cacheable(unless="#result == null",value = "WxUser", key = "'WxUser'+ '_' + #p0")
    @Query("select u from WxUser u where u.openid = :openid")
    WxUser findByWxUserOpenid(@Param("openid") String openid);


    @Modifying
    @Transactional
    @CacheEvict(value = "WxUser", key = "'WxUser'+ '_' + #p0")
    @Query("update WxUser u set u.sid = :sid where u.openid = :openid")
    void updateSidByOpenid(@Param("openid") String openid,
                           @Param("sid") Long sid);

    //@Cacheable(value = "sid", key = "#root.targetClass + '_' + #p0 + '_' + #p1")
    @Query("select u.sid from WxUser u where u.openid = :openid")
    Long findSidByWxUserName(@Param("openid") String openid);


    @Cacheable(unless="#result == null" ,value = "WxUserCheck", key = "'WxUserCheck' + '_' + #p0")
    @Query("select count(u) from WxUser u where u.openid = :openid")
    int checkExistWxUser(@Param("openid") String openid);

    @Query("select u.openid from WxUser u where u.sid = :sid")
    String findOpenidBySid(@Param("sid") long sid);




}
