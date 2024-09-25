package tp.integrador.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import tp.integrador.entidades.Carrera;
import tp.integrador.entidades.Estudiante;
import tp.integrador.entidades.EstudianteCarrera;
import tp.integrador.entidades.EstudianteCarreraPK;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;


public class HelperMySQL {
    private Connection conn = null;

    public HelperMySQL() {//Constructor
        String driver = "com.mysql.cj.jdbc.Driver";
        String uri = "jdbc:mysql://localhost:3306/integrador2";

        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                 | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            conn = DriverManager.getConnection(uri, "root", "");
            conn.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void dropTables() throws SQLException {
        String dropEstudianteCarrera = "DROP TABLE IF EXISTS EstudianteCarrera";
        this.conn.prepareStatement(dropEstudianteCarrera).execute();

        String dropEstudiante = "DROP TABLE IF EXISTS Estudiante";
        this.conn.prepareStatement(dropEstudiante).execute();

        String dropCarrera = "DROP TABLE IF EXISTS Carrera";
        this.conn.prepareStatement(dropCarrera).execute();
    }

    public void populateDB() throws IOException, CsvException, ParseException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Example");
        EntityManager em = emf.createEntityManager();

        // Cargar los datos desde los archivos CSV
        cargarEstudiantes(em, "src/main/resources/csv/Estudiantes.csv");
        cargarCarreras(em, "src/main/resources/csv/Carreras.csv");
        cargarEstudianteCarreraPK(em, "src/main/resources/csv/Estudiante_Carreras_PK.csv");
        cargarEstudianteCarrera(em, "src/main/resources/csv/Estudiante_Carreras.csv");

        em.close();
        emf.close();
    }


    public static void cargarEstudiantes(EntityManager em, String archivoCsv) throws IOException, CsvException, ParseException {
        CSVReader reader = new CSVReader(new FileReader(archivoCsv));
        List<String[]> records = reader.readAll();

        em.getTransaction().begin();

        for (int i = 1; i < records.size(); i++) { // Iniciamos en 1 para evitar la cabecera
            String[] row = records.get(i);

            Estudiante estudiante = new Estudiante();
            estudiante.setId(Long.parseLong(row[0]));
            estudiante.setNombre(row[1]);
            estudiante.setApellido(row[2]);
            estudiante.setEdad(Integer.parseInt(row[3]));
            estudiante.setGenero(row[4]);
            estudiante.setDni(Integer.parseInt(row[5]));
            estudiante.setCiudad(row[6]);
            estudiante.setLu(Long.parseLong(row[7]));

            em.persist(estudiante);
        }

        em.getTransaction().commit();
    }

    public static void cargarCarreras(EntityManager em, String archivoCsv) throws IOException, CsvException {
        CSVReader reader = new CSVReader(new FileReader(archivoCsv));
        List<String[]> records = reader.readAll();

        em.getTransaction().begin();

        for (int i = 1; i < records.size(); i++) {
            String[] row = records.get(i);

            Carrera carrera = new Carrera();
            carrera.setId(Long.parseLong(row[0]));
            carrera.setNombre(row[1]);

            em.merge(carrera);
        }

        em.getTransaction().commit();
    }

    public static void cargarEstudianteCarrera(EntityManager em, String archivoCsv) throws IOException, CsvException {
        CSVReader reader = new CSVReader(new FileReader(archivoCsv));
        List<String[]> records = reader.readAll();

        em.getTransaction().begin();

        for (int i = 1; i < records.size(); i++) {
            String[] row = records.get(i);

            // Crear la clave compuesta (EstudianteCarreraPK)
            EstudianteCarreraPK pk = new EstudianteCarreraPK(Long.parseLong(row[0]), Long.parseLong(row[1]));

            // Crear la entidad EstudianteCarrera y asignarle la clave primaria compuesta
            EstudianteCarrera estudianteCarrera = new EstudianteCarrera();
            estudianteCarrera.setId(pk);

            // Encontrar las entidades Estudiante y Carrera
            Estudiante estudiante = em.find(Estudiante.class, Long.parseLong(row[0]));
            Carrera carrera = em.find(Carrera.class, Long.parseLong(row[1]));

            // Asignar estudiante y carrera
            estudianteCarrera.setEstudiante(estudiante);
            estudianteCarrera.setCarrera(carrera);

            // Asignar fecha de inscripción y fecha de graduación
            estudianteCarrera.setFechaInscripcion(Date.valueOf(row[2]));

            // Verificar si la fecha de graduación es null
            if (!row[3].equalsIgnoreCase("NULL")) {
                estudianteCarrera.setFechaGraduacion(Date.valueOf(row[3]));
            }

            // Asignar si está graduado o no
            estudianteCarrera.setEstaGraduado(Boolean.parseBoolean(row[4]));

            // Persistir o fusionar el objeto en la base de datos
            em.merge(estudianteCarrera);
        }

        em.getTransaction().commit();
    }

    public static void cargarEstudianteCarreraPK(EntityManager em, String archivoCsv) throws IOException, CsvException {
        CSVReader reader = new CSVReader(new FileReader(archivoCsv));
        List<String[]> records = reader.readAll();

        em.getTransaction().begin();

        for (int i = 1; i < records.size(); i++) {
            String[] row = records.get(i);

            // Crear la clave compuesta (EstudianteCarreraPK)
            EstudianteCarreraPK pk = new EstudianteCarreraPK(Long.parseLong(row[0]), Long.parseLong(row[1]));

            // Crear la entidad EstudianteCarrera y asignarle la clave primaria compuesta
            EstudianteCarrera estudianteCarrera = new EstudianteCarrera();
            estudianteCarrera.setId(pk);

            // Buscar el estudiante y la carrera
            Estudiante estudiante = em.find(Estudiante.class, pk.getId_estudiante());
            Carrera carrera = em.find(Carrera.class, pk.getId_carrera());

            // Asignar estudiante y carrera
            estudianteCarrera.setEstudiante(estudiante);
            estudianteCarrera.setCarrera(carrera);

            // Guardar la relación
            em.merge(estudianteCarrera);
        }

        em.getTransaction().commit();
    }

}




