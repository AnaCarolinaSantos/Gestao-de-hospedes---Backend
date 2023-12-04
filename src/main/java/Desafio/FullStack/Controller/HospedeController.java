package Desafio.FullStack.Controller;

import Desafio.FullStack.Model.Hospede;
import Desafio.FullStack.Repository.HospedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/hospedes")
public class HospedeController {

    @Autowired
    private HospedeRepository repository;

    @GetMapping("/{id}")
    public Hospede localizarHospede(@PathVariable(value = "id") Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Hóspede não encontrado no banco de dados."
                ));
    }

    @GetMapping
    public List<Hospede> localizarHospedes() {
        return repository.findAll();
    }

    @GetMapping("/no-hotel")
    public List<Hospede> localizarHospedesNoHotel() {
        return repository.hospedesNoHotel();
    }

    @GetMapping("/sem-check-in")
    public List<Hospede> localizarHospedesSemCheckIn() {
        return repository.hospedesSemCheckIn();
    }

    @PostMapping
    public Hospede cadastrar(@RequestBody Hospede hospede) {
        try {
            return repository.saveAndFlush(hospede);
        } catch(RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hospede> atualizar(
            @PathVariable(value = "id") Long id,
            @RequestBody Hospede hospede
    ) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hóspede não encontrado no banco de dados.");
        }

        hospede.setId(id);

        try {
            final Hospede atualizarHospede = repository.save(hospede);
            return ResponseEntity.ok(atualizarHospede);
        } catch(RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Hospede> excluir(@PathVariable(value = "id") Long id){
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hóspede não encontrado no banco de dados.");
        }

        try {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch(RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
