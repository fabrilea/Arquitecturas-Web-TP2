package tp.integrador.repositories;

import javax.persistence.EntityManager;
import java.sql.SQLException;

public interface Repository<T> {
    Object select(EntityManager em, long id);
    void insert(EntityManager em, Object obj) throws SQLException;
    void update(EntityManager em, long id, Object obj);
    void delete(EntityManager em, long id);
    Object find(EntityManager em, long id);
}
