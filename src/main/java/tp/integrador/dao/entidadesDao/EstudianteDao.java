package tp.integrador.dao.entidadesDao;

import tp.integrador.dao.Dao;
import tp.integrador.entidades.Carrera;
import tp.integrador.entidades.Estudiante;
import tp.integrador.entidades.EstudianteCarrera;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.*;
import java.util.List;


public class EstudianteDao implements Dao<EstudianteDao> {

    public EstudianteDao() {
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

    public static List<Estudiante> obtenerEstudiantesPorApellidoAsc(EntityManager em) {
        String jpql = "SELECT e FROM Estudiante e ORDER BY e.apellido";
        TypedQuery<Estudiante> query = em.createQuery(jpql, Estudiante.class);
        return query.getResultList();
    }

    public static Estudiante obtenerEstudiantePorLU(EntityManager em, long lu) {
        String jpql = "SELECT e FROM Estudiante e WHERE e.lu = :lu";
        TypedQuery<Estudiante> query = em.createQuery(jpql, Estudiante.class);
        query.setParameter("lu", lu);
        return query.getSingleResult();
    }

    public static List<Estudiante> obtenerEstudiantesPorGenero(EntityManager em, String genero) {
        String jpql = "SELECT e FROM Estudiante e WHERE e.genero = :genero";
        TypedQuery<Estudiante> query = em.createQuery(jpql, Estudiante.class);
        query.setParameter("genero", genero);
        return query.getResultList();
    }

    public static List<Estudiante> obtenerEstudiantesPorCarreraYCiudad(EntityManager em, String nombre, String ciudad) {
        String jpql = "SELECT e " +
                "FROM EstudianteCarrera ec " +
                "JOIN ec.estudiante e " +
                "JOIN ec.carrera c " +
                "WHERE c.nombre = :nombre AND e.ciudad = :ciudad";
        TypedQuery<Estudiante> query = em.createQuery(jpql, Estudiante.class);
        query.setParameter("nombre", nombre);
        query.setParameter("ciudad", ciudad);
        return query.getResultList();
    }
}
