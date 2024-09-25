package tp.integrador.repositories;

import tp.integrador.entidades.Carrera;
import tp.integrador.repositories.entitiesRepositories.CarreraRepositoryImpl;

import javax.persistence.EntityManager;
import java.util.List;

public interface CarreraRepository {
    void insert(EntityManager em, Carrera carrera);
    Carrera findById(EntityManager em, Long id);
    List<Carrera> findAll(EntityManager em);
    void update(EntityManager em, Carrera carrera);
    void delete(EntityManager em, Long id);
}
