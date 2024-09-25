package tp.integrador.factory;

import tp.integrador.repositories.entitiesRepositories.CarreraRepositoryImpl;
import tp.integrador.repositories.entitiesRepositories.EstudianteCarreraRepositoryImpl;
import tp.integrador.repositories.entitiesRepositories.EstudianteRepositoryImpl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLREPOSITORYFactory extends AbstractFactory {
    private static MySQLREPOSITORYFactory instance = null;

    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String uri = "jdbc:mysql://localhost:3306/integrador2";
    public static Connection conn;

    private MySQLREPOSITORYFactory() {
    }

    /*Singleton*/
    public static synchronized MySQLREPOSITORYFactory getInstance() {
        if (instance == null) {
            instance = new MySQLREPOSITORYFactory();
        }
        return instance;
    }

    public static Connection createConnection() {
        if (conn != null) {
            return conn;
        }
        String driver = DRIVER;
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public EstudianteRepositoryImpl getEstudianteRepository() {
        return EstudianteRepositoryImpl.getInstance();
    }

    @Override
    public CarreraRepositoryImpl getCarreraRepository() {
        return CarreraRepositoryImpl.getInstance();
    }

    @Override
    public EstudianteCarreraRepositoryImpl getEstudianteCarreraRepository(){
        return EstudianteCarreraRepositoryImpl.getInstance();
    }
}
