package com.zhuika.action;



import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.stereotype.Controller;
import com.zhuika.entity.PedoMeter;
import com.zhuika.factory.DAOException;
import com.zhuika.service.PedometerService;


@Controller
public class PedometerAction extends BaseAction implements ServletRequestAware,
		ServletResponseAware {

	private static final long serialVersionUID = 1L;
    private HttpServletRequest request;
    private HttpServletResponse response;
    @Resource
    private PedometerService pedometerService;
    
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;		
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response=response;	
	}
	  public void searchPedoMeter() throws DAOException{
		  PrintWriter out=null;
		  JSONObject json=new JSONObject();
	    	try {
	    		//133,27,2015-03-08/245,49,2015-03-09/
	    		//6,1,2015-03-10/174,35,2015-02-04/
	    		//0,0,2015-02-05/142,28,2015-02-06/56,11,2015-03-07/
	    		request.setCharacterEncoding("utf-8");
	    		//response.setContentType("text/html;charset=utf8");
				response.setContentType("text/json"); 
				response.setCharacterEncoding("utf-8"); 
				out=response.getWriter();
				String serialNumber=request.getParameter("serialNumber");
				System.out.println(serialNumber);
				String time=request.getParameter("time");
				System.out.println(time);
				Calendar calendar=Calendar.getInstance();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				calendar.setTime(sdf.parse(time));
				int day=calendar.get(Calendar.DAY_OF_WEEK);
				PedoMeter pedoMeter=pedometerService.getPedometer(serialNumber);				
				if(pedoMeter!=null){
					System.out.println("pedoMeter:"+pedoMeter.getDistance());
					String s[]=pedoMeter.getDistance().split("/");
					System.out.println("null".equals(s[day-1]));
					if(!"null".equals(s[day-1])&&s[day-1].split(",").length==3){
						if(s[day-1].split(",")[2].equals(time)){
							json.put("state", 1);
							json.put("info","成功查询");
							json.put("pedo", s[day-1].split(",")[0]);
							json.put("meter", s[day-1].split(",")[1]);
						}else{
							json.put("state", 1);
							json.put("info","成功查询");
							json.put("pedo", "0");
							json.put("meter", "0");
						}					
					}else{
						json.put("state", 1);
						json.put("info","成功查询");
						json.put("pedo", "");
						json.put("meter", "");
					}					
				}
				json.put("state", 1);
				json.put("info","成功查询，");
			} catch (Exception e) {
				json.put("state", -1);
				json.put("info", e.getMessage());
			} finally{
				out.print(json);
				out.close();
			}
	    }
}
