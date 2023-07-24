package com.retooling.chicken.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.retooling.chicken.entity.Chicken;
import com.retooling.chicken.exception.ChickenNotFoundException;
import com.retooling.chicken.exception.ChickenValidationErrorException;
import com.retooling.chicken.service.ChickenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class ChickenController {
	
	private static final Logger logger = LoggerFactory.getLogger(ChickenController.class);
	
	@Autowired
	ChickenService service;
	
	//Obtener todos los pollos
	@GetMapping("chickens")
	public ResponseEntity<List<Chicken>> getAllChickens() {
		logger.info("Controller - Calling method getAllChickens...");
		return new ResponseEntity<>(service.getAllChickens(), HttpStatus.OK);
	}

	//Obtener un pollo por id
	@GetMapping("chickens/{id}")
	public ResponseEntity<Chicken> getChickenById(@PathVariable("id") String id) throws ChickenNotFoundException{
		logger.info("Controller - Calling method getChickenById con id=" + id);
		return new ResponseEntity<>(service.getChickenById(id), HttpStatus.OK);				
	}
	
	//Guardar un pollo
	@PostMapping("chickens")
	public ResponseEntity<Chicken> createChicken(@Valid @RequestBody Chicken chicken, BindingResult bindingResult)
			throws ChickenValidationErrorException {
		logger.info("Controller - Calling method createChicken...");
		if (bindingResult.hasErrors()) {
			String message = new String();
			for(FieldError error : bindingResult.getFieldErrors()) {
				if (message.isEmpty()) {
					message = message + error.getField() + " : " + error.getDefaultMessage();
				} else {
					message = message + ", " + error.getField() + " : " + error.getDefaultMessage();
				}
			}
			throw new ChickenValidationErrorException(message);
		}
		return new ResponseEntity<>(service.saveChicken(chicken), HttpStatus.CREATED);				
	}

	//Actualizar datos de un pollo
	@PutMapping("chickens")
	public ResponseEntity<Chicken> updateChicken(@Valid @RequestBody Chicken chickenUpdated, BindingResult bindingResult)
			throws ChickenNotFoundException, ChickenValidationErrorException {
		logger.info("Controller - Calling method updateChicken...");
		if (bindingResult.hasErrors()) {
			String message = new String();
			for(FieldError error : bindingResult.getFieldErrors()) {
				if (message.isEmpty()) {
					message = message + error.getField() + " : " + error.getDefaultMessage();
				} else {
					message = message + ", " + error.getField() + " : " + error.getDefaultMessage();
				}
			}
			throw new ChickenValidationErrorException(message);
		}
		Chicken chicken = service.getChickenById(chickenUpdated.getChickenId());
		BeanUtils.copyProperties(chickenUpdated, chicken);
		return new ResponseEntity<>(service.saveChicken(chicken), HttpStatus.OK);
	}

	//Borrar un pollo
	@DeleteMapping("chickens/{id}")
	public ResponseEntity<Chicken> deleteChicken(@PathVariable("id") String id) throws ChickenNotFoundException{
		logger.info("Controller - Calling method deleteChicken...");
		Chicken chicken = service.getChickenById(id);
		service.deleteChicken(chicken);
		return new ResponseEntity<>(chicken, HttpStatus.OK);
	}

	//Obtener pollos por id de granja
	@GetMapping("chickens/farms/{id}")
	public ResponseEntity<List<Chicken>> getChickensByFarmId(@PathVariable("id") String id) throws ChickenNotFoundException {
		logger.info("Controller - Calling method getChickensByFarmId..."); 
		return new ResponseEntity<>(service.getChickensByFarmId(id), HttpStatus.OK);				
	}
	
}