package com.shuo747.ilnpu.repository;




import com.shuo747.ilnpu.entity.Semester;
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
public interface SemesterDao extends CrudRepository<Semester, String> {

    @Modifying
    @Transactional
    @CacheEvict(value = "CurrentSemester", key = "'Semester'")
    @Query("update Semester s set s.current = :current where s.flag = 1")
    void updateCurrentSemester(@Param("current") String current);

    @Cacheable(unless="#result == null" ,value = "CurrentSemester", key = "'Semester'")
    @Query("select s from Semester s")
    Semester[] getCurrent();

}
