package Desafio.FullStack.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="reservas")
@Data
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long id_hospede;

    private Date dt_inicial;

    private Date dt_final;

    @Temporal(TemporalType.TIMESTAMP)
    private Date check_in;

    @Temporal(TemporalType.TIMESTAMP)
    private Date checkout;

    private Boolean estacionamento;

    private Integer qt_diarias;

    @Column(precision=6, scale=2)
    private BigDecimal vl_diarias;

    @Column(precision=6, scale=2)
    private BigDecimal vl_taxa_estacionamento;

    @Column(precision=6, scale=2)
    private BigDecimal vl_taxa_checkout_atrasado;

    @Column(precision=6, scale=2)
    private BigDecimal vl_total;

}
