package com.james.beltreviewer.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.james.beltreviewer.models.Message;



@Repository
public interface MessageRepository extends CrudRepository <Message, Long>{

}