package tp.integrador;

import tp.integrador.entidades.Carrera;
import tp.integrador.entidades.Estudiante;
import tp.integrador.entidades.EstudianteCarrera;
import tp.integrador.factory.AbstractFactory;
import tp.integrador.factory.MySQLDAOFactory;
import tp.integrador.utils.HelperMySQL;
import tp.integrador.utils.Select;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        HelperMySQL dbMySQL = new HelperMySQL();
        dbMySQL.dropTables();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Example");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        dbMySQL.populateDB();

        AbstractFactory chosenFactory = AbstractFactory.getDAOFactory(1);

        /* Punto A */
        Estudiante e = new Estudiante(99, "Juan", "Gomez", 22, "Masculino", 12352378, "Córdoba", 1009, true);
        chosenFactory.getEstudianteDao().insert(em, e);

        /* Punto B */
        Carrera c = (Carrera) chosenFactory.getCarreraDao().select(em, 1);

        EstudianteCarrera ec = new EstudianteCarrera(4, e, c, 4, Date.valueOf("2020-01-01"));
        chosenFactory.getEstudianteCarreraDao().insert(em, ec);

        /* Punto C */
        System.out.println("Estudiante por LU: ");
        System.out.println(Select.obtenerEstudiantePorLU(em, 12345));

        /* Punto D */

        System.out.println("Estudiantes listados por Apellido: ");
        System.out.println(Select.obtenerEstudiantesPorApellidoAsc(em));

        System.out.println("/////////////////////////////////////");
        System.out.println("/////////////////////////////////////");

        System.out.println();

        /* Punto E */
        System.out.println("Estudiantes por género: ");
        System.out.println(Select.obtenerEstudiantesPorGenero(em, "Masculino"));


        System.out.println("/////////////////////////////////////");
        System.out.println("/////////////////////////////////////");

        System.out.println();


        /* Punto F */
        System.out.println("Carreras por Inscriptos: ");
        List<Object[]> resultados = Select.obtenerCarrerasPorEstudiantesInscriptos(em);
        for (Object[] resultado : resultados) {
            String nombreCarrera = (String) resultado[0];
            Long numeroEstudiantes = (Long) resultado[1];
            System.out.println("Carrera: " + nombreCarrera + ", Estudiantes inscriptos: " + numeroEstudiantes);
        }
        System.out.println("/////////////////////////////////////");
        System.out.println("/////////////////////////////////////");

        System.out.println();

        /* Punto G */
        System.out.println("Estudiantes Ordenados por Carrera y Ciudad: ");
        System.out.println(Select.obtenerEstudiantesPorCarreraYCiudad(em, "Ingeniería en Sistemas", "Buenos Aires"));
        em.getTransaction().commit();

        System.out.println("/////////////////////////////////////");
        System.out.println("/////////////////////////////////////");

        System.out.println();

        /* Ejercicio 3 */

        System.out.println("Generar Repoterte: ");
        List<Object[]> reporte = Select.generarReporteCarreras(em);
        for (Object[] fila : reporte) {
            String nombreCarrera = (String) fila[0];
            Integer anio = (Integer) fila[1];
            Long inscriptos = (Long) fila[2];
            Long egresados = (Long) fila[3];
            System.out.println("Carrera: " + nombreCarrera + ", Año: " + anio +
                    ", Inscriptos: " + inscriptos + ", Egresados: " + egresados);
        }


        em.close();
        emf.close();

    }
}
