package com.shuo747.ilnpu.repository;

import com.shuo747.ilnpu.entity.Student;
import com.shuo747.ilnpu.entity.WxUser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Qualifier("studentDao")
public interface StudentDao extends CrudRepository<Student, Long > {



    @Cacheable(unless="#result == null",value = "Student", key = "'Student' + '_' + #p0")
    @Query("select s from Student s where s.sid = :sid")
    Student findBySid(@Param("sid") Long sid);

    @Cacheable(unless="#result == null",value = "Student", key = "'StudentName' + '_' + #p0")
    @Query("select s.name from Student s where s.sid = :sid")
    String findNameBySid(@Param("sid") Long openid);


    @Caching(evict = {
            @CacheEvict(value = "Student", key = "'Student' + '_' + #p0.sid"),
            @CacheEvict(value = "StudentALL", key = "'StudentALL'")
    })
    Student save(Student student);

    @Cacheable(unless="#result == null",value = "StudentALL", key = "'StudentALL'")
    @Override
    List<Student> findAll();
}
