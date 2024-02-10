package examen.examen.Service;

import examen.examen.Model.Categoria;
import examen.examen.Repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Categoria saveCategoria(Categoria bean){
        return repo.save(bean);
    }

    public List<Categoria> getAllCategorias() {
        return repo.findAll();
    }
}
