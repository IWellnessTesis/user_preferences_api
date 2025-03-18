package com.iwellness.preferences.Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "servicio_x_preferencia")
@AllArgsConstructor
@NoArgsConstructor
public class ServicioXPreferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio_x_preferencia")
    private Long _idServicioXPreferencia;

    @Column(name = "id_servicio", nullable = false)
    private Long idServicio;  // Se relaciona con el microservicio de servicios

    @ManyToOne
    @JoinColumn(name = "id_preferencia", referencedColumnName = "id_preferencia", nullable = false)
    private Preferencias preferencia;
}
