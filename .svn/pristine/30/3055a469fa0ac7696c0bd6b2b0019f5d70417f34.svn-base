package com.zhuika.dao.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.naming.Context;
import javax.naming.NamingException;

import org.springframework.stereotype.Repository;

import com.users.ejb.SerialnumberDatarelate;
import com.users.ejb.SerialnumberDatarelateService;
import com.users.ejb.SerialnumberSport;
import com.users.ejb.SerialnumberSportService;
import com.users.ejb.SerialnumberWeight;
import com.users.ejb.SerialnumberWeightService;
import com.zhuika.dao.ISerialnumberSportDao;
import com.zhuika.dao.SHBaseDAO;
import com.zhuika.util.ejbproxy;

import com.users.ejb.SerialnumberDatauser;
import com.users.ejb.SerialnumberDatauserService;


/**
 * <p>Title: ejb title </p>
 * <p>Description: t_serialnumber_sport Client Dao 处理类</p>
 * @author yangqinxu 电话：137****5317
 * @version 1.0 时间  2015-11-9 10:04:35
 */
@Repository("serialnumberSportDao")
public class SerialnumberSportDaoIml  extends SHBaseDAO implements ISerialnumberSportDao{	

  	public void AddSerialnumberSport(SerialnumberSport serialnumberSport) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberSportService serviceClient;
		try {
			serviceClient = (SerialnumberSportService) weblogicContext.lookup("SerialnumberSportBean/remote");
			serviceClient.AddSerialnumberSport(serialnumberSport);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	public int GetSerialnumberSportCount(HashMap<String, String> map)
	{
		int total = 0 ;			
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberSportService serviceClient;
		try {
			serviceClient = (SerialnumberSportService)weblogicContext.lookup("SerialnumberSportBean/remote");
			total = serviceClient.GetSerialnumberSportCount(map);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return total;
	}
	

	public List<SerialnumberSport> ListSerialnumberSport(int offset, int length,HashMap<String, String> map)
	{		
		List<SerialnumberSport> listSerialnumberSport =  new ArrayList<SerialnumberSport>();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			SerialnumberSportService serviceClient = (SerialnumberSportService)weblogicContext.lookup("SerialnumberSportBean/remote");
			listSerialnumberSport = serviceClient.ListSerialnumberSport(offset, length,map);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return listSerialnumberSport;		
	}
    
    public SerialnumberSport findSerialnumberSport(String fid)
	{		
		SerialnumberSport serialnumberSport = new SerialnumberSport();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			SerialnumberSportService serviceClient = (SerialnumberSportService)weblogicContext.lookup("SerialnumberSportBean/remote");
			serialnumberSport = serviceClient.findSerialnumberSport(fid);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return serialnumberSport;		
	}
    
  	public void UpdateSerialnumberSport(SerialnumberSport serialnumberSport) {		
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberSportService serviceClient;
		try {
			serviceClient = (SerialnumberSportService)weblogicContext.lookup("SerialnumberSportBean/remote");
			serviceClient.UpdateSerialnumberSport(serialnumberSport);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}        
   
	public void DeleteSerialnumberSport(String id) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberSportService serviceClient;
		try {
			serviceClient = (SerialnumberSportService) weblogicContext
					.lookup("SerialnumberSportBean/remote");
			serviceClient.DeleteSerialnumberSport(id);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  
	
	public void AddSerialnumberDatauser(SerialnumberDatauser serialnumberDatauser) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberDatauserService serviceClient;
		try {
			serviceClient = (SerialnumberDatauserService) weblogicContext.lookup("SerialnumberDatauserBean/remote");
			serviceClient.AddSerialnumberDatauser(serialnumberDatauser);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	public int GetSerialnumberDatauserCount(HashMap<String, String> map)
	{
		int total = 0 ;			
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberDatauserService serviceClient;
		try {
			serviceClient = (SerialnumberDatauserService)weblogicContext.lookup("SerialnumberDatauserBean/remote");
			total = serviceClient.GetSerialnumberDatauserCount(map);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return total;
	}
	

	public List<SerialnumberDatauser> ListSerialnumberDatauser(int offset, int length,HashMap<String, String> map)
	{		
		List<SerialnumberDatauser> listSerialnumberDatauser =  new ArrayList<SerialnumberDatauser>();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			SerialnumberDatauserService serviceClient = (SerialnumberDatauserService)weblogicContext.lookup("SerialnumberDatauserBean/remote");
			listSerialnumberDatauser = serviceClient.ListSerialnumberDatauser(offset, length,map);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return listSerialnumberDatauser;		
	}
    
    public SerialnumberDatauser findSerialnumberDatauser(String fid)
	{		
		SerialnumberDatauser serialnumberDatauser = new SerialnumberDatauser();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			SerialnumberDatauserService serviceClient = (SerialnumberDatauserService)weblogicContext.lookup("SerialnumberDatauserBean/remote");
			serialnumberDatauser = serviceClient.findSerialnumberDatauser(fid);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return serialnumberDatauser;		
	}
    
  	public void UpdateSerialnumberDatauser(SerialnumberDatauser serialnumberDatauser) {		
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberDatauserService serviceClient;
		try {
			serviceClient = (SerialnumberDatauserService)weblogicContext.lookup("SerialnumberDatauserBean/remote");
			serviceClient.UpdateSerialnumberDatauser(serialnumberDatauser);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}        

   	public void UpdateSerialnumberDatauser(SerialnumberDatauser serialnumberDatauser,HashMap<String, String> map) {		
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberDatauserService serviceClient;
		try {
			serviceClient = (SerialnumberDatauserService)weblogicContext.lookup("SerialnumberDatauserBean/remote");
			serviceClient.UpdateSerialnumberDatauser(serialnumberDatauser,map);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}    
   
	public void DeleteSerialnumberDatauser(String id) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberDatauserService serviceClient;
		try {
			serviceClient = (SerialnumberDatauserService) weblogicContext
					.lookup("SerialnumberDatauserBean/remote");
			serviceClient.DeleteSerialnumberDatauser(id);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  
	
	public void AddSerialnumberDatarelate(SerialnumberDatarelate serialnumberDatarelate) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberDatarelateService serviceClient;
		try {
			serviceClient = (SerialnumberDatarelateService) weblogicContext.lookup("SerialnumberDatarelateBean/remote");
			serviceClient.AddSerialnumberDatarelate(serialnumberDatarelate);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	public int GetSerialnumberDatarelateCount(HashMap<String, String> map)
	{
		int total = 0 ;			
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberDatarelateService serviceClient;
		try {
			serviceClient = (SerialnumberDatarelateService)weblogicContext.lookup("SerialnumberDatarelateBean/remote");
			total = serviceClient.GetSerialnumberDatarelateCount(map);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return total;
	}
	

	public List<SerialnumberDatarelate> ListSerialnumberDatarelate(int offset, int length,HashMap<String, String> map)
	{		
		List<SerialnumberDatarelate> listSerialnumberDatarelate =  new ArrayList<SerialnumberDatarelate>();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			SerialnumberDatarelateService serviceClient = (SerialnumberDatarelateService)weblogicContext.lookup("SerialnumberDatarelateBean/remote");
			listSerialnumberDatarelate = serviceClient.ListSerialnumberDatarelate(offset, length,map);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return listSerialnumberDatarelate;		
	}
    
    public SerialnumberDatarelate findSerialnumberDatarelate(String fid)
	{		
		SerialnumberDatarelate serialnumberDatarelate = new SerialnumberDatarelate();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			SerialnumberDatarelateService serviceClient = (SerialnumberDatarelateService)weblogicContext.lookup("SerialnumberDatarelateBean/remote");
			serialnumberDatarelate = serviceClient.findSerialnumberDatarelate(fid);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return serialnumberDatarelate;		
	}
    
  	public void UpdateSerialnumberDatarelate(SerialnumberDatarelate serialnumberDatarelate) {		
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberDatarelateService serviceClient;
		try {
			serviceClient = (SerialnumberDatarelateService)weblogicContext.lookup("SerialnumberDatarelateBean/remote");
			serviceClient.UpdateSerialnumberDatarelate(serialnumberDatarelate);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}        

   	public void UpdateSerialnumberDatarelate(SerialnumberDatarelate serialnumberDatarelate,HashMap<String, String> map) {		
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberDatarelateService serviceClient;
		try {
			serviceClient = (SerialnumberDatarelateService)weblogicContext.lookup("SerialnumberDatarelateBean/remote");
			serviceClient.UpdateSerialnumberDatarelate(serialnumberDatarelate,map);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}    
   
	public void DeleteSerialnumberDatarelate(String id) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberDatarelateService serviceClient;
		try {
			serviceClient = (SerialnumberDatarelateService) weblogicContext
					.lookup("SerialnumberDatarelateBean/remote");
			serviceClient.DeleteSerialnumberDatarelate(id);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void AddSerialnumberWeight(SerialnumberWeight serialnumberWeight) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberWeightService serviceClient;
		try {
			serviceClient = (SerialnumberWeightService) weblogicContext.lookup("SerialnumberWeightBean/remote");
			serviceClient.AddSerialnumberWeight(serialnumberWeight);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	public int GetSerialnumberWeightCount(HashMap<String, String> map)
	{
		int total = 0 ;			
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberWeightService serviceClient;
		try {
			serviceClient = (SerialnumberWeightService)weblogicContext.lookup("SerialnumberWeightBean/remote");
			total = serviceClient.GetSerialnumberWeightCount(map);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return total;
	}
	

	public List<SerialnumberWeight> ListSerialnumberWeight(int offset, int length,HashMap<String, String> map)
	{		
		List<SerialnumberWeight> listSerialnumberWeight =  new ArrayList<SerialnumberWeight>();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			SerialnumberWeightService serviceClient = (SerialnumberWeightService)weblogicContext.lookup("SerialnumberWeightBean/remote");
			listSerialnumberWeight = serviceClient.ListSerialnumberWeight(offset, length,map);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return listSerialnumberWeight;		
	}
    
    public SerialnumberWeight findSerialnumberWeight(String fid)
	{		
		SerialnumberWeight serialnumberWeight = new SerialnumberWeight();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			SerialnumberWeightService serviceClient = (SerialnumberWeightService)weblogicContext.lookup("SerialnumberWeightBean/remote");
			serialnumberWeight = serviceClient.findSerialnumberWeight(fid);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return serialnumberWeight;		
	}
    
  	public void UpdateSerialnumberWeight(SerialnumberWeight serialnumberWeight) {		
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberWeightService serviceClient;
		try {
			serviceClient = (SerialnumberWeightService)weblogicContext.lookup("SerialnumberWeightBean/remote");
			serviceClient.UpdateSerialnumberWeight(serialnumberWeight);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}        

   	public void UpdateSerialnumberWeight(SerialnumberWeight serialnumberWeight,HashMap<String, String> map) {		
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberWeightService serviceClient;
		try {
			serviceClient = (SerialnumberWeightService)weblogicContext.lookup("SerialnumberWeightBean/remote");
			serviceClient.UpdateSerialnumberWeight(serialnumberWeight,map);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}    
   
	public void DeleteSerialnumberWeight(String id) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberWeightService serviceClient;
		try {
			serviceClient = (SerialnumberWeightService) weblogicContext
					.lookup("SerialnumberWeightBean/remote");
			serviceClient.DeleteSerialnumberWeight(id);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}   


}
