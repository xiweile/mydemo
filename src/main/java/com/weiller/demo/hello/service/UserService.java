package com.weiller.demo.hello.service;

import com.weiller.demo.hello.entity.User;
import com.weiller.demo.hello.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Flux<User> findAll(){
        return Flux.fromIterable(userRepository.findAll());
    }

    public Flux<User> getByIds(Flux<Integer> ids){
        return ids.flatMap(id -> Mono.justOrEmpty(userRepository.findById(id)));
    }

    public Mono<User> getById(Integer id){
        return Mono.justOrEmpty(userRepository.findById(id));
    }

    public Mono<User> createOrUpdate( User  user){
        User save = userRepository.save(user);
        return Mono.just(save);
    }

    public Mono<User> delete(Integer id){
        User user = new User();
        user.setId(id);
        userRepository.delete( user);
        return Mono.justOrEmpty(user);
    }
}
