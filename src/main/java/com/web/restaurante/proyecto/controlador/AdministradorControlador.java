package com.web.restaurante.proyecto.controlador;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.web.restaurante.proyecto.Implemento.AddImplement;
import com.web.restaurante.proyecto.Implemento.GeneradorCod;
import com.web.restaurante.proyecto.Implemento.ProductoAdminImplento;
import com.web.restaurante.proyecto.Implemento.ProductoImplemento;
import com.web.restaurante.proyecto.Implemento.UsuarioAdminImplemento;
import com.web.restaurante.proyecto.Implemento.UsuarioImplemento;
import com.web.restaurante.proyecto.clases.BoletaCompra;
import com.web.restaurante.proyecto.clases.CarritoCompras;
import com.web.restaurante.proyecto.clases.productos.CategoriaProducto;
import com.web.restaurante.proyecto.clases.productos.Producto;
import com.web.restaurante.proyecto.clases.usuarios.Estado;
import com.web.restaurante.proyecto.clases.usuarios.GeneroUsuario;
import com.web.restaurante.proyecto.clases.usuarios.ReclamoUsuario;
import com.web.restaurante.proyecto.clases.usuarios.RolUsuario;
import com.web.restaurante.proyecto.clases.usuarios.TipoDocumento;
import com.web.restaurante.proyecto.clases.usuarios.Usuario;
import com.web.restaurante.proyecto.clases.usuarios.UsuarioSaldo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/administrador")
public class AdministradorControlador {
    DecimalFormat df = new DecimalFormat("#.##");
    GeneradorCod cod = new GeneradorCod();
    @Autowired
    private UsuarioAdminImplemento usuarioAdminImplemento;
    @Autowired
    private ProductoAdminImplento productoAdminImplento;

    @Autowired
    private ProductoImplemento productoImplemento;

    @Autowired
    private AddImplement addImplement;

    @Autowired
    private UsuarioImplemento usuarioImplemento;

    @GetMapping("/dashboard")
    public String main(Model model, HttpSession session) {
        ArrayList<BoletaCompra> listaCompra = usuarioAdminImplemento.compras();
        model.addAttribute("listacompra", listaCompra);
        model.addAttribute("cantCompra", listaCompra.size());
        double cantidad = 0.0;
        for (int i = 0; i < listaCompra.size(); i++) {
            BoletaCompra compra = listaCompra.get(i);
            double precio = compra.getTotal();
            cantidad += precio;
        }


        cantidad = Double.parseDouble(df.format(cantidad));
        model.addAttribute("cantidad", cantidad);

        return "administrador/dashboard";
    }

    @GetMapping("/usuarios")
    public String usuariosAdmin(Model model) {
        ArrayList<Usuario> listaUsuario = usuarioAdminImplemento.listaUsuario();
        ArrayList<Estado> listaEstado = addImplement.listaEstado();
        ArrayList<RolUsuario> RolUsuario = addImplement.listaRol();
        ArrayList<TipoDocumento> TipoDocumento = addImplement.listaTipoDoc();
        ArrayList<GeneroUsuario> gen = addImplement.listaGen();
        model.addAttribute("listaUsuario", listaUsuario);
        model.addAttribute("TipoDocumento", TipoDocumento);
        model.addAttribute("RolUsuario", RolUsuario);
        model.addAttribute("listaEstado", listaEstado);
        model.addAttribute("genero", gen);
        model.addAttribute("cantUsuarios", listaUsuario.size());
        return "/administrador/usuarios";
    }

    // PRODUCTOS


    @GetMapping("/editar/usuario/{id_usuario}")
    public String editarUsuario(@PathVariable String id_usuario, Model model) {
        Usuario user = usuarioAdminImplemento.buscarUsuario(id_usuario);
        model.addAttribute("user", user);
        return "administrador/editarUsuario";
    }

    @PostMapping("/guardar/usuario")
    public String guardarAdminUsuario(
            @RequestParam String id_usuario,
            @RequestParam int id_documento,
            @RequestParam String numero_documento,
            @RequestParam String nombre,
            @RequestParam String primer_apellido,
            @RequestParam String segundo_apellido,
            @RequestParam String telefono,
            @RequestParam String correo,
            @RequestParam String contrasena,
            @RequestParam int id_genero,
            @RequestParam int id_rol,
            @RequestParam int id_estado) {
        Usuario user = new Usuario();
        user.setId_usuario(id_usuario);
        user.setId_documento(id_documento);
        user.setNumero_documento(numero_documento);
        user.setNombre(nombre);
        user.setPrimer_apellido(primer_apellido);
        user.setSegundo_apellido(segundo_apellido);
        user.setTelefono(telefono);
        user.setCorreo(correo);
        user.setContrasena(contrasena);
        user.setId_genero(id_genero);
        user.setId_rol(id_rol);
        user.setId_estado(id_estado);
        boolean res = usuarioAdminImplemento.editarUsuario(user);
        return "redirect:/administrador/usuarios";
    }

