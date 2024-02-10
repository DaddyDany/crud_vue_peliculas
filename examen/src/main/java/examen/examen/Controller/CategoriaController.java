package examen.examen.Controller;


import examen.examen.Model.Categoria;
import examen.examen.Service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @PostMapping
    public Categoria saveCategoria(@RequestBody(required = true) Categoria categoria) {
        return service.saveCategoria(categoria);
    }

    @GetMapping
    public List<Categoria> getAllCategorias() {
        return service.getAllCategorias();
    }
}
