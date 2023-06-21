package com.ceiba.biblioteca.servicio;

import com.ceiba.biblioteca.entidad.Prestamo;
import com.ceiba.biblioteca.repositorio.PrestamoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PrestamoServicio implements PrestamoServicioInterface {
    @Autowired
    public PrestamoRepositorio prestamoRepositorio;

    @Override
    public Prestamo crearPrestamo(Prestamo prestamo) throws Exception {
        try {
            if (prestamo.getTipoUsuario() == 3 && prestamoRepositorio.existsByIdentificacionUsuario(prestamo.getIdentificacionUsuario())) {
                throw new Exception("El usuario con identificación  ya tiene un libro prestado por lo cual no se le puede realizar otro préstamo " + prestamo.getTipoUsuario());
            }
            LocalDate fechaMaximaDevolucion = calcularfechaMaximadevolucion(prestamo);
            prestamo.setFechaMaximadevolucion(fechaMaximaDevolucion);
            return prestamoRepositorio.save(prestamo);
        } catch (Exception e) {
            throw new Exception(e.getMessage());

        }

    }

    @Override
    public LocalDate calcularfechaMaximadevolucion(Prestamo prestamo) {
        Integer tipoUsuario = prestamo.getTipoUsuario();
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaMaximadevolucion;
        if (tipoUsuario == 1) {
            fechaMaximadevolucion = fechaActual.plusDays(10);
        } else if (tipoUsuario == 2) {
            fechaMaximadevolucion = fechaActual.plusDays(8);
        } else if (tipoUsuario == 3) {
            fechaMaximadevolucion = fechaActual.plusDays(7);
        } else {
            throw new IllegalArgumentException("Tipo de usuario no permitido en la biblioteca");
        }
        return fechaMaximadevolucion;
    }

    public Prestamo buscarPorid(Integer id) throws Exception {
        try {
            Optional<Prestamo> prestamoOptional = prestamoRepositorio.findById(id);
            if (prestamoOptional.isPresent()) {
                return prestamoOptional.get();
            } else {
                throw new Exception("Tipo de usuario no permitido en la biblioteca");
            }

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