    @PostMapping("/registrar/usuario")
    public String registrarAdminUsuario(
            @RequestParam int id_documento,
            @RequestParam String numero_documento,
            @RequestParam String nombre,
            @RequestParam String primer_apellido,
            @RequestParam String segundo_apellido,
            @RequestParam String telefono,
            @RequestParam String correo,
            @RequestParam String contrasena,
            @RequestParam int id_genero,
            @RequestParam int id_rol,
            @RequestParam int id_estado) {
        Usuario user = new Usuario();
        user.setId_usuario(cod.codUsuarios());
        user.setId_documento(id_documento);
        user.setNumero_documento(numero_documento);
        user.setNombre(nombre);
        user.setPrimer_apellido(primer_apellido);
        user.setSegundo_apellido(segundo_apellido);
        user.setTelefono(telefono);
        user.setCorreo(correo);
        user.setContrasena(contrasena);
        user.setId_genero(id_genero);
        user.setId_rol(id_rol);
        user.setId_estado(id_estado);
        UsuarioSaldo saldo = new UsuarioSaldo();
        saldo.setId_usuario(user.getId_usuario());
        saldo.setSaldo_usuario(0.0);
        boolean res = usuarioAdminImplemento.editarUsuario(user);
        if (res) {
            usuarioAdminImplemento.usuarioSaldoAdd(saldo);
        }
        return "redirect:/administrador/usuarios";
    }

    @PostMapping("/registrar/user")
    public String registrarUsuario(
            @RequestParam int id_documento,
            @RequestParam String numero_documento,
            @RequestParam String nombre,
            @RequestParam String primer_apellido,
            @RequestParam String segundo_apellido,
            @RequestParam String telefono,
            @RequestParam String correo,
            @RequestParam String contrasena,
            @RequestParam int id_genero) {
        Usuario user = new Usuario();
        user.setId_usuario(cod.codUsuarios());
        user.setId_documento(id_documento);
        user.setNumero_documento(numero_documento);
        user.setNombre(nombre);
        user.setPrimer_apellido(primer_apellido);
        user.setSegundo_apellido(segundo_apellido);
        user.setTelefono(telefono);
        user.setCorreo(correo);
        user.setContrasena(contrasena);
        user.setId_genero(id_genero);
        user.setId_rol(1);
        user.setId_estado(1);
        UsuarioSaldo saldo = new UsuarioSaldo();
        saldo.setId_usuario(user.getId_usuario());
        saldo.setSaldo_usuario(0.0);
        boolean res = usuarioAdminImplemento.editarUsuario(user);
        if (res) {
            usuarioAdminImplemento.usuarioSaldoAdd(saldo);
        }
        return "redirect:/";
    }

    @GetMapping("/productos")
    public String productosAdmin(Model model) {
        ArrayList<Producto> lista = productoAdminImplento.listaProducto();
        model.addAttribute("listaProducto", lista);
        model.addAttribute("cantPr", lista.size());
        ArrayList<Estado> listaEstado = addImplement.listaEstado();
        model.addAttribute("listaEstado", listaEstado);
        ArrayList<CategoriaProducto> CategoriaProducto = addImplement.listaCategoria();
        model.addAttribute("CategoriaProducto", CategoriaProducto);
        return "/administrador/productos";
    }
    @GetMapping("/editar/producto/{id_producto}")
    public String editarProducto(@PathVariable String id_producto, Model model) {
        Producto producto = productoImplemento.buscarProducto(id_producto);
        ArrayList<CategoriaProducto> cat =  addImplement.listaCategoria();
        model.addAttribute("cat", cat);
        model.addAttribute("producto", producto);
        return "administrador/editarProducto";
    }

    @PostMapping("/guardar/producto")
    public String guardarEditarProducto(@RequestParam String id_producto, @RequestParam String nombre_producto,
            @RequestParam String descripcion, @RequestParam int cantidad, @RequestParam float precio,
            @RequestParam int id_categoria, @RequestParam String url, @RequestParam int id_estado) {
        try {
            BigDecimal bigDecimal = new BigDecimal(precio).setScale(2, RoundingMode.HALF_UP);
            double formattedPrecio = bigDecimal.doubleValue();
            Producto producto = new Producto();
            producto.setId_producto(id_producto);
            producto.setNombre_producto(nombre_producto);
            producto.setDescripcion(descripcion);
            producto.setCantidad_stock(cantidad);
            producto.setPrecio_unidad(formattedPrecio);
            producto.setId_categoria(id_categoria);
            producto.setImg_url(url);
            producto.setId_estado(id_estado);
            productoAdminImplento.saveProducto(producto);
            return "redirect:/administrador/productos";
        } catch (Exception e) {
            // Manejo de errores
            e.printStackTrace();
            return "error"; // Redirige a una página de error
        }
    }

