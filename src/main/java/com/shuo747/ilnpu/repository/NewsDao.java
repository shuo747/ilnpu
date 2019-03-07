package com.shuo747.ilnpu.repository;

import com.shuo747.ilnpu.entity.News;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("newsDao")
public interface NewsDao extends CrudRepository<News, String > {



    /*//@Cacheable(value = "Student", key = "#root.targetClass + '_' + #p0 + '_' + #p1")
    @Query("select s from Student s where s.sid = :sid")
    Student findBySid(@Param("sid") Long openid);

    //@Cacheable(value = "sname", key = "#root.targetClass + '_' + #p0 + '_' + #p1")
    @Query("select s.name from Student s where s.sid = :sid")
    String findNameBySid(@Param("sid") Long openid);

    //@CachePut(value = "Student", key = "#root.targetClass + '_' + #p0 + '_' + #p1")
    public Student save(Student student);*/

    @Caching(evict = {
            @CacheEvict(value = "News", key = "'News'"),
            @CacheEvict(value = "NewsCheck", key = "'NewsCheck' + '_' + #p0.title")
    }
    )
    public News save(News news);

    @Cacheable(unless="#result == null" ,value = "News", key = "'News'")
    @Query("select n from News n order by n.time desc")
    List<News> findAllOrderByTime();


    @Cacheable(unless="#result == null" ,value = "NewsCheck", key = "'NewsCheck' + '_' + #p0")
    @Query("select count(n) from News n where n.title = :title")
    int checkExistNews(@Param("title") String title);




}
