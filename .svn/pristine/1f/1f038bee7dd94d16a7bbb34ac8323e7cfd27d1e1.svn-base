package com.zhuika.dao.impl;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.zhuika.dao.IAssociatedAccountDao;
import com.zhuika.dao.SHBaseDAO;
import com.zhuika.entity.AssociatedAccount;
import com.zhuika.factory.DAOException;
@Repository("assAccountDao")
public class AssociatedAccountDaoImpl extends SHBaseDAO implements IAssociatedAccountDao {

	public void addAssociatedAccount(AssociatedAccount assAccount)
			throws DAOException {
		
		super.getHibernateTemplate().save(assAccount);
	}

	public List<AssociatedAccount> findAssociatedAccount(String serialNumber)
			throws DAOException {
		String hql="from AssociatedAccount where serialNumber=?";
		Object[] params={serialNumber};
		@SuppressWarnings("unchecked")
		List<AssociatedAccount> list=super.getHibernateTemplate().find(hql, params);
		if(!list.isEmpty()){
			return list;
		}
		return null;
	}

	public void deleteAssociatedAccount(final AssociatedAccount assAccount)
			throws DAOException {
		final String hql="delete from AssociatedAccount where serialNumber=? and userName=?";
		@SuppressWarnings({ "rawtypes", "unused" })
		List list = (List)super.getHibernateTemplate().execute(
				new HibernateCallback(){
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						System.out.println("delete");
						Query query = 
							session.createQuery(hql);
						query.setParameter(0, assAccount.getSerialNumber());
						query.setParameter(1, assAccount.getUserName());
						query.executeUpdate();
						return  null;
					}
				}
			);		
	}

	public AssociatedAccount findAssociatedAccount(AssociatedAccount assAccount)
			throws DAOException {
		String hql="from AssociatedAccount where serialNumber=? and userName=?";
		Object[] params={assAccount.getSerialNumber(),assAccount.getUserName()};
		@SuppressWarnings("unchecked")
		List<AssociatedAccount> list=super.getHibernateTemplate().find(hql, params);
		
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	public void updateAssociatedAccount(String userName,String password)
			throws DAOException {
		
		String hql="from AssociatedAccount where userName=? ";
		Object[] params={userName}; 
		@SuppressWarnings("unchecked")
		List<AssociatedAccount> list=super.getHibernateTemplate().find(hql, params);	
		@SuppressWarnings("rawtypes")
		Iterator ite=list.iterator(); 
	    while(ite.hasNext()){ 			
			AssociatedAccount assAccount=(AssociatedAccount) ite.next();
			assAccount.setPassword(password);
			super.getHibernateTemplate().update(assAccount);
		} 
	}
}
