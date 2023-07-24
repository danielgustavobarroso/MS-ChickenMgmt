package com.retooling.chicken.service;

import java.util.List;
import com.retooling.chicken.entity.Chicken;
import com.retooling.chicken.exception.ChickenNotFoundException;

public interface ChickenService {
	
	public List<Chicken> getAllChickens();

	public Chicken getChickenById(String id) throws ChickenNotFoundException;
	
	public Chicken saveChicken(Chicken chicken);
	
	public void deleteChicken(Chicken chicken);
	
	public List<Chicken> getChickensByFarmId(String idFarm) throws ChickenNotFoundException;
	
}