    @PostMapping("/registrar/producto")
    public String registrarProducto(@RequestParam String nombre_producto,
            @RequestParam String descripcion, @RequestParam int cantidad, @RequestParam float precio,
            @RequestParam int id_categoria, @RequestParam String url, @RequestParam int id_estado) {
        try {
        BigDecimal bigDecimal = new BigDecimal(precio).setScale(2, RoundingMode.HALF_UP);
        double formattedPrecio = bigDecimal.doubleValue();

            Producto producto = new Producto();
            producto.setId_producto(cod.codProductos());
            producto.setNombre_producto(nombre_producto);
            producto.setDescripcion(descripcion);
            producto.setCantidad_stock(cantidad);
            producto.setPrecio_unidad(formattedPrecio);
            producto.setId_categoria(id_categoria);
            producto.setImg_url(url);
            producto.setId_estado(id_estado);
            productoAdminImplento.saveProducto(producto);
            return "redirect:/administrador/productos";
        } catch (Exception e) {
            // Manejo de errores
            e.printStackTrace();
            return "error"; // Redirige a una página de error
        }
    }
    @GetMapping("/eliminar/producto/{id_producto}")
    public String getMethodName(@PathVariable String id_producto) {
        productoAdminImplento.eliminarProducto(id_producto);
        return  "redirect:/administrador/productos";
    }
    
    @GetMapping("/reclamos")
    private String reclamosUsuarios(Model mode) {
        ArrayList<ReclamoUsuario> reclamos = usuarioAdminImplemento.listaReclamos();
        mode.addAttribute("listaReclamos", reclamos);
        return "administrador/reclamos";
    }

    @GetMapping("/responder/reclamo/{id_reclamo}")
    public String responderReclamo(@PathVariable String id_reclamo, Model model) {
        ReclamoUsuario reclamo = usuarioAdminImplemento.reclamo(id_reclamo);
        model.addAttribute("reclamo", reclamo);
        return "/administrador/responderReclamo";
    }

    @PostMapping("/guardar/respuesta")
    public String guardarRespuesta(@RequestParam String id_reclamo,
            @RequestParam String id_usuario,
            @RequestParam String motivo,
            @RequestParam int id_estado_mensaje,
            @RequestParam String descripcion,
            @RequestParam String respuesta) {

        ReclamoUsuario reclamos = new ReclamoUsuario();
        reclamos.setId_reclamo(id_reclamo);
        reclamos.setId_usuario(id_usuario);
        reclamos.setMotivo(motivo);
        reclamos.setId_estado_mensaje(id_estado_mensaje);
        reclamos.setDescripcion(descripcion);
        reclamos.setRespuesta(respuesta);
        usuarioAdminImplemento.actualizarReclamo(reclamos);
        return "redirect:/administrador/reclamos";
    }

    @GetMapping("/compras")
    public String compras(Model model) {
        ArrayList<BoletaCompra> listaCompra = usuarioAdminImplemento.compras();
        model.addAttribute("listacompra", listaCompra);
        return "administrador/compras";
    }

    
@GetMapping("/detalle/compra/{id_compra}")
public String detalleCompra(@PathVariable("id_compra") String id_compra, Model model) {
    ArrayList<CarritoCompras> miCompra = usuarioImplemento.buscarMicompra(id_compra);
    model.addAttribute("id_compra", id_compra);
    model.addAttribute("miCompra", miCompra);
    double precioFinalcompra = 0.0;
    for (CarritoCompras sumaTT : miCompra) {
        double sumar = sumaTT.getSubtotal();
        precioFinalcompra += sumar;
    }

    model.addAttribute("id_compra", id_compra);
    model.addAttribute("precioFinalcompra", precioFinalcompra);
    return "/administrador/detallesCompras";
}

@GetMapping("/categoria/producto")
public String cat(Model model) {
    ArrayList<CategoriaProducto> categoria = addImplement.listaCategoria();
    model.addAttribute("categoria", categoria);
    return "/administrador/CategoriasProducto";
}

    @PostMapping("/agregar/categoria")
    public String postMethodName(@RequestParam String categoria) {
        
        addImplement.agregarCategoria(categoria);
        return "redirect:/administrador/categoria/producto";
    }
    @GetMapping("/eliminar/categoria/producto/{id_categoria}")
public String cat(Model model, @PathVariable("id_categoria") int id_categoria) {
    addImplement.eliminarCategoria(id_categoria);
    return "redirect:/administrador/categoria/producto";
}
}
