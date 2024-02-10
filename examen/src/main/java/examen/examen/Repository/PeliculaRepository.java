package examen.examen.Repository;

import examen.examen.Model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {

}
