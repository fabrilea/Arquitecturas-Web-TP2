package tp.integrador.repositories.entitiesRepositories;

import tp.integrador.dto.EstudianteDto;
import tp.integrador.repositories.Repository;
import tp.integrador.entidades.Estudiante;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.*;
import java.util.List;


public class EstudianteRepository implements Repository<EstudianteRepository> {

    public EstudianteRepository() {
    }

    @Override
    public Object select(EntityManager em, long id) {
        String jpql = "SELECT e FROM Estudiante e WHERE e.id = :id";
        TypedQuery<Estudiante> query = em.createQuery(jpql, Estudiante.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public void insert(EntityManager em, Object estudiante) throws SQLException {
        if (estudiante instanceof Estudiante) {// Inicia la transacción
            em.persist(estudiante);       // Inserta la entidad en la base de datos
        }
    }

    @Override
    public void update(EntityManager em, long id, Object estudiante) {
        if (estudiante instanceof Estudiante) {

            // Prepara la consulta JPQL para actualizar los campos del estudiante
            String jpql = "UPDATE Estudiante e SET e.nombre = :nombre, e.apellido = :apellido, e.edad = :edad, " +
                    "e.genero = :genero, e.dni = :dni, e.ciudad = :ciudad, e.graduado = :estaGraduado " +
                    "WHERE e.id = :id";
            Estudiante estudianteActualizado = (Estudiante) estudiante;

            Query query = em.createQuery(jpql);

            // Extrae los valores del objeto estudianteActualizado y los asigna a los parámetros de la consulta
            query.setParameter("nombre", estudianteActualizado.getNombre());
            query.setParameter("apellido", estudianteActualizado.getApellido());
            query.setParameter("edad", estudianteActualizado.getEdad());
            query.setParameter("genero", estudianteActualizado.getGenero());
            query.setParameter("dni", estudianteActualizado.getDni());
            query.setParameter("ciudad", estudianteActualizado.getCiudad());
            query.setParameter("estaGraduado", estudianteActualizado.getGraduado());
            query.setParameter("id", estudianteActualizado.getId());  // Usamos el id para identificar al estudiante

            // Ejecuta la actualización
            query.executeUpdate();

        }
    }

    @Override
    public void delete(EntityManager em, long id) {
        String jpql = "DELETE FROM Estudiante e WHERE e.id = :id";
        Query query = em.createQuery(jpql);
        query.setParameter("id", id);
        query.executeUpdate();  // Ejecuta la eliminación
    }

    @Override
    public Object find(EntityManager em, long id) {
        String jpql = "SELECT e FROM Estudiante e WHERE e.id = :id";
        TypedQuery<Estudiante> query = em.createQuery(jpql, Estudiante.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public static List<EstudianteDto> obtenerEstudiantesPorApellidoAsc(EntityManager em) {
        String jpql = "SELECT new tp.integrador.dto.EstudianteDto(e.nombre, e.apellido, e.edad, e.genero, e.dni, e.ciudad, e.lu, e.graduado) " +
                "FROM Estudiante e " +
                "ORDER BY e.apellido";
        TypedQuery<EstudianteDto> query = em.createQuery(jpql, EstudianteDto.class);
        return query.getResultList();
    }

    public static EstudianteDto obtenerEstudiantePorLU(EntityManager em, long lu) {
        String jpql = "SELECT new tp.integrador.dto.EstudianteDto(e.nombre, e.apellido, e.edad, e.genero, e.dni, e.ciudad, e.lu, e.graduado) " +
                "FROM Estudiante" +
                " e WHERE e.lu = :lu";
        TypedQuery<EstudianteDto> query = em.createQuery(jpql, EstudianteDto.class);
        query.setParameter("lu", lu);
        return query.getSingleResult();
    }

    public static List<EstudianteDto> obtenerEstudiantesPorGenero(EntityManager em, String genero) {
        String jpql = "SELECT new tp.integrador.dto.EstudianteDto(e.nombre, e.apellido, e.edad, e.genero, e.dni, e.ciudad, e.lu, e.graduado) " +
                "FROM Estudiante" +
                " e WHERE e.genero = :genero";
        TypedQuery<EstudianteDto> query = em.createQuery(jpql, EstudianteDto.class);
        query.setParameter("genero", genero);
        return query.getResultList();
    }

    public static List<EstudianteDto> obtenerEstudiantesPorCarreraYCiudad(EntityManager em, String nombre, String ciudad) {
        String jpql = "SELECT new tp.integrador.dto.EstudianteDto(e.nombre, e.apellido, e.edad, e.genero, e.dni, e.ciudad, e.lu, e.graduado)" +
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
