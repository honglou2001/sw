package com.zhuika.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.users.ejb.SerialnumberDetach;
import com.users.ejb.SerialnumberFee;
import com.watch.ejb.Serialnumber;
import com.watch.ejb.SerialnumberFeepacket;
import com.watch.ejb.SerialnumberParam;
import com.watch.ejb.UserWatch;
import com.zhuika.service.SerialNumberService;
import com.zhuika.service.UserService;
import com.zhuika.service.impl.UserServiceImpl;
import com.zhuika.util.ByteConverter;
import com.zhuika.util.Jsonconf;
import com.zhuika.util.Tools;

public class SerialNumAction extends BaseAction implements ServletResponseAware,
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

	/**
	 * 注册
	 */
	public void serialNumAdd() {

		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();

			String serialNumber = request.getParameter("serialNumber");
			String usrid = request.getParameter("usrid");
			
			UserWatch user =null;
			if(usrid!=null && usrid.length()>0){
				user = userService.Find(usrid);
			}
			
			if (user == null) {				
				json.put("state", 2);
				json.put("info", "新增失败，此用户不存在");		
				out.print(json);
				out.close();				
				return ;
			}
			
			Serialnumber serialnumberCheck = serialNumService.findBySNNumber(serialNumber);
			if(serialnumberCheck != null)
			{								
				json.put("state", 3);
				json.put("info", "新增失败，此序列号已经存在");
				json.put("data", null);				
				out.print(json);
				out.close();				
				return;
			}
						

			String sex = request.getParameter("sex");
			String nickname = Tools.DecodeUtf8String(request.getParameter("nickname"));
			String birthday = request.getParameter("birthday");
			String height = request.getParameter("height");
			String weight = request.getParameter("weight");
			// String picture=request.getParameter("picture");
			String femail = request.getParameter("femail");
			String devtype = request.getParameter("devtype");
			
			
			String fschool = Tools.DecodeUtf8String(request.getParameter("fschool"));
			String fgrade = Tools.DecodeUtf8String(request.getParameter("fgrade"));
			String fcallname = Tools.DecodeUtf8String(request.getParameter("fcallname"));
			String fremark = Tools.DecodeUtf8String(request.getParameter("fremark"));

			Serialnumber serialnumber = new Serialnumber();
			serialnumber.setSerialnumber(serialNumber);
			serialnumber.setNickname(nickname);
			serialnumber.setBirthday(birthday);
			serialnumber.setFsex(Integer.parseInt(sex));
			serialnumber.setFweight(weight);
			serialnumber.setFeight(height);
			serialnumber.setFgrade(fgrade);
			serialnumber.setFschool(fschool);
			serialnumber.setFcallname(fcallname);	
			serialnumber.setFremark(fremark);
			
			if(devtype!=null && !devtype.equals("")){				
				serialnumber.setFdevtype(Integer.parseInt(devtype));				
			}
			
			serialnumber.setEf("3");

			String uniqueid = UUID.randomUUID().toString();
			serialnumber.setFuniqueid(uniqueid);

			serialNumService.Add(serialnumber);
			
			UserServiceImpl userImpl = new UserServiceImpl();
			userImpl.AddToRelated(uniqueid, usrid);

			json.put("state", 1);
			json.put("info", "新增序列号成功");
			json.put("serialnumid", uniqueid);
		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());
			json.put("serialnumid", "");
		} finally {
			out.print(json);
			out.close();
		}
	}
	
	public void numUsrRelation() {

		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();

			String serialid = request.getParameter("serialnumid");
			String usrid = request.getParameter("usrid");
			
			Serialnumber serialnumber = this.serialNumService.Find(serialid);
			if(serialnumber!=null)
			{
				if(serialnumber.getIsreg().equals("1"))
				{
					json.put("state", 2);
					json.put("info", "关联失败，此序列号已经注册，已绑定到用户");
					json.put("serialnumid", serialid);
					json.put("usrid", usrid);
				}
				else{
					serialnumber.setIsreg("1");				
					this.serialNumService.Update(serialnumber);				 
					UserServiceImpl userImpl = new UserServiceImpl();
					userImpl.AddToRelated(serialid, usrid);					
					json.put("state", 1);
					json.put("info", "成功关联序列号及用户");
					json.put("serialnumid", serialid);
					json.put("usrid", usrid);
				}
				
			}
			else
			{
				json.put("state", 3);
				json.put("info", "查询不到此序列号信息");
				json.put("serialnumid", serialid);
				json.put("usrid", usrid);
			}
			
		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());
			json.put("serialnumid", "");
		} finally {
			out.print(json);
			out.close();
		}
	}
	
	public void numUsrRelationUnreg() {

		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();

			String serialid = request.getParameter("serialnumid");
			String usrid = request.getParameter("usrid");
			
			UserWatch user = userService.Find(usrid);
			
			if (user == null) {				
				json.put("state", 2);
				json.put("info", "取消关联失败，此用户不存在");		
				out.print(json);
				out.close();				
				return ;
			}
						
			Serialnumber serialnumber = this.serialNumService.Find(serialid);
			if(serialnumber!=null)
			{
				serialnumber.setIsreg("0");				
				this.serialNumService.Update(serialnumber);				 
				UserServiceImpl userImpl = new UserServiceImpl();					
				int i = userImpl.RemoveUsrRelation(serialid, usrid);	
				
				int result = 1;
				String info = "成功取消序列号与用户绑定信息,序列号："+serialnumber.getSerialnumber()+",用户昵称："+user.getNickname();
				
				if(i==0)
				{
					result =4;
					info = "解除绑定出现异常，找不到已有的绑定信息，可能已经取消绑定。";
				}
				
				json.put("state", result);
				json.put("info",info);
				json.put("serialnumid", serialid);
				json.put("usrid", usrid);							
			}
			else
			{
				json.put("state", 5);
				json.put("info", "查询不到此序列号信息");
				json.put("serialnumid", serialid);
				json.put("usrid", usrid);
			}
			
		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());
			json.put("serialnumid", "");
		} finally {
			out.print(json);
			out.close();
		}
	}
	
	
	public void getSerialNum()
	{
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();

			String serialnum = request.getParameter("serialNumber");			
			Serialnumber serialnumber = serialNumService.findBySNNumber(serialnum);
			if(serialnumber == null)
			{								
				json.put("state", 2);
				json.put("info", "找不到此序列号的信息");
				json.put("data", null);
				
				out.print(json);
				out.close();
				
				return;
			}

			
			JsonConfig jsonConfig = Jsonconf.getSertialNumJsonConf(request);
			
			json.put("state", 1);
			json.put("info", "查询得到序列号信息");
			json.put("data", JSONObject.fromObject(serialnumber, jsonConfig));
			//json.put("data", serialNumber);
			
			
		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());
			json.put("data", null);
		} finally {
			out.print(json);
			out.close();
		}
	}
	/**
	 * 注册
	 */
	public void serialNumUpdate() {

		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();

			String serialnumid = request.getParameter("serialnumid");
			
			Serialnumber serialnumber = serialNumService.Find(serialnumid);
			if(serialnumber == null)
			{
				json.put("state", 2);
				json.put("info", "找不到此序列号的信息");
				json.put("serialnumid", serialnumid);
				
				out.print(json);
				out.close();
				
				return;
			}

//			String usrid = request.getParameter("usrid");
			String sex = request.getParameter("sex");
			String nickname = Tools.DecodeUtf8String(request.getParameter("nickname"));
			String birthday = request.getParameter("birthday");
			String height = request.getParameter("height");
			String weight = request.getParameter("weight");
			// String picture=request.getParameter("picture");
//			String femail = request.getParameter("femail");
			
			String fschool = Tools.DecodeUtf8String(request.getParameter("fschool"));
			String fgrade = Tools.DecodeUtf8String(request.getParameter("fgrade"));
			String fcallname = Tools.DecodeUtf8String(request.getParameter("fcallname"));
			String fremark = Tools.DecodeUtf8String(request.getParameter("fremark"));

			
			serialnumber.setNickname(nickname);
			serialnumber.setBirthday(birthday);
			serialnumber.setFsex(Integer.parseInt(sex));
			serialnumber.setFweight(weight);
			serialnumber.setFeight(height);
			serialnumber.setFgrade(fgrade);
			serialnumber.setFschool(fschool);
			serialnumber.setFcallname(fcallname);	
			serialnumber.setFremark(fremark);


			serialNumService.Update(serialnumber);			

			json.put("state", 1);
			json.put("info", "成功修改此序列号信息");
			json.put("data", serialnumber);
		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());
			json.put("data", null);
		} finally {
			out.print(json);
			out.close();
		}
	}
	
	
	public void setPhoneNumber() {
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();

			String serialnumid = request.getParameter("serialnumid");
			String fpwd = request.getParameter("fpwd");
			
			if(fpwd==null||!fpwd.equals("123"))
			{
				json.put("state", 3);
				json.put("info", "操作密码为空或不正确");
				json.put("serialnumid", serialnumid);
				
				out.print(json);
				out.close();
				
				return;
			}
			
			Serialnumber serialnumber = serialNumService.Find(serialnumid);
			if(serialnumber == null)
			{
				json.put("state", 2);
				json.put("info", "找不到此序列号的信息");
				json.put("serialnumid", serialnumid);
				
				out.print(json);
				out.close();
				
				return;
			}

			String phonenum = request.getParameter("phonenum");
			serialnumber.setFphonenum(phonenum);
			serialnumber.setFphonetime(ByteConverter.getTimeStamp());
			
			serialNumService.Update(serialnumber);			
			json.put("state", 1);
			json.put("info", "成功记录手表对应的电话号码");
			json.put("data", JSONArray.fromObject(serialnumber, Jsonconf.getCommonJsonConf()));
		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());
			json.put("data", null);
		} finally {
			out.print(json);
			out.close();
		}
	}
	public void serialNumAction() {

		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();

			String serialnumid = request.getParameter("serialnumid");

			
			Serialnumber serialnumber =null;
			if(serialnumid!=null && serialnumid.length()>0){
				serialnumber = serialNumService.Find(serialnumid);
			}
			
			int state = 1;
			String info = "发送成功";
			
			if(serialnumber.getOnline()==null || !serialnumber.getOnline().equals("1"))
			{
				state = 2;
				info = "检测到设备不在线，发送可能失败";
			}

			
			HashMap<String, String> mapSerial = Tools.getUrlParams(request);			
			serialNumService.updateSerialNumberAction(serialnumber,mapSerial);

			json.put("state", state);
			json.put("info",info);
			json.put("serialNumber", serialnumber.getSerialnumber());
		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());
			json.put("serialNumber", "");
		} finally {
			out.print(json);
			out.close();
		}
	}
	
	public void searchSerialNumber() {

		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			List<Serialnumber> listSerialNumber = null;
			
			String phonenum = request.getParameter("phonenum");

			if(phonenum!=null&&phonenum.length()>0){
				
				HashMap<String, String> mapSerial = new HashMap<String, String>();
				mapSerial.put("sn.fphonenum", phonenum);					
				listSerialNumber = this.serialNumService.GetAll(0, 100, mapSerial);
				
				if(listSerialNumber!=null&&listSerialNumber.size()>0){
				
					json.put("state", 1);
					json.put("info", "查询到手表信息");
				
				}else
				{
					json.put("state", 3);
					json.put("info", "无此号码相关信息");
				}
				
				JsonConfig jconfig = Jsonconf.getSerialnumJsonConf(request);
				jconfig.setExcludes(new String[] { "id" });
				json.put("SerialNumber", JSONArray.fromObject(listSerialNumber, jconfig));
				
			}
			else
			{
				json.put("state", 2);
				json.put("info", "参数不允许为空");
			}
			
		
		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());
			json.put("serialNumber", "");
		} finally {
			out.print(json);
			out.close();
		}
	}

	public void searchSerialParm() {
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();

			String serialnumid = request.getParameter("serialnumid");
			String category = request.getParameter("category");
			
			if(category==null||serialnumid==null)
			{
				json.put("state", 2);
				json.put("info", "参数不能为空");
				out.print(json);
				out.close();
				return ;
			}
			
			HashMap<String, String> map = new HashMap<String, String>();	
			map.put("FSnID",serialnumid);
			map.put("FCategory", category);

			
			List<SerialnumberParam> lists = serialNumService.ListSerialnumberParam(0, 100, map);
			
			
			JsonConfig  jconfig = Jsonconf.getCommonJsonConf();
			
			Jsonconf.setParmJsonMapName(jconfig,Integer.parseInt(category));
			
			jconfig.setExcludes(new String[] {"fupdatetime", "fincreaseid", "fisdelete" ,"forder","faddtime","FCode","foperator"});
			
			json.put("state", 1);
			json.put("info", "操作查询");
			json.put("data", JSONArray.fromObject(lists, jconfig));

		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());

		} finally {
			out.print(json);
			out.close();
		}
	}
	
		//查询每月套餐，及账户余额
		public void addPacketAction() {
			PrintWriter out=null;
			JSONObject json=new JSONObject();
			try {
				request.setCharacterEncoding("utf-8");
				response.setContentType("text/json"); 
				response.setCharacterEncoding("utf-8");
				out=response.getWriter();
				
				String usrid= request.getParameter("usrid");
				String serialnumid = request.getParameter("serialnumid");
				String category = request.getParameter("category");
				String fee = request.getParameter("fee");
				String discount = request.getParameter("discount");
				String title = request.getParameter("title");
				
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
				
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("FUserID", usrid);
				map.put("FSnID", serialnumid);
				map.put("FCategory", category);
				
				List<SerialnumberFeepacket> lists = this.serialNumService.ListSerialnumberFeepacket(0, 100, map);
				if(lists!=null &&lists.size()>0)
				{					
					json.put("state", 4);
					json.put("info", "此设备id的此类型套餐已经存在,serialnumid="+serialnumid);		
					out.print(json);
					out.close();				
					return ;
				}
				
				
				SerialnumberFeepacket serialnumberFeepacketInfo = new SerialnumberFeepacket();
				serialnumberFeepacketInfo.setFuserid(usrid);
				serialnumberFeepacketInfo.setFsnid(serialnumid);
				serialnumberFeepacketInfo.setFtitle(title);
				serialnumberFeepacketInfo.setFcategory(Integer.parseInt(category));
				
				if(discount!=null && !discount.equals("")){
					java.math.BigDecimal discountdecimal = new java.math.BigDecimal(discount);
					serialnumberFeepacketInfo.setFdiscount(discountdecimal);
				}
				
				java.math.BigDecimal feedecimal = new java.math.BigDecimal(fee);
				
				serialnumberFeepacketInfo.setFpacketfee(feedecimal);
						
				this.serialNumService.AddSerialnumberFeepacket(serialnumberFeepacketInfo);	
								
				json.put("state", 1);
				json.put("info", "成功查询套餐及剩余余额");
				
				JsonConfig  jconfig = Jsonconf.getCommonJsonConf();						
				//jconfig.setExcludes(new String[] {"fincreaseid", "fpacketid", "faddtime","foperator","foperatestate","foperatetime","ffieldstatus"});
				
				JSONObject jsonObj = JSONObject.fromObject(serialnumberFeepacketInfo, jconfig);
				
				json.put("data",jsonObj);

			
			} catch (Exception e) {
				json.put("state", -1);
				json.put("info", e.getMessage());
				json.put("data", null);

			} finally{	
				out.print(json);
				out.close();
			}
		}
		
	//查询每月套餐，及账户余额
	public void getPacketAction() {
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
			
			JSONArray feePacket = this.getFeePacketJson(usrid, serialnumid);
			JSONArray feeBalance = this.getFeeBalanceJson(serialnumid);
						
			json.put("state", 1);
			json.put("info", "成功查询套餐及剩余余额");
			json.put("feepacket",feePacket);
			json.put("feebalance", feeBalance);
		
		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());
			json.put("feepacket", null);
			json.put("feebalance", null);
		} finally{	
			out.print(json);
			out.close();
		}
	}
	
	private java.math.BigDecimal getFee(int category)
	{
		if(category == 1)
		{
			return new BigDecimal(50.0);
		}else if(category == 2)
		{
			return new BigDecimal(30.0);
		}
		else if(category == 3)
		{
			return new BigDecimal(2);
		}
		
		return new BigDecimal(0);
			
		
	}
	private JSONArray getFeePacketJson(String usrid,String serialnumid) {
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("FUserID", usrid);
		map.put("FSnID", serialnumid);

	
		
		List<SerialnumberFeepacket> lists = this.serialNumService.ListSerialnumberFeepacket(0, 100, map);
		
		if(lists!=null && lists.size()==0)
		{		
			for(int i=1;i<=3;i++){
				
				String title = "通讯费用";
				if(i==2)
				{
					title = "云之迅平台费用";
				}
				else if(i==3)
				{
					title = "小狗嘟嘟服务费用";
				}
				
				SerialnumberFeepacket serialnumberFeepacketInfo = new SerialnumberFeepacket();
				serialnumberFeepacketInfo.setFuserid(usrid);
				serialnumberFeepacketInfo.setFsnid(serialnumid);
				serialnumberFeepacketInfo.setFtitle(title);
				
				java.math.BigDecimal feedecimal = new java.math.BigDecimal("0.00");
				
				serialnumberFeepacketInfo.setFpacketfee(this.getFee(i));
				serialnumberFeepacketInfo.setFcategory(i);
						
				this.serialNumService.AddSerialnumberFeepacket(serialnumberFeepacketInfo);	
				
				lists.add(serialnumberFeepacketInfo);
			}			
		}
		
		JsonConfig  jconfig = Jsonconf.getCommonJsonConf();						
		jconfig.setExcludes(new String[] {"fincreaseid", "fpacketid", "faddtime","foperator","foperatestate","foperatetime","ffieldstatus"});
		
		JSONArray arrayObj = JSONArray.fromObject(lists, jconfig);
		return arrayObj;
	}
	
	private JSONArray getFeeBalanceJson(String serialnumid) {
		JSONArray arrayObj = null;
		HashMap<String, String> mapSerial = new HashMap<String, String>();
		mapSerial.put("FSNID", serialnumid);
		
		List<SerialnumberFee> listFee = serialNumService.ListSerialnumberFee(0,1,mapSerial);						
		
		JsonConfig  jconfig = Jsonconf.getCommonJsonConf();						
		jconfig.setExcludes(new String[] {"fincreaseid", "freltable", "frelval" ,"foperator","foperatestate","foperatetime","ffieldstatus"});
		
		arrayObj = JSONArray.fromObject(listFee, jconfig);
		return arrayObj;
	}

	public void rechargeAction() {
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();

			String usrid= request.getParameter("usrid");
			String serialnumid = request.getParameter("serialnumid");
			
			String fpwd = request.getParameter("fpwd");
			String ftype= request.getParameter("ftype");
			String title = Tools.DecodeUtf8String(request.getParameter("title"));
			
			if(usrid!=null && !usrid.equals("")){
			
				UserWatch user = userService.Find(usrid);
				if(user==null)
				{
					
					json.put("state", 5);
					json.put("info", "此用户id的信息不存在");
					json.put("usrid", usrid);
					
					out.print(json);
					out.close();
					
					return;
				}
			
			}
			
			if(fpwd==null||!fpwd.equals("123"))
			{
				json.put("state", 3);
				json.put("info", "操作密码为空或不正确");
				json.put("serialnumid", serialnumid);
				
				out.print(json);
				out.close();
				
				return;
			}
			
			Serialnumber serialnumber = serialNumService.Find(serialnumid);
			if(serialnumber == null)
			{
				json.put("state", 2);
				json.put("info", "找不到此序列号的信息");
				json.put("serialnumid", serialnumid);
				
				out.print(json);
				out.close();
				
				return;
			}
				
			String feeval = request.getParameter("fee");
			String action= request.getParameter("action");

			if(feeval==null||feeval.equals(""))
			{
				json.put("state", 4);
				json.put("info", "费用值为空");
				json.put("serialnumid", serialnumid);
				
				out.print(json);
				out.close();
				
				return;
			}
						
			SerialnumberFee serialnumberFee = new SerialnumberFee();
			serialnumberFee.setFuserid(usrid);
			serialnumberFee.setFtitle(title);
			
			HashMap<String, String> mapSerial = new HashMap<String, String>();
			mapSerial.put("FSNID", serialnumid);
			//获得余额
			java.math.BigDecimal balanceDecimal = serialNumService.GetSerialnumberFeeBalance(mapSerial);
	
			
			if(ftype!=null && ftype.length()>0){
				serialnumberFee.setFfeetype(Integer.parseInt(ftype));
			}
			
			java.math.BigDecimal feedecimal = new java.math.BigDecimal(feeval);
			
			serialnumberFee.setFsnid(serialnumid);
			if(action.equals("1")){
				serialnumberFee.setFdeposit(feedecimal);
				balanceDecimal = balanceDecimal.add(feedecimal);
			}else if(action.equals("2")){
				serialnumberFee.setFspending(feedecimal);
				balanceDecimal = balanceDecimal.subtract(feedecimal);
			}
		
			serialnumberFee.setFbalance(balanceDecimal);
			serialNumService.AddSerialnumberFee(serialnumberFee);	
				
			
			json.put("state", 1);
			json.put("info", "成功充值");
			json.put("data", JSONArray.fromObject(serialnumberFee, Jsonconf.getCommonJsonConf()));
		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());
			json.put("data", null);
		} finally {
			out.print(json);
			out.close();
		}
	}
	public void queryBalance() {
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();

			String serialnumid = request.getParameter("serialnumid");
			String ftype= request.getParameter("ftype");

			HashMap<String, String> mapSerial = new HashMap<String, String>();
			mapSerial.put("FSNID", serialnumid);
			mapSerial.put("ftype", ftype);
			
			BigDecimal balance = new BigDecimal("0");
			//BigDecimal balance = serialNumService.GetSerialnumberFeeBalance(mapSerial);	
			
			List<SerialnumberFee> listFee = serialNumService.ListSerialnumberFee(0,1,mapSerial);	
			if(listFee!=null && listFee.size()>0)
			{
				balance = listFee.get(0).getFbalance();
			}
			
			
			json.put("state", 1);
			json.put("info", "成功查询剩余金额");
			json.put("serialnumid", serialnumid);
			json.put("balance", balance);
		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());
			json.put("serialnumid", null);
			json.put("balance", null);
		} finally {
			out.print(json);
			out.close();
		}
	}
	public void queryFee() {
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();

			String serialnumid = request.getParameter("serialnumid");
			String ftype= request.getParameter("ftype");
			String pagesize = request.getParameter("pagesize");
			String pageindex = request.getParameter("pageindex");
			
			int npIndex = Integer.parseInt(pageindex);
			int npSize = Integer.parseInt(pagesize);
			
			if (npIndex>0){
				npIndex = (npIndex) * npSize;
			}
			
			if(npIndex<0 && npSize<=0)
			{
				json.put("state", 2);
				json.put("info", "页码索引或页码不合法");
				json.put("serialnumid", serialnumid);
				
				out.print(json);
				out.close();				
				return;
			}
			
			HashMap<String, String> mapSerial = new HashMap<String, String>();
			mapSerial.put("FSNID", serialnumid);
			mapSerial.put("ftype", ftype);
			
			List<SerialnumberFee> listFee = serialNumService.ListSerialnumberFee(npIndex,npSize,mapSerial);						
					
			JsonConfig  jconfig = Jsonconf.getCommonJsonConf();						
			jconfig.setExcludes(new String[] {"fincreaseid", "freltable", "frelval" ,"foperator","foperatestate","foperatetime","ffieldstatus"});
			
			json.put("state", 1);
			json.put("info", "操作查询费用信息");
			json.put("pagesize", pagesize);
			json.put("pageindex", pageindex);
			json.put("data", JSONArray.fromObject(listFee, jconfig));
		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());
			json.put("pagesize", null);
			json.put("pageindex", null);
			json.put("data", null);
		} finally {
			out.print(json);
			out.close();
		}
	}
	
	public void queryDetach() {
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();

			String serialnumid = request.getParameter("serialnumid");
			String pagesize = request.getParameter("pagesize");
			String pageindex = request.getParameter("pageindex");
			
			int npIndex = Integer.parseInt(pageindex);
			int npSize = Integer.parseInt(pagesize);
			
			if (npIndex>0){
				npIndex = (npIndex) * npSize;
			}
			
			if(npIndex<0 && npSize<=0)
			{
				json.put("state", 2);
				json.put("info", "页码索引或页码不合法");
				json.put("serialnumid", serialnumid);
				
				out.print(json);
				out.close();				
				return;
			}
			
			Serialnumber serialnumber = serialNumService.Find(serialnumid);
			if(serialnumber == null)
			{
				json.put("state", 2);
				json.put("info", "找不到此序列号的信息");
				json.put("serialnumid", serialnumid);
				
				out.print(json);
				out.close();
				
				return;
			}
			
			HashMap<String, String> mapSerial = new HashMap<String, String>();
			mapSerial.put("FSNID", serialnumber.getSerialnumber());
			
			List<SerialnumberDetach> listDetach = serialNumService.ListSerialnumberDetach(npIndex, npSize, mapSerial);						
					
			JsonConfig  jconfig = Jsonconf.getCommonJsonConf();						
			jconfig.setExcludes(new String[] {"fincreaseid", "ffieldstatus","fsnid","fuserid"});
			
			json.put("state", 1);
			json.put("info", "操作查询手表脱落信息");
			json.put("pagesize", pagesize);
			json.put("pageindex", pageindex);
			json.put("data", JSONArray.fromObject(listDetach, jconfig));
		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());
			json.put("pagesize", null);
			json.put("pageindex", null);
			json.put("data", null);
		} finally {
			out.print(json);
			out.close();
		}
	}
}
