
package com.movecar.service;

import com.movecar.model.Car;
import com.movecar.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository repo;

    @InjectMocks
    private CarService service;

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car("Toyota", "Corolla", 2022, 150.0);
        car.setId(1L);
    }

    @Test
    void deveListarTodosOsCarros() {
        when(repo.findAll()).thenReturn(List.of(car));

        List<Car> resultado = service.listarTodos();

        assertEquals(1, resultado.size());
        assertEquals("Toyota", resultado.get(0).getMarca());
        verify(repo).findAll();
    }

    @Test
    void deveListarCarrosDisponiveis() {
        when(repo.findByDisponivelTrue()).thenReturn(List.of(car));

        List<Car> resultado = service.listarDisponiveis();

        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).isDisponivel());
        verify(repo).findByDisponivelTrue();
    }

    @Test
    void deveBuscarCarroPorId() {
        when(repo.findById(1L)).thenReturn(Optional.of(car));

        Optional<Car> resultado = service.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        verify(repo).findById(1L);
    }

    @Test
    void deveRetornarVazioQuandoCarroNaoExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        Optional<Car> resultado = service.buscarPorId(99L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveSalvarCarro() {
        when(repo.save(car)).thenReturn(car);

        Car resultado = service.salvar(car);

        assertSame(car, resultado);
        verify(repo).save(car);
    }

    @Test
    void deveAtualizarCarroExistente() {
        Car dadosAtualizados =
                new Car("Honda", "Civic", 2024, 220.0);
        dadosAtualizados.setDisponivel(false);

        when(repo.findById(1L)).thenReturn(Optional.of(car));
        when(repo.save(any(Car.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Car resultado = service.atualizar(1L, dadosAtualizados);

        assertEquals("Honda", resultado.getMarca());
        assertEquals("Civic", resultado.getModelo());
        assertEquals(2024, resultado.getAno());
        assertEquals(220.0, resultado.getDiaria());
        assertFalse(resultado.isDisponivel());

        verify(repo).findById(1L);
        verify(repo).save(car);
    }

    @Test
    void deveLancarExcecaoAoAtualizarCarroInexistente() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                java.util.NoSuchElementException.class,
                () -> service.atualizar(99L, car)
        );

        verify(repo, never()).save(any(Car.class));
    }

    @Test
    void deveDeletarCarroPorId() {
        doNothing().when(repo).deleteById(1L);

        service.deletar(1L);

        verify(repo).deleteById(1L);
    }
}
