package com.iwellness.preferences.Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "preferencias")
@AllArgsConstructor
@NoArgsConstructor
public class Preferencias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_preferencia")
    private Long _idPreferencias;
    
    @Column(nullable = false, unique = true)
    private String nombre;

    @Column
    private String imagen;
}
