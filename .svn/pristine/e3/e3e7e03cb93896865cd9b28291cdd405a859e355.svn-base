package com.zhuika.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.users.ejb.SerialnumberFee;
import com.watch.ejb.Serialnumber;
import com.watch.ejb.UserWatch;
import com.zhuika.service.SerialNumberService;
import com.zhuika.service.UserService;

/**
 * @author yqx
 * 套餐、订单管理等
 */
public class PacketAction  extends BaseAction implements ServletResponseAware,
ServletRequestAware {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	@Resource
	private SerialNumberService serialNumService;
	@Resource
	private UserService userService;

	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		
	}
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
		
	}
	
	//获取支付宝支付订单号好
	public void getTradeNoAction() {
		PrintWriter out=null;
		JSONObject json=new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");
			out=response.getWriter();
			
			String usrid= request.getParameter("usrid");
			String serialnumid = request.getParameter("serialnumid");
			
			UserWatch user =null;
			if(usrid!=null && usrid.length()>0){
				user = userService.Find(usrid);
			}
			
			if (user == null) {				
				json.put("state", 2);
				json.put("info", "此用户不存在");		
				out.print(json);
				out.close();				
				return ;
			}
			
			Serialnumber serialnumber =null;
			if(serialnumid!=null && serialnumid.length()>0){
				serialnumber = serialNumService.Find(serialnumid);
			}
			if (serialnumber == null) {				
				json.put("state", 3);
				json.put("info", "此设备id不存在，id为"+serialnumid);		
				out.print(json);
				out.close();				
				return ;
			}
			
			SerialnumberFee serialnumberFee = new SerialnumberFee();
			serialnumberFee.setFfeeid(UUID.randomUUID().toString());
			serialnumberFee.setFuserid(usrid);
			serialnumberFee.setFtitle("支付宝充值");
			
			HashMap<String, String> mapSerial = new HashMap<String, String>();
			mapSerial.put("FSNID", serialnumid);
			//获得余额
			java.math.BigDecimal balanceDecimal = serialNumService.GetSerialnumberFeeBalance(mapSerial);
				
			serialnumberFee.setFfeetype(1);
			//设置是否支付状态
			serialnumberFee.setFdatastatus(1);
			serialnumberFee.setFsnid(serialnumid);			
			java.math.BigDecimal feedecimal = new java.math.BigDecimal("0.0");
			serialnumberFee.setFdeposit(feedecimal);
			balanceDecimal = balanceDecimal.add(feedecimal);
		
			serialnumberFee.setFbalance(balanceDecimal);
			serialnumberFee.setFremark("获取到支付宝订单号,凭此订单号进行支付");
			serialNumService.AddSerialnumberFee(serialnumberFee);				
			String out_trade_no = serialnumberFee.getFfeeid();
						
			json.put("state", 1);
			json.put("info", "获取支付订单号");
			json.put("out_trade_no",out_trade_no);
		
		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());
			json.put("out_trade_no", "");

		} finally{	
			out.print(json);
			out.close();
		}
	}


}
