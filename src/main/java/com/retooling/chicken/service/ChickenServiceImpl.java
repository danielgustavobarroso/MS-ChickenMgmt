package com.retooling.chicken.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.retooling.chicken.entity.Chicken;
import com.retooling.chicken.exception.ChickenNotFoundException;
import com.retooling.chicken.repository.ChickenRepository;

@Service
public class ChickenServiceImpl implements ChickenService {

	private static final Logger logger = LoggerFactory.getLogger(ChickenServiceImpl.class);
	
	@Autowired
	ChickenRepository repository;

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public List<Chicken> getAllChickens() {
		logger.info("Service - Calling method getAllChickens...");
		return repository.findAll(Sort.by(Sort.Direction.ASC, "creationDate"));			
	}

	@Override
	public Chicken getChickenById(String id) throws ChickenNotFoundException{
		logger.info("Service - Calling method getChickenById...");
		Chicken chicken = repository.findByChickenId(id);
		if (chicken != null) {
			return chicken;
		} else {
			throw new ChickenNotFoundException("Chicken not found with id=" + id); 
		}	
	}
	
	public Chicken saveChicken(Chicken chicken) {
		logger.info("Service - Calling method saveChicken...");
		return repository.save(chicken);
	}
	
	@Override
	public void deleteChicken(Chicken chicken) {
		logger.info("Service - Calling method deleteChicken...");
		repository.delete(chicken);
	}
	
	@Override
	public List<Chicken> getChickensByFarmId(String idFarm) throws ChickenNotFoundException {
		logger.info("Service - Calling method getChickensByFarmId...");
		Query query = new Query();
		query.addCriteria(Criteria.where("farmId").is(idFarm)).with(Sort.by(Sort.Direction.ASC, "creationDate"));
		List<Chicken> chickens = mongoTemplate.find(query, Chicken.class);
		if (!chickens.isEmpty()) {
			return chickens;	
		} else {
			throw new ChickenNotFoundException("Chickens not found");
		}
	}
	
}
