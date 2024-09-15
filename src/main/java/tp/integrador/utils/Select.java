package tp.integrador.utils;

import tp.integrador.entidades.Carrera;
import tp.integrador.entidades.Estudiante;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Select {
    public Select(){
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

    public static List<Object[]> obtenerCarrerasPorEstudiantesInscriptos(EntityManager em) {
        String jpql = "SELECT c.nombre, COUNT(e.id) AS cantidad FROM Carrera c JOIN c.estudiantes e WHERE e.id IS NOT NULL GROUP BY c.nombre ORDER BY COUNT(e.id) DESC";
        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
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

    public static List<Object[]> generarReporteCarreras(EntityManager em) {
        String jpql = "SELECT c.nombre, YEAR(ec.fechaInscripcion) as anio, " +
                "COUNT(ec.estudiante.id) AS inscriptos, " +
                "SUM(CASE WHEN e.graduado = TRUE THEN 1 ELSE 0 END) AS egresados " +
                "FROM EstudianteCarrera ec " +
                "JOIN ec.carrera c " +
                "JOIN ec.estudiante e " +
                "GROUP BY c.nombre, anio";

        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        List<Object[]> resultados = query.getResultList();

        // Ordenar los resultados en memoria
        resultados.sort(Comparator.comparing((Object[] o) -> (Integer) o[1]));

        return resultados;
    }
}
