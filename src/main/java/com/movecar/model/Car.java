package com.movecar.model;

import jakarta.persistence.*;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;
    private String modelo;
    private int ano;
    private double diaria;
    private boolean disponivel = true;
    
    public Car(String marca, String modelo, int ano, double diaria) {
    	this.marca = marca;
    	this.modelo = modelo;
    	this.ano = ano;
    	this.diaria = diaria;
    	this.disponivel = true;
    }
    
    public Car() {
    	
    }
    
    public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    
    public String getMarca() {
    	return marca;
    }
    
    public void setMarca(String marca) {
    	this.marca = marca;
    }
    
    public String getModelo() {
    	return modelo;
    }
    
    public void setModelo(String modelo) {
    	this.modelo = modelo;
    }
    
    public int getAno() {
    	return ano;
    }
    
    public void setAno(int ano) {
    	this.ano = ano;
    }
    
    public double getDiaria() {
    	return diaria;
    }
    
    public void setDiaria(double diaria) {
    	this.diaria = diaria;
    }
    
    public boolean isDisponivel() {
    	return disponivel;
    }
    
    public void setDisponivel(boolean disponivel) {
    	this.disponivel = disponivel;
    }
}