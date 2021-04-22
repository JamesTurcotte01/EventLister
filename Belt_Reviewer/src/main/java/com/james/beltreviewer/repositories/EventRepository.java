package com.james.beltreviewer.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.james.beltreviewer.models.Event;




@Repository
public interface EventRepository extends CrudRepository <Event, Long> {

}