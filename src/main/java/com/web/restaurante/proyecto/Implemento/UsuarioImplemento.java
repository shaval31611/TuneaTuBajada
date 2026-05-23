package com.web.restaurante.proyecto.Implemento;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.restaurante.proyecto.clases.BoletaCompra;
import com.web.restaurante.proyecto.clases.CarritoCompras;
import com.web.restaurante.proyecto.clases.usuarios.Usuario;
import com.web.restaurante.proyecto.clases.usuarios.UsuarioSaldo;
import com.web.restaurante.proyecto.repositorio.BoletaCompraRepository;
import com.web.restaurante.proyecto.repositorio.CarritoCompraRepository;
import com.web.restaurante.proyecto.repositorio.CartRepository;
import com.web.restaurante.proyecto.repositorio.UsuarioRepositorio;
import com.web.restaurante.proyecto.repositorio.UsuarioSaldoRepository;
import com.web.restaurante.proyecto.servicio.UsuarioServicio;

@Service
public class UsuarioImplemento implements UsuarioServicio{

@Autowired
private UsuarioRepositorio usuarioRepositorio;

@Override
public Usuario validacionUsuario(String numero_documento, String contrasena) {
    Usuario user = usuarioRepositorio.findUserByCodigo(numero_documento, contrasena);
    try {
        if (!user.getId_usuario().isEmpty() ) {
            return user;
        } else {
            return user;
        }
    } catch (Exception e) {
        return user;
    }

}
@Autowired
private UsuarioSaldoRepository usuarioSaldoRepository;
@Override
public UsuarioSaldo mySaldo(String id_usuario) {
    Optional<UsuarioSaldo> saldo = usuarioSaldoRepository.findById(id_usuario);
    if(saldo.isPresent()){
        return saldo.get();
    }else{
        return null;
    }
    
}



@Autowired
private BoletaCompraRepository boletaCompraRepository;
@Override
public ArrayList<BoletaCompra> misCompras(String id_usuario) {
    ArrayList<BoletaCompra> misCompras = (ArrayList<BoletaCompra>) boletaCompraRepository.misCompras(id_usuario);
    return misCompras;
}

@Autowired
private CarritoCompraRepository carritoCompraRepository;

@Override
public ArrayList<CarritoCompras> buscarMicompra(String id_compra) {
    ArrayList<CarritoCompras> miCompras = carritoCompraRepository.misCompras(id_compra);
    return miCompras;
}

@Autowired
private CartRepository cartRepository;
@Override
public boolean comprarProductos( BoletaCompra boleta) {
    boletaCompraRepository.save(boleta);
    return true;
}
@Override
public boolean actualizarSaldo(UsuarioSaldo usuarioSaldo) {
    boolean res= true;
 try {
    usuarioSaldoRepository.save(usuarioSaldo);
    return  res;
 } catch (Exception e) {
    return  false;
 }
   

}


@Override
public boolean agregarCompraCarrito(String Id_compra, String Id_producto, String Nombre_producto, String Descripcion,
        int Cantidad_carrito, double Precio_carrito, double Subtotal, String Img) {
            //id_compra, id_producto, nombre_producto, descripcion, cantidad_carrito, precio_carrito, subtotal, img
            cartRepository.insertCarrito(Id_compra, Id_producto, Nombre_producto, Descripcion, Cantidad_carrito, Precio_carrito, Subtotal, Img);          
            return true;
}
}