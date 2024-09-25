package tp.integrador.repositories;

import tp.integrador.entidades.Estudiante;
import tp.integrador.repositories.entitiesRepositories.EstudianteRepositoryImpl;

import javax.persistence.EntityManager;
import java.util.List;

public interface EstudianteRepository {
    void insert(EntityManager em, Estudiante estudiante);
    Estudiante findById(EntityManager em, Long id);
    List<Estudiante> findAll(EntityManager em);
    void update(EntityManager em, Estudiante estudiante);
    void delete(EntityManager em, Long id);
}