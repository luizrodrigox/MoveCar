package com.movecar.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nomeCliente;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private double valorTotal;

    @ManyToOne
    @JoinColumn(name = "carro_id")
    private Car carro;
    
	public Rental(Long id, LocalDate dataInicio, LocalDate dataFim, double valorTotal) {
	    	this.id = id;
	    	this.dataInicio = dataInicio;
	    	this.dataFim = dataFim;
	    	this.valorTotal = valorTotal;
	}
    
    public Rental() {
    	
    }
    
    public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    
    public String getNomeCliente(){
    	return nomeCliente;
    }
    
    public void setNomeCliente(String nomeCliente) {
    	this.nomeCliente = nomeCliente;
    }
    
    public LocalDate getDataInicio() {
    	return dataInicio;
    }
    
    public void setDataInicio(LocalDate dataInicio) {
    	this.dataInicio = dataInicio;
    }
    
    public LocalDate getDataFim() {
    	return dataFim;
    }
    
    public void setDataFim(LocalDate dataFim) {
    	this.dataFim = dataFim;
    }
    
    public double getValorTotal() {
    	return valorTotal;
    }
    
    public void setValorTotal(double valorTotal) {
    	this.valorTotal = valorTotal;
    }
    
    public Car getCarro(){
    	return carro;
    }
    
    public void setCarro(Car carro) {
        this.carro = carro;
    }
}