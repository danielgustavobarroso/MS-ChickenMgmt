package com.retooling.chicken.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.retooling.chicken.entity.Chicken;

public interface ChickenRepository extends MongoRepository<Chicken, String>{

	//Necesita el atributo chickenId en el modelo para funcionar
	Chicken findByChickenId(String id);

}
