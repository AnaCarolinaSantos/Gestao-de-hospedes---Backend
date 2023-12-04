package Desafio.FullStack.Controller;

import Desafio.FullStack.Model.Hospede;
import Desafio.FullStack.Model.Reserva;
import Desafio.FullStack.Controller.ReservaController.Reservas;
import Desafio.FullStack.Repository.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ReservaControllerTest {

    @Mock
    private ReservaRepository repository;

    @InjectMocks
    private ReservaController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLocalizarReserva() {
        Reservas reserva = mock(Reservas.class);
        when(reserva.getId()).thenReturn(1L);

        when(repository.buscar(1L)).thenReturn(reserva);

        Reservas result = controller.localizarReserva(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testLocalizarReservaNotFound() {
        when(repository.buscar(anyLong())).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> controller.localizarReserva(1L));
    }

    @Test
    void testLocalizarReservas() {
        ReservaController.Reservas reserva1 = mock(ReservaController.Reservas.class);
        ReservaController.Reservas reserva2 = mock(ReservaController.Reservas.class);
        List<ReservaController.Reservas> reservas = Arrays.asList(reserva1, reserva2);

        when(repository.listar()).thenReturn(reservas);

        List<ReservaController.Reservas> result = controller.localizarReservas();

        assertEquals(2, result.size());
    }

    @Test
    void testCadastrar() {
        Reserva reserva = new Reserva();
        when(repository.saveAndFlush(any())).thenReturn(reserva);

        Reserva result = controller.cadastrar(reserva);

        assertEquals(reserva, result);
    }

    @Test
    void testAtualizar() {
        Long id = 1L;
        Reserva reserva = new Reserva();
        reserva.setId(id);

        when(repository.existsById(id)).thenReturn(true);
        when(repository.findById(id)).thenReturn(Optional.of(reserva));
        when(repository.saveAndFlush(any(Reserva.class))).thenReturn(reserva);

        ResponseEntity<Reserva> result = controller.atualizar(id, reserva);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(id, result.getBody().getId());
    }

    @Test
    void testExcluir() {
        Long id = 1L;

        when(repository.buscar(id)).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> controller.excluir(id));
    }

}
