package Desafio.FullStack.Controller;

import Desafio.FullStack.Model.Hospede;
import Desafio.FullStack.Model.Reserva;
import Desafio.FullStack.Repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaRepository repository;

    @GetMapping("/{id}")
    public Reservas localizarReserva(@PathVariable(value = "id") Long id) {
        Reservas reserva = repository.buscar(id);
        if (reserva == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva não encontrada no banco de dados");
        }
        return reserva;
    }

    @GetMapping
    public List<Reservas> localizarReservas() {
        return repository.listar();
    }

    @PostMapping
    public Reserva cadastrar(@RequestBody Reserva reserva) {
        try {
            return repository.saveAndFlush(reserva);
        } catch(RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> atualizar(
            @PathVariable(value = "id") Long id,
            @RequestBody Reserva reserva
    ) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva não encontrada no banco de dados.");
        }

        try {
            final Reserva atualizarReserva = repository.saveAndFlush(reserva);
            return ResponseEntity.ok(atualizarReserva);
        } catch(RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reserva> excluir(@PathVariable(value = "id") Long id){
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva não encontrada no banco de dados.");
        }

        try {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch(RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public interface Reservas {
        Long getId();
        Long getId_hospede();
        Date getDt_inicial();
        Date getDt_final();
        Timestamp getCheck_in();
        Timestamp getCheckout();
        Boolean getEstacionamento();
        Integer getQt_diarias();
        BigDecimal getVl_diarias();
        BigDecimal getVl_taxa_estacionamento();
        BigDecimal getVl_taxa_checkout_atrasado();
        BigDecimal getVl_total();
        String getHospede();
    }
}
