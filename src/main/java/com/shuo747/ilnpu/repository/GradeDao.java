package com.shuo747.ilnpu.repository;

import com.shuo747.ilnpu.entity.Grade;
import com.shuo747.ilnpu.entity.model.GradeModel;
import com.shuo747.ilnpu.entity.model.GradeSemesterModel;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeDao extends CrudRepository<Grade, Long> {

    @Caching(evict = {
        @CacheEvict(value = "GradeCheck", key = "'GradeCheck' + '_' + #p0.sid + '_' + #p0.semester+ '_' + #p0.resultstype+ '_' + #p0.cname"),
        @CacheEvict(value = "Grade", key = "'Grade' + '_' + #p0.sid + '_' + #p0.semester"),
        @CacheEvict(value = "Semester", key = "'Semester' + '_' + #p0.sid")

    })
    @Override
    Grade save(Grade g);

    @Cacheable(unless="#result == null" ,value = "Semester", key = "'Semester' + '_' + #p0")
    @Query("select distinct semester from Grade where sid = :sid")
    List<String> findALLSemester(@Param("sid") Long sid);

    //@Query("select g.cid,g.cname,g.cmode,g.school,g.credits,g.results,g.resultstype,g.examtype from Grade g where g.sid = :sid and g.semester = :semester")
    //List<GradeModel> findALLBySemester(@Param("sid") Long sid, @Param("semester") String semester);

    @Cacheable(unless="#result == null" ,value = "Grade", key = "'Grade' + '_' + #p0 + '_' + #p1")
    @Query("select g from Grade g where g.sid = :sid and g.semester = :semester order by g.cname desc")
    List<Grade> findALLBySemester(@Param("sid") Long sid, @Param("semester") String semester);



    @Query("select g.cid,g.cname,g.cmode,g.school,g.credits,g.results,g.resultstype,g.examtype from Grade g where g.sid = :sid and g.semester = :semester order by g.cname desc")
    List<Grade> findMainValueBySemester(@Param("sid") Long sid, @Param("semester") String semester);


    @Cacheable(unless="#result == null" ,value = "GradeCheck", key = "'GradeCheck' + '_' + #p0 + '_' + #p1+ '_' + #p2+ '_' + #p3")
    @Query("select count(g) from Grade g where g.sid = :sid and g.semester = :semester and g.resultstype = :resultstype and g.cname = :cname")
    int checkExistGrade(@Param("sid") Long sid, @Param("semester") String semester, @Param("resultstype") String resultstype, @Param("cname") String cname);



}
