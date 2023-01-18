package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }
    private final SessionFactory factory = Util.getFactory();


    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE Users" + "(ID INTEGER NOT NULL AUTO_INCREMENT," + "Name VARCHAR(255),"
                + "LastName VARCHAR(255)," + "Age TINYINT," + "PRIMARY KEY (ID))";
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch(Exception e) {
            System.out.println("Table users already exists");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE users").executeUpdate();
            session.getTransaction().commit();
        } catch(Exception e) {
            System.out.println("Can't delete table, table does not exist");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("saveUser: Exception was caught");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("removeUserById: Exception was caught");
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            List<User> userList = session.createQuery("FROM User").getResultList();
            session.getTransaction().commit();
            list = userList;
        } catch (Exception e) {
            System.out.println("getAllUsers: Exception was caught");
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.createQuery("DELETE from User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e){
            System.out.println("cleanUsersTable: exception was caught");
        }

    }
}
