package com.movecar.controller;

import com.movecar.model.Car;
import com.movecar.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/carros")
@CrossOrigin
public class CarController {
    private final CarService service;

    public CarController(CarService service) { 
    	this.service = service; 
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Car> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping 
    public List<Car> listarTodos() { 
    	return service.listarTodos(); 
    }
    
    @GetMapping("/disponiveis") 
    public List<Car> listarDisponiveis() { 
    	return service.listarDisponiveis(); 
    }
    
    @PostMapping 
    public Car criar(@RequestBody Car c) { 
    	return service.salvar(c); 
    }
    
    @PutMapping("/{id}") 
    public Car atualizar(@PathVariable Long id, @RequestBody Car c) { 
    	return service.atualizar(id, c); 
    }
    
    @DeleteMapping("/{id}") 
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}