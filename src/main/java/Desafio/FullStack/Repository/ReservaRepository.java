package Desafio.FullStack.Repository;

import Desafio.FullStack.Controller.ReservaController.Reservas;
import Desafio.FullStack.Model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query(value = "select \n" +
            "\tr.*, \n" +
            "\th.nome as hospede\n" +
            "from reservas r\n" +
            "join hospedes h on (h.id = r.id_hospede)\n" +
            "where r.id = :id", nativeQuery = true)
    public Reservas buscar(Long id);

    @Query(value = "select \n" +
            "\tr.*, \n" +
            "\th.nome as hospede\n" +
            "from reservas r\n" +
            "join hospedes h on (h.id = r.id_hospede)", nativeQuery = true)
    public List<Reservas> listar();

}
