package tp.integrador.repositories;

import tp.integrador.entidades.EstudianteCarrera;
import tp.integrador.entidades.EstudianteCarreraPK;
import tp.integrador.repositories.entitiesRepositories.EstudianteCarreraRepositoryImpl;

import javax.persistence.EntityManager;
import java.util.List;

public interface EstudianteCarreraRepository {
    void insert(EntityManager em, EstudianteCarrera estudianteCarrera);
    EstudianteCarrera findById(EntityManager em, EstudianteCarreraPK id);
    List<EstudianteCarrera> findAll(EntityManager em);
    void update(EntityManager em, EstudianteCarrera estudianteCarrera);
    void delete(EntityManager em, EstudianteCarreraPK id);
}