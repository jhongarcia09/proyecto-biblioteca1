package com.ceiba.biblioteca.controlador;


import com.ceiba.biblioteca.entidad.Prestamo;
import com.ceiba.biblioteca.servicio.PrestamoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PrestamoControlador {
    @Autowired
    public PrestamoServicio prestamoServicio;


    @PostMapping("/prestamo")
    public ResponseEntity<?> crearprestamo(@RequestBody Prestamo prestamo){

        try {
            Prestamo prestamoCreado = prestamoServicio.crearPrestamo(prestamo);
            return new ResponseEntity(prestamoCreado, HttpStatus.OK);

        }catch (Exception e){
            return ResponseEntity.badRequest().body("{\"mensaje\": \"" + e.getMessage() + "\"}");
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo>buscarPorid(@PathVariable Integer id){
        try {
            Prestamo prestamoEncontrado=prestamoServicio.buscarPorid(id);
            return  ResponseEntity.status(HttpStatus.OK).body(prestamoEncontrado);
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }
}