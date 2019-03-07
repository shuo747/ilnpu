package com.shuo747.ilnpu.repository;

import com.shuo747.ilnpu.entity.Course;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;

@Repository
@Table(name="course")
@Qualifier("courseDao")
public interface CourseDao extends CrudRepository<Course, Long >{

    /*//源生sql插入
    @Query(value = "insert into course(cardno,name,tname) value(?1,?2,?3)",nativeQuery = true)
    @Modifying
    @Transactional
    public void insertBank_account(String name, String tname, String room);*/

    @Caching(evict = {
            @CacheEvict(value = "Course", key = "'Course' + '_' + #p0.sid"),
            @CacheEvict(value = "CourseCheck", key = "'CourseCheck' + '_' + #p0.semester + '_' + #p0.sid + '_' + #p0.name+ '_' + #p0.room+ '_' + #p0.tname+ '_' + #p0.day")
    })
    public Course save(Course course);


    @Cacheable(unless="#result == null",value = "Course", key = "'Course' + '_' + #p1 + '_' + #p0")
    @Query("select c from Course c where c.sid = :sid and c.semester = :semester order by c.day,c.section")
    Iterable<Course> findAllBySid(@Param("sid") Long sid,@Param("semester") String semester);


    @Cacheable(unless="#result == null" ,value = "CourseCheck", key = "'CourseCheck' + '_' + #p0 + '_' + #p1+ '_' + #p2+ '_' + #p3+ '_' + #p4 + '_' + #p5")
    @Query("select count(c) from Course c where c.semester = :semester and c.sid = :sid and c.name = :name and c.room = :room and c.tname = :tname and c.day = :day")
    int checkExistCourse(@Param("semester") String semester,@Param("sid") Long sid, @Param("name") String name, @Param("room") String room, @Param("tname") String tname, @Param("day") byte day);



}
