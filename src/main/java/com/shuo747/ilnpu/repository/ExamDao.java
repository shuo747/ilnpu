package com.shuo747.ilnpu.repository;


import com.shuo747.ilnpu.entity.Exam;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamDao extends CrudRepository<Exam, Long> {


    @Cacheable(unless="#result == null" ,value = "Exam", key = "'Exam'+ '_' + #p1+ '_' + #p0")
    @Query("select e from Exam e where e.examclass = :sclass and e.semester = :semester order by e.examdate")
    List<Exam> findOwnBySClss(@Param("sclass") String sclass,@Param("semester") String semester);


}
