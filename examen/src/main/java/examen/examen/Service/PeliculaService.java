package examen.examen.Service;

import examen.examen.Model.Pelicula;
import examen.examen.Repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository repo;

    public List<Pelicula> getAllPeliculas() {
        return repo.findAll();
    }

    public Pelicula savePelicula(Pelicula bean){
        return repo.save(bean);
    }

    public boolean deletePelicula(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
    public Pelicula updatePelicula(Long id, Pelicula pelicula) {
        Optional<Pelicula> existingPeliculaOptional = repo.findById(id);
        if (existingPeliculaOptional.isPresent()) {
            Pelicula existingPelicula = existingPeliculaOptional.get();
            existingPelicula.setName(pelicula.getName());
            existingPelicula.setDescription(pelicula.getDescription());
            existingPelicula.setCategoria(pelicula.getCategoria());
            return repo.save(existingPelicula);
        } else {
            return null;
        }
    }
}
