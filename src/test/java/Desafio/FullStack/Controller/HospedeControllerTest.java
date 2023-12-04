package Desafio.FullStack.Controller;

import Desafio.FullStack.Model.Hospede;
import Desafio.FullStack.Repository.HospedeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class HospedeControllerTest {

    @Mock
    private HospedeRepository repository;

    @InjectMocks
    private HospedeController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLocalizarHospede() {
        Hospede hospede = new Hospede();
        hospede.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(hospede));

        Hospede result = controller.localizarHospede(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testLocalizarHospedeNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> controller.localizarHospede(1L));
    }

    @Test
    void testLocalizarHospedes() {
        Hospede hospede1 = new Hospede();
        Hospede hospede2 = new Hospede();
        when(repository.findAll()).thenReturn(Arrays.asList(hospede1, hospede2));

        List<Hospede> result = controller.localizarHospedes();

        assertEquals(2, result.size());
    }

    @Test
    void testLocalizarHospedesNoHotel() {
        Hospede hospede1 = new Hospede();
        Hospede hospede2 = new Hospede();
        when(repository.hospedesNoHotel()).thenReturn(Arrays.asList(hospede1, hospede2));

        List<Hospede> result = controller.localizarHospedesNoHotel();

        assertEquals(2, result.size());
    }

    @Test
    void testLocalizarHospedesSemCheckIn() {
        Hospede hospede1 = new Hospede();
        Hospede hospede2 = new Hospede();
        when(repository.hospedesSemCheckIn()).thenReturn(Arrays.asList(hospede1, hospede2));

        List<Hospede> result = controller.localizarHospedesSemCheckIn();

        assertEquals(2, result.size());
    }

    @Test
    void testCadastrar() {
        Hospede hospede = new Hospede();
        when(repository.saveAndFlush(any())).thenReturn(hospede);

        Hospede result = controller.cadastrar(hospede);

        assertEquals(hospede, result);
    }

    @Test
    void testAtualizar() {
        Long id = 1L;
        Hospede hospede = new Hospede();
        hospede.setId(id);

        when(repository.existsById(id)).thenReturn(true);
        when(repository.findById(id)).thenReturn(Optional.of(hospede));
        when(repository.save(any(Hospede.class))).thenReturn(hospede);

        ResponseEntity<Hospede> result = controller.atualizar(id, hospede);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(id, result.getBody().getId());
    }

    @Test
    void testExcluirHospede() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(true);

        doNothing().when(repository).deleteById(id);

        ResponseEntity<Hospede> response = controller.excluir(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void testExcluirHospedeNaoEncontrado() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            controller.excluir(id);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}