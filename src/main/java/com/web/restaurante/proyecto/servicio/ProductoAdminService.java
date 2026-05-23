package com.web.restaurante.proyecto.servicio;

import java.util.ArrayList;

import com.web.restaurante.proyecto.clases.productos.Producto;

public interface ProductoAdminService {
    ArrayList<Producto> listaProducto();
    boolean saveProducto (Producto producto);
    boolean eliminarProducto (String id_producto);
}
