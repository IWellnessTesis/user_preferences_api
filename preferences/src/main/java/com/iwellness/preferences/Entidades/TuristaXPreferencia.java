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
@Table(name = "turista_x_preferencia")
@AllArgsConstructor
@NoArgsConstructor
public class TuristaXPreferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turista_x_preferencia")
    private Long _idTuristaXPreferencia;

    @Column(name = "id_usuario",nullable = false)
    private Long _idUsuario;  // Se relaciona con el microservicio de usuarios

    @ManyToOne
    @JoinColumn(name = "id_preferencia", nullable = false)
    private Preferencias preferencia;
}
