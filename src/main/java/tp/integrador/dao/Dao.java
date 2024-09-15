package tp.integrador.dao;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {
    Object select(EntityManager em, long id);
    void insert(EntityManager em, Object obj) throws SQLException;
    void update(EntityManager em, long id, Object obj);
    void delete(EntityManager em, long id);
    Object find(EntityManager em, long id);
}
