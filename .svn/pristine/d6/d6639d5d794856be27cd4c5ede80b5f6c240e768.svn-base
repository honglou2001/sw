package com.zhuika.action;

import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.stereotype.Controller;
import com.zhuika.entity.SerialNumber;
import com.zhuika.factory.DAOException;
import com.zhuika.service.SerialNumberService;




@Controller
public class GpsAction extends BaseAction implements ServletRequestAware,
		ServletResponseAware {

	private static final long serialVersionUID = 1L;
    private HttpServletRequest request;
    private HttpServletResponse response;
    @Resource
    private SerialNumberService serialNumberService;
    
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;		
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response=response;	
	}
	
	public void searchLbs() throws DAOException{
		PrintWriter out=null;
		JSONObject json=new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			//response.setContentType("text/html;charset=utf8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8"); 
			out=response.getWriter();
			String serialNumber=request.getParameter("serialNumber");
			SerialNumber sn=serialNumberService.getSerialNumber(serialNumber);
			json.put("state", 1);
			json.put("info", "成功查询");	
			json.put("lbs", sn.getLbs());
			json.put("gps",sn.getSetGps());
		} catch (Exception e) {
			json.put("state", -1);
			json.put("info",e.getMessage());
		} finally{
			out.print(json);
			out.close();
		}
	}	
	
	public void setLbs() throws DAOException{
		PrintWriter out=null;
		JSONObject json=new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			//response.setContentType("text/html;charset=utf8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8"); 
			out=response.getWriter();
			String serialNumber=request.getParameter("serialNumber");
			String lbs=request.getParameter("lbs");
			String gps=request.getParameter("gps");
			
			if(lbs==null || lbs.equals("")||gps==null || gps.equals(""))
			{				
				json.put("state", -1);
				json.put("info", "定位模式或定位频率都不能为空");	
				json.put("lbs", lbs);
				json.put("gps",gps);
				
			}
			else
			{
				if((lbs.equals("1") && gps.equals("60"))  
				 ||(lbs.equals("0") && gps.equals("180"))
				 ||(lbs.equals("0") && gps.equals("360")))
				{
					int state = 1;
					String info="成功给此手表设置gps信息";
					SerialNumber sn=serialNumberService.getSerialNumber(serialNumber);
					
					if(sn.getOnline()==null || !sn.getOnline().equals("1"))
					{
						state = 3;
						info = "检测到设备不在线，发送可能失败";
					}
					
					sn.setGpsStatus("0");
					sn.setLbs(lbs);
					sn.setSetGps(gps);
					serialNumberService.updateSerialNumber(sn);
					json.put("state", state);
					json.put("info",info);			
				}
				else
				{
					json.put("state", 2);
					json.put("info", "提交失败，参数值不匹配，lbs为1时，gps 固定取值60 -sos模式；lbs为0时，gps取值180 -安全模式,gps取值360-省电模式");			
				}
			
				json.put("lbs", lbs);
				json.put("gps",gps);
			}
		} catch (Exception e) {
			json.put("state", -1);
			json.put("info",e.getMessage());
		} finally{
			out.print(json);
			out.close();
		}
	}
	
}
