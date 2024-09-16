package tp.integrador;

import tp.integrador.dao.entidadesDao.CarreraDao;
import tp.integrador.dao.entidadesDao.EstudianteCarreraDao;
import tp.integrador.dao.entidadesDao.EstudianteDao;
import tp.integrador.entidades.Carrera;
import tp.integrador.entidades.Estudiante;
import tp.integrador.entidades.EstudianteCarrera;
import tp.integrador.factory.AbstractFactory;
import tp.integrador.utils.HelperMySQL;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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

        EstudianteDao estudiante = chosenFactory.getEstudianteDao();
        CarreraDao carrera = chosenFactory.getCarreraDao();
        EstudianteCarreraDao estudianteCarrera = chosenFactory.getEstudianteCarreraDao();

        /* Punto A */
        Estudiante e = new Estudiante(99, "Juan", "Gomez", 22, "Masculino", 12352378, "Córdoba", 1009, true);
        chosenFactory.getEstudianteDao().insert(em, e);

        /* Punto B */
        Carrera c = (Carrera) chosenFactory.getCarreraDao().select(em, 1);

        EstudianteCarrera ec = new EstudianteCarrera(4, e, c, 4, Date.valueOf("2020-01-01"));
        chosenFactory.getEstudianteCarreraDao().insert(em, ec);

        /* Punto C */
        System.out.println("Estudiante por LU: ");
        System.out.println(estudiante.obtenerEstudiantePorLU(em, 12345));

        /* Punto D */

        System.out.println("Estudiantes listados por Apellido: ");
        System.out.println(estudiante.obtenerEstudiantesPorApellidoAsc(em));

        System.out.println("/////////////////////////////////////");
        System.out.println("/////////////////////////////////////");

        System.out.println();

        /* Punto E */
        System.out.println("Estudiantes por género: ");
        System.out.println(estudiante.obtenerEstudiantesPorGenero(em, "Masculino"));


        System.out.println("/////////////////////////////////////");
        System.out.println("/////////////////////////////////////");

        System.out.println();


        /* Punto F */
        System.out.println("Carreras por Inscriptos: ");
        System.out.println(carrera.obtenerCarrerasPorEstudiantesInscriptos(em));
        System.out.println("/////////////////////////////////////");
        System.out.println("/////////////////////////////////////");

        System.out.println();

        /* Punto G */
        System.out.println("Estudiantes Ordenados por Carrera y Ciudad: ");
        System.out.println(estudiante.obtenerEstudiantesPorCarreraYCiudad(em, "Ingeniería en Sistemas", "Buenos Aires"));

        System.out.println("/////////////////////////////////////");
        System.out.println("/////////////////////////////////////");

        System.out.println();

        /* Ejercicio 3 */

        System.out.println("Generar Repoterte: ");
        System.out.println(estudianteCarrera.generarReporteCarreras(em));


        em.close();
        emf.close();

    }
}
