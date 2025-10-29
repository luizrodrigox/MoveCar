package com.movecar.controller;

import com.movecar.model.Rental;
import com.movecar.service.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/alugueis")
@CrossOrigin
public class RentalController {
    private final RentalService service;

    public RentalController(RentalService service) { 
    	this.service = service; 
    }

    @GetMapping 
    public List<Rental> listar() { 
    	return service.listarTodos(); 
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Rental> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping 
    public Rental criar(@RequestBody Rental r) throws Exception { 
    	return service.criar(r); 
    }
    
    @PutMapping("/{id}") 
    public Rental atualizar(@PathVariable Long id, @RequestBody Rental r) { 
    	return service.atualizar(id, r); 
    }
    
    @DeleteMapping("/{id}") 
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}