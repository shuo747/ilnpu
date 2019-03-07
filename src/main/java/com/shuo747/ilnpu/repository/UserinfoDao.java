package com.shuo747.ilnpu.repository;


import com.shuo747.ilnpu.entity.Userinfo;
import com.shuo747.ilnpu.entity.WxUser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Qualifier("userinfoDao")
public interface UserinfoDao extends CrudRepository<Userinfo, String> {


    @CacheEvict(condition = "#result != null",value = "Userinfo", key = "'Userinfo'+ '_' + #p0.openid")
    Userinfo save(Userinfo userinfo);


    @Cacheable(unless="#result == null" ,value = "Userinfo", key = "'Userinfo' + '_' + #p0")
    @Query("select u from Userinfo u where u.openid = :openid")
    Userinfo checkExistUserinfo(@Param("openid") String openid);


    List<Userinfo> findAll();

}
