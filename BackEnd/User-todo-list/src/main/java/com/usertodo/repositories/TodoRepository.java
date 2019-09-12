package com.usertodo.repositories;

import com.usertodo.models.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public interface TodoRepository extends MongoRepository<Todo, String>  {

}
