package com.movecar.service;

import com.movecar.model.Car;
import com.movecar.repository.CarRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepository repo;

    public CarService(CarRepository repo) {
        this.repo = repo;
    }

    public List<Car> listarTodos() { 
    	return repo.findAll(); 
    }
    
    public List<Car> listarDisponiveis() { 
    	return repo.findByDisponivelTrue(); 
    }
    
    public Optional<Car> buscarPorId(Long id) { 
    	return repo.findById(id); 
    }
    
    public Car salvar(Car c) { 
    	return repo.save(c); 
    }
    
    public Car atualizar(Long id, Car c) {
    	
        Car existente = repo.findById(id).orElseThrow();
        
        existente.setMarca(c.getMarca());
        existente.setModelo(c.getModelo());
        existente.setAno(c.getAno());
        existente.setDiaria(c.getDiaria());
        existente.setDisponivel(c.isDisponivel());
        return repo.save(existente);
    }
    
    public void deletar(Long id) { repo.deleteById(id); }
}