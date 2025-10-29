package com.movecar.service;

import com.movecar.model.Car;
import com.movecar.model.Rental;
import com.movecar.repository.CarRepository;
import com.movecar.repository.RentalRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RentalService {
    private final RentalRepository rentalRepo;
    private final CarRepository carRepo;
    
    @Autowired
    private RentalRepository repo;

    public RentalService(RentalRepository rentalRepo, CarRepository carRepo) {
        this.rentalRepo = rentalRepo;
        this.carRepo = carRepo;
    }

    public List<Rental> listarTodos() { return rentalRepo.findAll(); }

    public Rental criar(Rental aluguel) throws Exception {
        Car carro = carRepo.findById(aluguel.getCarro().getId())
                .orElseThrow(() -> new Exception("Carro não encontrado"));
        if (!carro.isDisponivel()) throw new Exception("Carro já alugado");

        carro.setDisponivel(false);
        carRepo.save(carro);

        return rentalRepo.save(aluguel);
    }
    
    public Optional<Rental> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public Rental atualizar(Long id, Rental aluguel) {
        Rental existente = rentalRepo.findById(id).orElseThrow();

        existente.setNomeCliente(aluguel.getNomeCliente());
        existente.setDataInicio(aluguel.getDataInicio());
        existente.setDataFim(aluguel.getDataFim());
        existente.setValorTotal(aluguel.getValorTotal());

        Car carroAntigo = existente.getCarro();
        Car carroNovo = aluguel.getCarro() != null && aluguel.getCarro().getId() != null
                ? carRepo.findById(aluguel.getCarro().getId())
                    .orElseThrow(() -> new RuntimeException("Carro não encontrado"))
                : carroAntigo;

        if (!carroAntigo.getId().equals(carroNovo.getId())) {
            carroAntigo.setDisponivel(true);
            carroNovo.setDisponivel(false);
            carRepo.save(carroAntigo);
            carRepo.save(carroNovo);
        }

        existente.setCarro(carroNovo);
        return rentalRepo.save(existente);
    }

    public void deletar(Long id) {
        Rental aluguel = rentalRepo.findById(id).orElseThrow();
        Car carro = aluguel.getCarro();
        carro.setDisponivel(true);
        carRepo.save(carro);
        rentalRepo.deleteById(id);
    }
}