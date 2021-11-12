package dao;

import model.User;
import utils.DBConnect;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
	EntityManager em = emf.createEntityManager();

	public String checkAuth (String username, String pass) {
		User userCheck = new User(username, pass);
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			Query query = em.createQuery("select u from User u where u.userName = (:username) and u.userPass = (:pass)", User.class);
			query.setParameter("username", username);
			query.setParameter("pass", pass);
			User user = (User) query.getSingleResult();
			em.getTransaction().commit();
			if(user != null){
				return user.getUserName();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
		}
		return "";
	}
}
