package com.zhuika.action;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.stereotype.Controller;
import com.zhuika.dao.ILocationInfoDao;
import com.zhuika.entity.LocationInfo;
import com.zhuika.factory.DAOException;

@Controller
public class BatteryAction extends BaseAction implements ServletRequestAware,ServletResponseAware{
	private static final long serialVersionUID = 1L;
    private HttpServletRequest request;
    private HttpServletResponse response;
    @Resource
    private ILocationInfoDao liDao;
    
	public ILocationInfoDao getLiDao() {
		return liDao;
	}
	public void setLiDao(ILocationInfoDao liDao) {
		this.liDao = liDao;
	}
	public void setServletResponse(HttpServletResponse response) {
		this.response=response;	
	}
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;	
	}
    public void searchBattery() throws DAOException{
    	PrintWriter out=null;
    	JSONObject json=new JSONObject();
    	try {
    		request.setCharacterEncoding("utf-8");
			//response.setContentType("text/html;charset=utf8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");
			out=response.getWriter();
			String serialNumber=request.getParameter("serialNumber");
			System.out.println(serialNumber);
		    LocationInfo li=liDao.findBySeriaNumber(serialNumber);
		    if(li.getBattery()==null){
		    	json.put("state", 2);
		    	json.put("info", "查询成功，无电量信息");
		    	json.put("battery", "100");
		    }else{		    	
		    	json.put("state", 1);
		    	json.put("info", "查询成功");
		    	json.put("battery", Integer.valueOf(li.getBattery()));
		    }
		} catch (Exception e) {
			json.put("state", -1);		
			json.put("info", e.getMessage());
		} finally{
			out.print(json);
			out.close();
		}
    }
}
