package examen.examen.Controller;


import examen.examen.Model.Pelicula;
import examen.examen.Service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/peliculas")
@CrossOrigin(origins = "http://localhost:5173")
public class PeliculaController {
    @Autowired
    private PeliculaService service;
    @PostMapping
    public Pelicula savePelicula(@RequestBody(required = true) Pelicula pelicula) {
        return service.savePelicula(pelicula);
    }
    @GetMapping
    public List<Pelicula> getAllPeliculas() {
        return service.getAllPeliculas();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePelicula(@PathVariable Long id) {
        boolean deleted = service.deletePelicula(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Pelicula> updatePelicula(@PathVariable Long id, @RequestBody Pelicula pelicula) {
        Pelicula updatedPelicula = service.updatePelicula(id, pelicula);
        if (updatedPelicula != null) {
            return ResponseEntity.ok(updatedPelicula);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
