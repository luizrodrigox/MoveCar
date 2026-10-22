
package com.movecar.service;

import com.movecar.model.Car;
import com.movecar.model.Rental;
import com.movecar.repository.CarRepository;
import com.movecar.repository.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentalServiceTest {

    @Mock
    private RentalRepository rentalRepo;

    @Mock
    private CarRepository carRepo;

    @InjectMocks
    private RentalService service;

    private Car car;
    private Rental rental;

    
    @BeforeEach
    void setUp() {
        car = new Car("Toyota", "Corolla", 2022, 150.0);
        car.setId(1L);

        rental = new Rental(
                1L,
                LocalDate.of(2026, 10, 20),
                LocalDate.of(2026, 10, 23),
                450.0
        );
        rental.setId(1L);
        rental.setNomeCliente("João");
        rental.setCarro(car);
    }

    @Test
    void deveListarTodasAsLocacoes() {
        when(rentalRepo.findAll()).thenReturn(List.of(rental));

        List<Rental> resultado = service.listarTodos();

        assertEquals(1, resultado.size());
        assertEquals("João", resultado.get(0).getNomeCliente());
        verify(rentalRepo).findAll();
    }

    @Test
    void deveBuscarLocacaoPorId() {
        when(rentalRepo.findById(1L)).thenReturn(Optional.of(rental));

        Optional<Rental> resultado = service.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        verify(rentalRepo).findById(1L);
    }

    @Test
    void deveRetornarVazioQuandoLocacaoNaoExiste() {
        when(rentalRepo.findById(99L)).thenReturn(Optional.empty());

        Optional<Rental> resultado = service.buscarPorId(99L);

        assertTrue(resultado.isEmpty());
        verify(rentalRepo).findById(99L);
    }

    @Test
    void deveCriarLocacaoEIndisponibilizarCarro() throws Exception {
        when(carRepo.findById(1L)).thenReturn(Optional.of(car));
        when(rentalRepo.save(rental)).thenReturn(rental);

        Rental resultado = service.criar(rental);

        assertSame(rental, resultado);
        assertFalse(car.isDisponivel());
        verify(carRepo).save(car);
        verify(rentalRepo).save(rental);
    }

    @Test
    void naoDeveCriarLocacaoQuandoCarroNaoExiste() {
        when(carRepo.findById(99L)).thenReturn(Optional.empty());

        Car carroInexistente = new Car();
        carroInexistente.setId(99L);
        rental.setCarro(carroInexistente);

        Exception erro = assertThrows(
                Exception.class,
                () -> service.criar(rental)
        );

        assertEquals("Carro não encontrado", erro.getMessage());
        verify(rentalRepo, never()).save(any(Rental.class));
    }

    @Test
    void naoDeveCriarLocacaoQuandoCarroEstaIndisponivel() {
        car.setDisponivel(false);
        when(carRepo.findById(1L)).thenReturn(Optional.of(car));

        Exception erro = assertThrows(
                Exception.class,
                () -> service.criar(rental)
        );

        assertEquals("Carro já alugado", erro.getMessage());
        verify(rentalRepo, never()).save(any(Rental.class));
    }

    
    @Test
    void deveAtualizarDadosDaLocacao() {
        Rental dadosAtualizados = new Rental(
                1L,
                LocalDate.of(2026, 11, 1),
                LocalDate.of(2026, 11, 4),
                600.0
        );
        dadosAtualizados.setNomeCliente("Maria");
        dadosAtualizados.setCarro(car);

        when(rentalRepo.findById(1L)).thenReturn(Optional.of(rental));
        when(carRepo.findById(1L)).thenReturn(Optional.of(car));

        when(rentalRepo.save(any(Rental.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Rental resultado = service.atualizar(1L, dadosAtualizados);

        assertEquals("Maria", resultado.getNomeCliente());
        assertEquals(LocalDate.of(2026, 11, 1), resultado.getDataInicio());
        assertEquals(LocalDate.of(2026, 11, 4), resultado.getDataFim());
        assertEquals(600.0, resultado.getValorTotal());
        assertEquals(car, resultado.getCarro());

        verify(rentalRepo).save(rental);
        verify(carRepo, never()).save(any(Car.class));
    }


    @Test
    void deveExcluirLocacaoELiberarCarro() {
        when(rentalRepo.findById(1L)).thenReturn(Optional.of(rental));

        service.deletar(1L);

        assertTrue(car.isDisponivel());
        verify(carRepo).save(car);
        verify(rentalRepo).deleteById(1L);
    }
}
