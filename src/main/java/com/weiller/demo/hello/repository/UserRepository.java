package com.weiller.demo.hello.repository;

import com.weiller.demo.hello.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {


}

