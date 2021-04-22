package com.james.beltreviewer.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.james.beltreviewer.models.UserEvent;


@Repository
public interface UserEventRepository extends CrudRepository<UserEvent, Long> {

}