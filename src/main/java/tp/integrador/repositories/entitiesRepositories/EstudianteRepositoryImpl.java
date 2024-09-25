package tp.integrador.repositories.entitiesRepositories;

import tp.integrador.dto.EstudianteDto;
import tp.integrador.entidades.Estudiante;
import tp.integrador.repositories.EstudianteRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.*;
import java.util.List;


public class EstudianteRepositoryImpl implements EstudianteRepository {

    @Override
    public void insert(EntityManager em, Estudiante estudiante) {
        em.persist(estudiante);  // Insertar el objeto estudiante
        em.getTransaction().commit();
    }

    @Override
    public Estudiante findById(EntityManager em, Long id) {
        return em.find(Estudiante.class, id);
    }

    @Override
    public List<Estudiante> findAll(EntityManager em) {
        Query query = em.createQuery("SELECT e FROM Estudiante e");
        return query.getResultList();
    }

    @Override
    public void update(EntityManager em, Estudiante estudiante) {
        em.merge(estudiante);  // Actualizar el objeto estudiante
        em.getTransaction().commit();
    }

    @Override
    public void delete(EntityManager em, Long id) {
        Estudiante estudiante = em.find(Estudiante.class, id);
        if (estudiante != null) {
            em.remove(estudiante);  // Eliminar el objeto estudiante
        }
        em.getTransaction().commit();
    }

    public static EstudianteRepositoryImpl getInstance() {
        return new EstudianteRepositoryImpl();
    }

    public static List<EstudianteDto> obtenerEstudiantesPorApellidoAsc(EntityManager em) {
        String jpql = "SELECT new tp.integrador.dto.EstudianteDto(e.nombre, e.apellido, e.edad, e.genero, e.dni, e.ciudad, e.lu) " +
                "FROM Estudiante e " +
                "ORDER BY e.apellido";
        TypedQuery<EstudianteDto> query = em.createQuery(jpql, EstudianteDto.class);
        return query.getResultList();
    }

    public static EstudianteDto obtenerEstudiantePorLU(EntityManager em, long lu) {
        String jpql = "SELECT new tp.integrador.dto.EstudianteDto(e.nombre, e.apellido, e.edad, e.genero, e.dni, e.ciudad, e.lu) " +
                "FROM Estudiante" +
                " e WHERE e.lu = :lu";
        TypedQuery<EstudianteDto> query = em.createQuery(jpql, EstudianteDto.class);
        query.setParameter("lu", lu);
        return query.getSingleResult();
    }

    public static List<EstudianteDto> obtenerEstudiantesPorGenero(EntityManager em, String genero) {
        String jpql = "SELECT new tp.integrador.dto.EstudianteDto(e.nombre, e.apellido, e.edad, e.genero, e.dni, e.ciudad, e.lu) " +
                "FROM Estudiante" +
                " e WHERE e.genero = :genero";
        TypedQuery<EstudianteDto> query = em.createQuery(jpql, EstudianteDto.class);
        query.setParameter("genero", genero);
        return query.getResultList();
    }

    public static List<EstudianteDto> obtenerEstudiantesPorCarreraYCiudad(EntityManager em, String nombre, String ciudad) {
        String jpql = "SELECT new tp.integrador.dto.EstudianteDto(e.nombre, e.apellido, e.edad, e.genero, e.dni, e.ciudad, e.lu)" +
                "FROM EstudianteCarrera ec " +
                "JOIN ec.estudiante e " +
                "JOIN ec.carrera c " +
                "WHERE c.nombre = :nombre AND e.ciudad = :ciudad";
        TypedQuery<EstudianteDto> query = em.createQuery(jpql, EstudianteDto.class);
        query.setParameter("nombre", nombre);
        query.setParameter("ciudad", ciudad);
        return query.getResultList();
    }
}
