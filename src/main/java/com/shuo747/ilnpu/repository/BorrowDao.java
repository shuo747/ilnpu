package com.shuo747.ilnpu.repository;

import com.shuo747.ilnpu.entity.Borrow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowDao extends CrudRepository<Borrow, String> {


}
