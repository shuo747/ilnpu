package com.shuo747.ilnpu.repository;


import com.shuo747.ilnpu.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * User Repository 接口.
 * 
 * @since 1.0.0 2017年7月16日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
public interface UserRepository extends CrudRepository<User, Long> {

}
