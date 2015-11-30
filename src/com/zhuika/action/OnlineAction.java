package com.zhuika.action;

import java.io.PrintWriter;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.stereotype.Controller;
import com.zhuika.dao.ISerialNumberDao;
import com.zhuika.entity.SerialNumber;
import com.zhuika.factory.DAOException;
@Controller
public class OnlineAction extends BaseAction implements ServletRequestAware,ServletResponseAware{
	private static final long serialVersionUID = 1L;
    private HttpServletRequest request;
    private HttpServletResponse response;
    @Resource

    private ISerialNumberDao serialNumberDao;

	public void setSerialNumberDao(ISerialNumberDao serialNumberDao) {
		this.serialNumberDao = serialNumberDao;
	}
	public void setServletResponse(HttpServletResponse response) {
		this.response=response;	
	}
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;	
	}
    public void searchOnline() throws DAOException{
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
			SerialNumber sn=serialNumberDao.findBySerialNumber(serialNumber);
			if(sn==null){
				json.put("state", 0);
				json.put("info", "无此序列号信息");
			}else{
				if("1".equals(sn.getOnline())){
					json.put("state", 1);
					json.put("info", "查询成功，设备在线");
					json.put("isOnline", 1);
				}else{
					json.put("state", 1);
					json.put("info", "查询成功，设备离线");
					json.put("isOnline", 0);
				}
			}
		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());	
		} finally{
			out.print(json);
			out.close();
		}
    }
    public void getOnlineNumber(){
    	PrintWriter out=null;
    	JSONObject json=new JSONObject();
    	try {
    		request.setCharacterEncoding("utf-8");
			//response.setContentType("text/html;charset=utf8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");
			out=response.getWriter();
			json.put("state", 1);
			json.put("info", "查询成功，所有序列号的在线离线状态的总数");
			json.put("onlineCount", serialNumberDao.getOnlineNo());
			json.put("offlineCount", serialNumberDao.getNotOnlineNo());
		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());	
		} finally{
			out.print(json);
			out.close();
		}
    }

}
