package dao;

import model.Fish;
import model.Pond;
import utils.DBConnect;
import validate.Validate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class FishDao {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
	EntityManager em = emf.createEntityManager();
	
	public int countTotalRecords() {
		int totalRecords = 0;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			Query query = em.createQuery("select count(f.id) as total from Fish f");
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
	
	public boolean checkFish(String fishName, int pondId, int id) {
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			Query query = null;
			if (id != 0) {
				query = em.createQuery("select f from Fish f where f.name = (:name) and f.pond.id = (:pondId) and f.id <> (:id)", Fish.class);
				query.setParameter("name", fishName);
				query.setParameter("pondId", pondId);
				query.setParameter("id", id);
	            
			} else {
				String sql = "Select * From fishmanager Where name = ? And pondId = ?";
				query = em.createQuery("select f from Fish f where f.name = (:name) and f.pond.id = (:pondId)", Fish.class);
				query.setParameter("name", fishName);
				query.setParameter("pondId", pondId);
			}
			
            Fish fish = (Fish) query.getSingleResult();
			em.getTransaction().commit();
			if(fish == null){
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return false;
	}
	
	public int countTotalSearchRecords(String search, List<Integer> listPondId) {
		int totalRecords = 0;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			String sql = "Select * From fishmanager Where fishName Like '%"+search+"%'";
			
			if(listPondId.size() != 0) {
				for(int item : listPondId) {
					sql = sql.concat(" Or pondId="+item);
				}
			}
			List<Fish> list = em.createNativeQuery(sql).getResultList();
			em.getTransaction().commit();
	        if (list.size() > 0) {
				totalRecords = list.size();
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return totalRecords;
	}
	
	public List<Fish> getFishPaginate(int page, String search, int itemPerPage){
		List<Fish> fishList = new ArrayList();
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			Query query = null;
			if(search != null) {
				query = em.createQuery("select f from Fish f join Pond p on f.pond.id = p.id where f.name like ?1 or p.pondName like ?2 order by f.id desc");
				query.setParameter(1,"%"+search+"%");
				query.setParameter(2,"%"+search+"%");
				query.setMaxResults(itemPerPage);
				query.setFirstResult(page*itemPerPage);
			} else {
				query = em.createQuery("select f from Fish f  order by f.id desc");
				query.setMaxResults(itemPerPage);
				query.setFirstResult(page*itemPerPage);
			}
			query.setFirstResult(page*itemPerPage);
			query.setMaxResults(itemPerPage);

			fishList = query.getResultList();
			em.getTransaction().commit();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
		}
		return fishList;
	}
	
	public Fish getEditFish(int id) {
		Fish editFish = null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			editFish = em.find(Fish.class, id);
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return editFish;
	}
	
	public String insertFish(Fish fish) throws SQLException {
		if(!checkFish(fish.getName(), fish.getPond().getId(), 0)) {
			try {
				em = emf.createEntityManager();
				em.getTransaction().begin();
				em.persist(fish);

				Pond pondEdit = em.find(Pond.class, fish.getPond().getId());
				int amountType = pondEdit.getAmountOfType()+1;
				int amountTotal = pondEdit.getAmountOfTotal()+fish.getAmount();
				pondEdit.setAmountOfType(amountType);
				pondEdit.setAmountOfTotal(amountTotal);

				em.getTransaction().commit();
				return "Thêm mới thành công";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				em.getTransaction().rollback();
				return "Có lỗi xảy ra. Thêm mới không thành công";
			} finally {
				em.close();
			}
		}
		return "Thêm mới thành công"; 
	}
	
	public String editFish(Fish fish) throws SQLException {
		if(!checkFish(fish.getName(), fish.getPond().getId(), fish.getId())) {
			Fish fishBeforeUpdate = getEditFish(fish.getId());
			try {
				em = emf.createEntityManager();
				em.getTransaction().begin();
				Pond oldPond = em.find(Pond.class, fishBeforeUpdate.getPond().getId());
				Pond newPond = em.find(Pond.class, fish.getPond().getId());
				String sql;
				Fish fishEdit = em.find(Fish.class, fish.getId());
				if (fish.getImage() != null) {
					fishEdit.setName(fish.getName());
					fishEdit.setMin_size(fish.getMin_size());
					fishEdit.setMax_size(fish.getMax_size());
					fishEdit.setAmount(fish.getAmount());
					fishEdit.setImage(fish.getImage());
					fishEdit.setPond(fish.getPond());
				} else {
					fishEdit.setName(fish.getName());
					fishEdit.setMin_size(fish.getMin_size());
					fishEdit.setMax_size(fish.getMax_size());
					fishEdit.setAmount(fish.getAmount());
					fishEdit.setPond(fish.getPond());
				}
				
				int amountChanged = fishBeforeUpdate.getAmount() - fish.getAmount();
				if(oldPond.getId() == newPond.getId()){
					int amountOfTotal = oldPond.getAmountOfTotal() - amountChanged;
					oldPond.setAmountOfTotal(amountOfTotal);
				} else {
					int amountOfTotal = oldPond.getAmountOfTotal() - fishBeforeUpdate.getAmount();
					int amountOfType = oldPond.getAmountOfType() -1;
					oldPond.setAmountOfTotal(amountOfTotal);
					oldPond.setAmountOfType(amountOfType);

					amountOfTotal = newPond.getAmountOfTotal() + fish.getAmount();
					amountOfType = newPond.getAmountOfType() +1;
					newPond.setAmountOfType(amountOfType);
					newPond.setAmountOfTotal(amountOfTotal);
				}

				em.getTransaction().commit();
				return "Cập nhật thành công";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				em.getTransaction().rollback();
				return "Có lỗi xảy ra. Cập nhật không thành công";
			} finally {
				em.close();
			}
		}
		return "Cập nhật không thành công";
		
	}
	
	public String deleteFish(int id) throws SQLException {
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			Fish fish = em.find(Fish.class, id);
			
			int pondID = 0;
			int amountFish = 0;
			int amountType = 0;
			int amountTotal = 0;
			
			if(fish != null) {
				pondID = fish.getPond().getId();
				amountFish = fish.getAmount();
				em.remove(fish);
			}
			
			Pond pondEdit = em.find(Pond.class, pondID);
			amountType = pondEdit.getAmountOfType();
			amountTotal = pondEdit.getAmountOfTotal();
			
			amountType--;
			amountTotal-=amountFish;

			pondEdit.setAmountOfTotal(amountTotal);
			pondEdit.setAmountOfType(amountType);

			em.getTransaction().commit();
			return "Xóa thành công";
			
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			return "Có lỗi xảy ra. Xóa không thành công";
		} finally {
			em.close();
		}
	}
}
