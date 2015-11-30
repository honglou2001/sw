package com.zhuika.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.stereotype.Controller;

import com.watch.ejb.Serialnumber;
import com.zhuika.entity.ElectFence;
import com.zhuika.service.ElectFenceService;
import com.zhuika.service.SerialNumberService;
import com.zhuika.util.BDTransUtil;
import com.zhuika.util.Jsonconf;
import com.zhuika.util.Tools;
@Controller
public class ElectFenceAction extends BaseAction implements
		ServletRequestAware, ServletResponseAware {
	private static final long serialVersionUID = 1L;
    private HttpServletRequest request;
    private HttpServletResponse response;
    @Resource
    private ElectFenceService electFenceService;
	@Resource
	private SerialNumberService serialNumService;

    
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;		
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response=response;	
	}
	/**
	 * 添加电子围栏，或者更新电子围栏
	 */
	public void addElectFence(){
		PrintWriter out=null;
		JSONObject json=new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			
			//response.setContentType("text/html;charset=utf8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");
			out=response.getWriter();
			String serialNumber=request.getParameter("serialNumber");
			String areaNum=request.getParameter("areaNumber");				
			String name=request.getParameter("name");	
			
			name = Tools.DecodeUtf8String(name);
//			
//			String tmpStr =  java.net.URLEncoder.encode("杨","utf-8");
			
//			String name = new String(request.getParameter("name").getBytes("ISO-8859-1"), "UTF-8");  
			 
			String locationbd=request.getParameter("locationInfo");
			System.out.println(name);
			System.out.println(locationbd);
			String scope=request.getParameter("scope");
			String model=request.getParameter("mode");	
			System.out.println("mode:"+model);
			Calendar cal=Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			double[] bd2gcj=BDTransUtil.bd2gcj(Double.parseDouble(locationbd.split(",")[1]), Double.parseDouble(locationbd.split(",")[0]));
			ElectFence electFence=new ElectFence();
			
			//areaNumber字段不使用
			electFence.setAreaNum(1);
			electFence.setSerialNumber(serialNumber);
			electFence.setName(name);
			electFence.setLocationbd(locationbd);
			//客户端先用高德
			electFence.setLocationgd(locationbd);
			//electFence.setLocationgd(String.valueOf(bd2gcj[1]).substring(0, 9)+","+String.valueOf(bd2gcj[0]).substring(0, 9));
			
			
			electFence.setModel(model);
			electFence.setScope(Integer.parseInt(scope));
			electFence.setStatus("1");
			
			
			if(areaNum==null || areaNum.length() == 0)
			{				
				HashMap<String,String> maps = new HashMap<String,String>();
				maps.put("serialNumber", serialNumber);
				maps.put("name", name);
				
				List<ElectFence> lists =electFenceService.getElectFence(maps);
				
				if(lists!=null && lists.size()>0)
				{
					json.put("state", 2);
					json.put("info", "该序列号的此围栏名称已经存在");		
					
				}
				else{
					electFence.setCreateTime(sdf.format(cal.getTime()));
					electFenceService.addElectFence(electFence);
					json.put("state", 1);
					json.put("info", "新增电子围栏成功");		
				}
			}
			else{
				ElectFence ef=electFenceService.getElectFence(serialNumber,areaNum);
				if(ef==null){		
	//				electFence.setCreateTime(sdf.format(cal.getTime()));
	//				electFenceService.addElectFence(electFence);
					json.put("state", 2);
					json.put("info", "修改失败，查询无此序列号及围栏ID的数据");
				}else{
					
					HashMap<String,String> maps = new HashMap<String,String>();
					maps.put("serialNumber", serialNumber);
					maps.put("name", name);
					maps.put("areaNum", areaNum);
					
					List<ElectFence> lists =electFenceService.getElectFence(maps);
					
					if(lists!=null && lists.size()>0)
					{
						json.put("state", 2);
						json.put("info", "修改失败，该序列号已存在其他相同的围栏名称");		
						
					}
					else{
						electFence.setId(ef.getId());
						electFence.setCreateTime(ef.getCreateTime());
						electFence.setUpdateTime(sdf.format(cal.getTime()));
						electFenceService.updateElectFence(electFence);
						json.put("state", 1);
						json.put("info", "更新电子围栏成功");
					}
				}
			}

		} catch (Exception e) {
			json.put("state", -1);
			json.put("info",e.getMessage());
		} finally{
			out.print(json);
			out.close();
		}
	}
	/**
	 * 查找电子围栏集合 
	 */
	public void findElectFence(){
		PrintWriter out=null;
		JSONObject json=new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			//response.setContentType("text/html;charset=utf8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");
			out=response.getWriter();
			String serialNumber=request.getParameter("serialNumber");
			
			Serialnumber serialnumber = serialNumService.findBySNNumber(serialNumber);
			if(serialnumber == null)
			{								
				json.put("state", 2);
				json.put("info", "找不到此序列号的信息");
				json.put("SerialNum",  null);
				json.put("data", null);
				
				out.print(json);
				out.close();
				
				return;
			}
			
			List<ElectFence> list=electFenceService.findElectFence(serialNumber);
			System.out.println(list.size());
//			JSONArray jsonArr=new JSONArray();
//			if(!list.isEmpty()){				
//				jsonArr.add(list);				
//			}
			json.put("state", 1);
			json.put("info", "成功查询");
			
			JsonConfig jsonConfig = new JsonConfig();
			
			jsonConfig.setExcludes(new String[] { "areaNum"}); 
			 
//			json.put("data",  jsonArr);
			JsonConfig jsonSerialNumConfig = Jsonconf.getSertialNumJsonConf(request);
			
			json.put("SerialNum",  JSONObject.fromObject(serialnumber,jsonSerialNumConfig));
			json.put("data",  JSONArray.fromObject(list,jsonConfig));
		} catch (Exception e) {
			json.put("state", -1);
			json.put("info",  e.getMessage());
			json.put("SerialNum",  null);
			json.put("data",  null);
		} finally{
			out.print(json);
			out.close();
		}
	}
	/**
	 * 删除电子围栏
	 */
	public void deleteElectFence(){
		JSONObject json=new JSONObject();
		PrintWriter out=null;
		try {
			request.setCharacterEncoding("utf-8");
			//response.setContentType("text/html;charset=utf8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");
			out=response.getWriter();
			String serialNumber=request.getParameter("serialNumber");
			String areaNum=request.getParameter("areaNumber");
			System.out.println(serialNumber);
			System.out.println(areaNum);
			ElectFence electFence=electFenceService.getElectFence(serialNumber, areaNum);			
			if(electFence!=null){
				electFenceService.deleteElectFence(electFence);
				json.put("state", 1);
				json.put("info", String.format("成功删除电子围栏,序列号%s，区域号%s", serialNumber,areaNum));
			}else{
				json.put("state", 1);
				json.put("info", "删除电子围栏出错，此序列号和对应的区域号不存在");
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
