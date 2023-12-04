package Desafio.FullStack.Repository;

import Desafio.FullStack.Model.Hospede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface HospedeRepository extends JpaRepository<Hospede, Long> {

    @Query(value = 	"select h.* \n" +
            "from hospedes h\n" +
            "join reservas r on (r.id_hospede = h.id)\n" +
            "where r.check_in is not null\n" +
            "and r.checkout is null", nativeQuery = true)
    public List<Hospede> hospedesNoHotel();

    @Query(value = 	"select h.* \n" +
            "from hospedes h\n" +
            "join reservas r on (r.id_hospede = h.id)\n" +
            "where r.check_in is null", nativeQuery = true)
    public List<Hospede> hospedesSemCheckIn();
}
