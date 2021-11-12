package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Pond;
import model.User;
import validate.DuplicateException;
import validate.ExistException;
import validate.Validate;
import utils.DBConnect;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class PondDao {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
	EntityManager em = emf.createEntityManager();
	
	// kiểm tra tên có trùng lặp
	public boolean checkPond(String name, int id) {
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			Query query = null;
			if (id != 0) {
				query = em.createQuery("select p from Pond p where p.pondName = (:name) and p.id <> (:id)", Pond.class);
				query.setParameter("name", name);
				query.setParameter("id",id);

			} else {
				query = em.createQuery("select p from Pond p where p.pondName = (:name)", Pond.class);
				query.setParameter("name", name);
			}

			List check = query.getResultList();
			em.getTransaction().commit();
            if(check.size() == 0){
            	return false;
			}
            return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			em.close();
		}
		return true;
	}
	
	public Pond getPond(int id) {
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			Pond pond = em.find(Pond.class, id);
			em.getTransaction().commit();
			return pond;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return null;
	}
	
	public int countTotalRecords() {
		int totalRecords = 0;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			Query query = em.createQuery("select count(p.id) as total from Pond p");
			Long total = (Long) query.getSingleResult();
			em.getTransaction().commit();

	        if (total != null) {
				totalRecords = total.intValue();
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return totalRecords;
	}
	
	public int countTotalSearchRecords(String search) {
		int totalRecords = 0;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			Query query = em.createQuery("select count(p.id) as total from Pond p where p.pondName like (:search) or p.pondAddress like (:search)");
			query.setParameter("search","%"+search+"%");
			Long total = (Long) query.getSingleResult();
			em.getTransaction().commit();

			if (total != null) {
				totalRecords = total.intValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return totalRecords;
	}
	
	public List<Pond> getListPond() throws SQLException{
		List<Pond> listPond;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			listPond = em.createQuery("select p from Pond p").getResultList();
			em.getTransaction().commit();
		} finally {
			em.close();
		}
		return listPond;
	}
	
	// Return danh sách ID hồ cá theo từ khóa tìm kiếm 
	public List<Integer> getArrayPondId(String search) throws SQLException {
		List<Integer> listPondId = new ArrayList();
		try {
			if(search != null){
				em = emf.createEntityManager();
				em.getTransaction().begin();
				Query query = em.createQuery("select p from Pond p where p.pondName like (:name)");
				query.setParameter("name","%"+search+"%");
				List<Pond> list = query.getResultList();
				em.getTransaction().commit();

				if(list.size() > 0){
					for (Pond pond : list) {
						listPondId.add(pond.getId());
					}
				}
			}
		} finally {
			em.close();
		}
		return listPondId;
	}
	
	public List<Pond> getPondPaginate(int page, String search, int itemPerPage){
		List<Pond> pondList = new ArrayList();
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			Query query = null;
			if(search != null) {
				query = em.createQuery("select p from Pond p where p.pondName like ?1 or p.pondAddress like ?2 order by p.id desc");
				query.setParameter(1,"%"+search+"%");
				query.setParameter(2,"%"+search+"%");
				query.setMaxResults(itemPerPage);
				query.setFirstResult(page*itemPerPage);
			} else {
				query = em.createQuery("select p from Pond p order by p.id desc");
				query.setMaxResults(itemPerPage);
				query.setFirstResult(page*itemPerPage);
			}
			pondList = query.getResultList();
			em.getTransaction().commit();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
		}
		return pondList;
	}
	
	public void insertPond(Pond pond){
		if (!checkPond(pond.getPondName(),0)) {
			try {
				em = emf.createEntityManager();
				em.getTransaction().begin();
				em.persist(pond);
				em.getTransaction().commit();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				em.close();
			}
		} 
	}
	
	public Pond getEditPond(int id) {
		Pond editPond = null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			editPond = em.find(Pond.class, id);
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return editPond;
	}
	
	public void editPond(Pond pond) throws DuplicateException {
		if (checkPond(pond.getPondName(),pond.getId())) {
			throw new DuplicateException();
		} else {
			try {
				em = emf.createEntityManager();
				em.getTransaction().begin();
				Pond pondEdit = em.find(Pond.class, pond.getId());
				pondEdit.setPondName(pond.getPondName());
				pondEdit.setPondAddress(pond.getPondAddress());
				pondEdit.setPhone(pond.getPhone());
				em.getTransaction().commit();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				em.close();
			}
		}
	}
	
	public String deletePond(int id) throws ExistException {
		String message = "";
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			Pond pond = em.find(Pond.class, id);
			if(pond != null && pond.getAmountOfType() == 0){
				em.remove(pond);
				em.getTransaction().commit();
				message = "Xóa thành công";
			} else {
				message = "Hồ có cá không thể xóa";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return message;
	}
}
