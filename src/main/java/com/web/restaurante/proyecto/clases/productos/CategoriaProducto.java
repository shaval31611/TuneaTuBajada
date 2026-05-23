package com.web.restaurante.proyecto.clases.productos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="categoria_producto")
public class CategoriaProducto {
    @Id
    private int id_categoria;
    private String nombre_categoria;
}
