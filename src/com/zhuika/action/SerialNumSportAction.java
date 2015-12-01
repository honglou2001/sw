package com.zhuika.action;

import java.io.PrintWriter;
import java.sql.Timestamp;
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

import com.users.ejb.SerialnumberDatarelate;
import com.users.ejb.SerialnumberDatauser;
import com.users.ejb.SerialnumberSport;
import com.users.ejb.SerialnumberWeight;
import com.watch.ejb.Serialnumber;
import com.watch.ejb.UserWatch;
import com.zhuika.service.SerialNumberService;
import com.zhuika.service.SerialNumberSportService;
import com.zhuika.service.UserService;
import com.zhuika.util.ByteConverter;
import com.zhuika.util.Jsonconf;
import com.zhuika.util.Tools;


public class SerialNumSportAction extends BaseAction implements ServletResponseAware,ServletRequestAware {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	@Resource
	private UserService userService;
	@Resource
	private SerialNumberService serialNumService;
	@Resource
	private SerialNumberSportService serialNumberSportSerivce;
	
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		
	}
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
		
	}
	
	/**
	 * 新增
	 */
	public void sportAdd() {

		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();

			String serialnumid = request.getParameter("serialnumid");
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
			
			Serialnumber serialnumberCheck = null;
	
			if(serialnumid!=null && !serialnumid.equals("")){
		        HashMap<String, String> map = new HashMap<String, String>();
		        map.put("a.funiqueid", serialnumid);
		        
				List<Serialnumber>  listSerialnumber= this.serialNumService.ListSerialnumberAll(0, 1,map);
		        if(listSerialnumber!=null && listSerialnumber.size()>0)
		        {
		        	serialnumberCheck = listSerialnumber.get(0);
		        }	        
			}
			
			if(serialnumid ==null || serialnumberCheck == null)
			{								
				json.put("state", 3);
				json.put("info", "新增失败，此设备序列号不存在");
				json.put("data", null);				
				out.print(json);
				out.close();				
				return;
			}
			
	        
//			if(serialnumberCheck.getFusrid()!=null && !serialnumberCheck.getFusrid().equals("") && !serialnumberCheck.getFusrid().equals(usrid))
//			{								
//				json.put("state", 4);
//				json.put("info", "新增失败，此序列号不属于此用户账号");
//				json.put("data", null);				
//				out.print(json);
//				out.close();				
//				return;
//			}
						

			String fsportdate = request.getParameter("fsportdate");
			String fstep = request.getParameter("fstep");
			String fdistance = request.getParameter("fdistance");
			String fcal = request.getParameter("fcal");
			String fheartrate = request.getParameter("fheartrate");
			String fheartstr = request.getParameter("fheartstr");
//			String fdatatousrid = request.getParameter("fdatatousrid");
			String fremark = request.getParameter("fremark");
			
			SerialnumberSport serialnumberSportInfo = new SerialnumberSport();
			
			serialnumberSportInfo.setFuserid(usrid);
			serialnumberSportInfo.setFsnid(serialnumid);
			
			if(fsportdate!=null &&!fsportdate.equals("")){
				serialnumberSportInfo.setFsportdate(fsportdate);
			}
			
			if(fstep!=null &&!fstep.equals("")){
				serialnumberSportInfo.setFstep(Integer.parseInt(fstep));
			}
			
			if(fdistance!=null &&!fdistance.equals("")){
				serialnumberSportInfo.setFdistance(Integer.parseInt(fdistance));
			}
			
			if(fcal!=null &&!fcal.equals("")){
				serialnumberSportInfo.setFcal(Integer.parseInt(fcal));
			}
			
			serialnumberSportInfo.setFheartrate(Integer.parseInt(fheartrate));
			serialnumberSportInfo.setFheartstr(fheartstr);	
			
//			serialnumberSportInfo.setFdatatousrid(fdatatousrid);
			serialnumberSportInfo.setFremark(fremark);
			
			
			serialNumberSportSerivce.AddSerialnumberSport(serialnumberSportInfo);

			json.put("state", 1);
			json.put("info", "新增设备运动数据");

		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());

		} finally {
			out.print(json);
			out.close();
		}
	}

	
	public void sportQuery() {

		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();

			String serialnumid = request.getParameter("serialnumid");
			String usrid = request.getParameter("usrid");
			String sportdate = request.getParameter("sportdate");
						
			UserWatch user =null;
			if(usrid!=null && usrid.length()>0){
				user = userService.Find(usrid);
			}
			
			if (user == null) {				
				json.put("state", 2);
				json.put("info", "查询失败，此用户不存在");		
				out.print(json);
				out.close();				
				return ;
			}
			
			Serialnumber serialnumberCheck = null;
			if(serialnumid!=null && !serialnumid.equals("")){
				serialnumberCheck = serialNumService.Find(serialnumid);
				if(serialnumberCheck == null)
				{								
					json.put("state", 3);
					json.put("info", "查询失败，此设备序列号不存在");
					json.put("data", null);				
					out.print(json);
					out.close();				
					return;
				}
			}
			
						
			HashMap<String, String> mapSerial = new HashMap<String, String>();
			mapSerial.put("userid", usrid);
			mapSerial.put("serialnumid", serialnumid);
			mapSerial.put("sportdate", sportdate);
			
			List<SerialnumberSport> listSports = this.serialNumberSportSerivce.ListSerialnumberSport(0, 100, mapSerial);

			
			JsonConfig  jconfig = Jsonconf.getCommonJsonConf();
			jconfig.setExcludes(new String[] {"fsportrecid", "fincreaseid", "fsnnumber" ,"fmeterinfo","fval1","fva2","fchar1","fchar2","fdatastatus","ffieldstatus","fsporttime","fbleid","faddtime"});
			

			
			json.put("state", 1);
			json.put("info", "成功查询运动数据");
			
			json.put("user", JSONObject.fromObject(user, Jsonconf.getSertialNumJsonConf(this.request)));
			json.put("Serialnumber", JSONObject.fromObject(serialnumberCheck, Jsonconf.getUserJsonConf(this.request)));
			json.put("sportdata", JSONArray.fromObject(listSports, jconfig));

		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());
			json.put("user",null);
			json.put("Serialnumber", null);
			json.put("sportdata", null);

		} finally {
			out.print(json);
			out.close();
		}
	}
	
	/**
	 * 家庭成员新增
	 */
	public void memberAdd() {

		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();

			String usrid = request.getParameter("usrid");
			
			UserWatch user =null;
			if(usrid!=null && usrid.length()>0){
				user = userService.Find(usrid);
			}
			
			if (user == null) {				
				json.put("state", 2);
				json.put("info", "新增失败，此APP用户不存在");		
				out.print(json);
				out.close();				
				return ;
			}
			
			String realname = Tools.DecodeUtf8String(request.getParameter("realname"));			
	        HashMap<String, String> map = new HashMap<String, String>();
	        map.put("realname", realname);
	        map.put("fappcountid", usrid);
	        map.put("fisdeleted", "0");
	        
			List<SerialnumberDatauser>  listdbUser= this.serialNumberSportSerivce.ListSerialnumberDatauser(0, 10, map);
			SerialnumberDatauser dbuser = null;
	        if(listdbUser!=null && listdbUser.size()>0)
	        {
	        	dbuser = listdbUser.get(0);
	        	json.put("state", 3);
				json.put("info", "新增失败，用户下的家庭成员此真实姓名已经存在,真实姓名："+realname);		
				out.print(json);
				out.close();				
				return ;
	        }
	        
			String birthday = request.getParameter("birthday");
			String phone = request.getParameter("phone");
			String weight = request.getParameter("weight");
			String height = request.getParameter("height");		
			String sex = request.getParameter("sex");
			String address = Tools.DecodeUtf8String(request.getParameter("address"));
			String nickname = Tools.DecodeUtf8String(request.getParameter("nickname"));
			String callname = Tools.DecodeUtf8String(request.getParameter("callname"));
			
			String email = Tools.DecodeUtf8String(request.getParameter("email"));
			String relation =Tools.DecodeUtf8String(request.getParameter("relation"));
			String fremark = Tools.DecodeUtf8String(request.getParameter("fremark"));
			
			dbuser = new SerialnumberDatauser();
			dbuser.setBirthday(birthday);
			dbuser.setCallname(callname);
			dbuser.setFaddress(address);
			dbuser.setFappcountid(usrid);
			dbuser.setFemail(email);
			dbuser.setHeight(height);
			dbuser.setFremark(fremark);
			dbuser.setNickname(nickname);
			dbuser.setPhone(phone);
			dbuser.setRealname(realname);
			dbuser.setWeight(weight);
			dbuser.setRelation(relation);
			dbuser.setSex(sex);
			
			serialNumberSportSerivce.AddSerialnumberDatauser(dbuser);

			json.put("state", 1);
			json.put("info", "成功新建家庭成员");
			
			
			JsonConfig  jconfig = Jsonconf.getUserJsonConf(request);
			jconfig.setExcludes(new String[] {"fincreaseid","createtime", "fmobile", "picture", "flogcount" ,"floglasttime"
					,"floglaspip","fienabled","fdatastatus","fdeletetime","flockstatus"
					,"faddtime","furl","address","fpassword"});
			
			json.put("data", JSONObject.fromObject(dbuser, jconfig));

		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());

		} finally {
			out.print(json);
			out.close();
		}
	}
	
	/**
	 * 家庭成员新增
	 */
	public void memberQuery() {

		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();

			String usrid = request.getParameter("usrid");
			
			UserWatch user =null;
			if(usrid!=null && usrid.length()>0){
				user = userService.Find(usrid);
			}
			
			if (user == null) {				
				json.put("state", 2);
				json.put("info", "新增失败，此APP用户不存在");		
				out.print(json);
				out.close();				
				return ;
			}
					
	        HashMap<String, String> map = new HashMap<String, String>();
	        map.put("fappcountid", usrid);
	        map.put("fisdeleted", "0");
	        
			List<SerialnumberDatauser>  listdbUser= this.serialNumberSportSerivce.ListSerialnumberDatauser(0, 1000, map);						
			json.put("state", 1);
			json.put("info", "成功查询家庭成员");
			
			JsonConfig  jconfig = Jsonconf.getUserJsonConf(request);
			jconfig.setExcludes(new String[] {"fincreaseid","createtime", "fmobile", "picture", "flogcount" ,"floglasttime"
					,"floglaspip","fienabled","fdatastatus","fdeletetime","flockstatus"
					,"faddtime","furl","address","fpassword"});
			
			json.put("data", JSONArray.fromObject(listdbUser, jconfig));

		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());

		} finally {
			out.print(json);
			out.close();
		}
	}
	
	public void memberDelete() {

		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();

			String usrid = request.getParameter("usrid");
			String fsndusrid = request.getParameter("fsndusrid");
			
			
			UserWatch user =null;
			if(usrid!=null && usrid.length()>0){
				user = userService.Find(usrid);
			}
			
			if (user == null) {				
				json.put("state", 2);
				json.put("info", "删除失败，此APP用户不存在");		
				out.print(json);
				out.close();				
				return ;
			}
			
			SerialnumberDatauser dbuser = this.serialNumberSportSerivce.findSerialnumberDatauser(fsndusrid);
		    if(dbuser==null)
	        {
	        	json.put("state", 3);
				json.put("info", "删除失败，找不到此此家庭成员id的数据，fsndusrid："+fsndusrid);		
				out.print(json);
				out.close();				
				return ;
	        }
		    
		    if(dbuser.getFisdeleted()!=null && dbuser.getFisdeleted() == 1)
	        {
	        	json.put("state", 4);
				json.put("info", "删除失败，此家庭成员已经是删除状态，fsndusrid："+fsndusrid);		
				out.print(json);
				out.close();				
				return ;
	        }
				    	        
		    dbuser.setFisdeleted(1);
		    dbuser.setFdeletetime(ByteConverter.getTimeStamp());
		    
			
			serialNumberSportSerivce.UpdateSerialnumberDatauser(dbuser);

			json.put("state", 1);
			json.put("info", "成功删除此家庭成员");
			
			JsonConfig  jconfig = Jsonconf.getUserJsonConf(request);
			jconfig.setExcludes(new String[] {"fincreaseid","createtime", "fmobile", "picture", "flogcount" ,"floglasttime"
					,"floglaspip","fienabled","fdatastatus","flockstatus"
					,"faddtime","furl","address","fpassword"});
			
			json.put("data", JSONObject.fromObject(dbuser, jconfig));

		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());

		} finally {
			out.print(json);
			out.close();
		}
	}
	
	public void memberUpdate() {

		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();

			String usrid = request.getParameter("usrid");
			String fsndusrid = request.getParameter("fsndusrid");
			
			
			UserWatch user =null;
			if(usrid!=null && usrid.length()>0){
				user = userService.Find(usrid);
			}
			
			if (user == null) {				
				json.put("state", 2);
				json.put("info", "新增失败，此APP用户不存在");		
				out.print(json);
				out.close();				
				return ;
			}
			
			SerialnumberDatauser dbuser = this.serialNumberSportSerivce.findSerialnumberDatauser(fsndusrid);
		    if(dbuser==null)
	        {
	        	json.put("state", 3);
				json.put("info", "修改失败，找不到此此家庭成员id的数据，fsndusrid："+fsndusrid);		
				out.print(json);
				out.close();				
				return ;
	        }
			
			String realname = Tools.DecodeUtf8String(request.getParameter("realname"));		
			
	        HashMap<String, String> map = new HashMap<String, String>();
	        map.put("realname", realname);
	        map.put("fappcountid", usrid);
	        map.put("fisdeleted", "0");
	        map.put("fsndusrid", fsndusrid);
	        map.put("updatetype", "1");
	        
	        
			List<SerialnumberDatauser>  listdbUser= this.serialNumberSportSerivce.ListSerialnumberDatauser(0, 10, map);
		    if(listdbUser!=null && listdbUser.size()>0)
	        {
	        	json.put("state", 4);
				json.put("info", "修改失败，此用户下已存在其他相同的真实名称，realname："+realname);		
				out.print(json);
				out.close();				
				return ;
	        }
	    
	        
			String birthday = request.getParameter("birthday");
			String phone = request.getParameter("phone");
			String weight = request.getParameter("weight");
			String height = request.getParameter("height");	
			String sex = request.getParameter("sex");
			String address = Tools.DecodeUtf8String(request.getParameter("address"));
			String nickname = Tools.DecodeUtf8String(request.getParameter("nickname"));
			String callname = Tools.DecodeUtf8String(request.getParameter("callname"));
			
			String email = Tools.DecodeUtf8String(request.getParameter("email"));
			String relation =Tools.DecodeUtf8String(request.getParameter("relation"));
			String fremark = Tools.DecodeUtf8String(request.getParameter("fremark"));
			
//			dbuser = new SerialnumberDatauser();
			dbuser.setBirthday(birthday);
			dbuser.setCallname(callname);
			dbuser.setFaddress(address);
			dbuser.setFappcountid(usrid);
			dbuser.setFemail(email);
			dbuser.setHeight(height);
			dbuser.setFremark(fremark);
			dbuser.setNickname(nickname);
			dbuser.setPhone(phone);
			dbuser.setRealname(realname);
			dbuser.setWeight(weight);
			dbuser.setRelation(relation);
			dbuser.setSex(sex);			
			
			serialNumberSportSerivce.UpdateSerialnumberDatauser(dbuser);

			json.put("state", 1);
			json.put("info", "成功修改家庭成员");
			
			JsonConfig  jconfig = Jsonconf.getUserJsonConf(request);
			jconfig.setExcludes(new String[] {"fincreaseid","createtime", "fmobile", "picture", "flogcount" ,"floglasttime"
					,"floglaspip","fienabled","fdatastatus","fdeletetime","flockstatus"
					,"faddtime","furl","address","fpassword"});
			
			json.put("data", JSONObject.fromObject(dbuser, jconfig));

		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());

		} finally {
			out.print(json);
			out.close();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void relevanceAdd() {

		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();

			String usrid = request.getParameter("usrid");
			String fsndusrid = request.getParameter("fsndusrid");
			String Serialnumid = request.getParameter("Serialnumid");
			String begintime = request.getParameter("begintime");
			String endtime = request.getParameter("endtime");
			
			UserWatch user =null;
			if(usrid!=null && usrid.length()>0){
				user = userService.Find(usrid);
			}
			
			if (user == null) {				
				json.put("state", 2);
				json.put("info", "关联失败，此APP用户不存在");		
				out.print(json);
				out.close();				
				return ;
			}
			
			SerialnumberDatauser dbuser = this.serialNumberSportSerivce.findSerialnumberDatauser(fsndusrid);
		    if(dbuser==null)
	        {
	        	json.put("state", 3);
				json.put("info", "关联失败，找不到此此家庭成员id的数据，fsndusrid："+fsndusrid);		
				out.print(json);
				out.close();				
				return ;
	        }
		    
			HashMap<String, String> mapSerial = new HashMap<String, String>();
			mapSerial.put("user.funiqueid",usrid);
			mapSerial.put("a.funiqueid",Serialnumid);
			List<Serialnumber> listSerialNumber = this.serialNumService.GetAll(0, 100, mapSerial);
		    if(listSerialNumber==null || listSerialNumber.size()==0)
	        {
	        	json.put("state", 4);
				json.put("info", "关联失败，找不到此用户与序列号的绑定关系，请确认设备序列号已经注册绑定");		
				out.print(json);
				out.close();				
				return ;
	        }
			
		    SerialnumberDatarelate serialnumberDatarelateInfo = new SerialnumberDatarelate();
		    serialnumberDatarelateInfo.setFsndusrid(fsndusrid);
		    serialnumberDatarelateInfo.setFappcountid(usrid);
		    serialnumberDatarelateInfo.setFuniqueid(Serialnumid);
		    
		    //日期格式要求为2011-06-01 12:49:45
		    if(begintime!=null && begintime.length()>9){
		    	Timestamp dtbegin = ByteConverter.getTimeStamp(begintime);
		    	
		    	if(dtbegin.getYear()+1900>2000)
		    	{
		    		serialnumberDatarelateInfo.setFstarttime(dtbegin);
		    	}
		    }
		    
		    if(endtime!=null && endtime.length()>9){
		    	Timestamp dtend = ByteConverter.getTimeStamp(endtime);
		    	
		    	if(dtend.getYear()+1900>2000)
		    	{
		    		serialnumberDatarelateInfo.setFendtime(dtend);
		    	}
		    }		    
				
	        HashMap<String, String> map = new HashMap<String, String>();
	        map.put("a.funiqueid", Serialnumid);
	        	        
			List<SerialnumberDatarelate>  listdbUser= this.serialNumberSportSerivce.ListSerialnumberDatarelate(0, 10, map);
		    if(listdbUser!=null && listdbUser.size()>0)
	        {
	        	json.put("state", 4);
				json.put("info", "关联失败，此序列号已经有了关联的家庭成员，如要重新关联，请先对此序列号解除关联，Serialnumid："+Serialnumid);		
				out.print(json);
				out.close();				
				return ;
	        }
	    	        						
			serialNumberSportSerivce.AddSerialnumberDatarelate(serialnumberDatarelateInfo);

			json.put("state", 1);
			json.put("info", "成功建立序列号与家庭成员的数据关联关系");
			
			JsonConfig  jconfig = Jsonconf.getUserJsonConf(request);
			jconfig.setExcludes(new String[] {"fincreaseid","fisdelete", "fdeletetime"});			
			json.put("data", JSONObject.fromObject(serialnumberDatarelateInfo, jconfig));

		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());

		} finally {
			out.print(json);
			out.close();
		}
	}
	
	
	/**
	 * 解除数据关联关系
	 */
	public void relevanceDel() {

		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();

			String usrid = request.getParameter("usrid");
			String fsndusrid = request.getParameter("fsndusrid");
			String Serialnumid = request.getParameter("Serialnumid");
			
			UserWatch user =null;
			if(usrid!=null && usrid.length()>0){
				user = userService.Find(usrid);
			}
			
			if (user == null) {				
				json.put("state", 2);
				json.put("info", "解除关联失败，此APP用户不存在");		
				out.print(json);
				out.close();				
				return ;
			}
			
			SerialnumberDatauser dbuser = this.serialNumberSportSerivce.findSerialnumberDatauser(fsndusrid);
		    if(dbuser==null)
	        {
	        	json.put("state", 3);
				json.put("info", "解除关联失败，找不到此此家庭成员id的数据，fsndusrid："+fsndusrid);		
				out.print(json);
				out.close();				
				return ;
	        }
					    		    			
	        HashMap<String, String> map = new HashMap<String, String>();
	        map.put("a.funiqueid", Serialnumid);
	        map.put("a.fsndusrid", fsndusrid);
	        map.put("a.fappcountid", usrid);
	        	        
			List<SerialnumberDatarelate>  listdbUser= this.serialNumberSportSerivce.ListSerialnumberDatarelate(0, 10, map);
		    if(listdbUser==null|| listdbUser.size()==0)
	        {
	        	json.put("state", 4);
				json.put("info", "解除关联失败，找不到此用户的家庭成员与此序列号的关联信息");		
				out.print(json);
				out.close();				
				return ;
	        }
		    
		    SerialnumberDatarelate relInfo = listdbUser.get(0);	    	        						
			serialNumberSportSerivce.DeleteSerialnumberDatarelate(relInfo.getFdatarelateid());

			json.put("state", 1);
			json.put("info", "成功解除此用户的家庭成员与此序列号的关联关系");
			
			JsonConfig  jconfig = Jsonconf.getUserJsonConf(request);
			jconfig.setExcludes(new String[] {"fincreaseid","fisdelete", "fdeletetime"});			
			json.put("data", JSONObject.fromObject(relInfo, jconfig));

		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());

		} finally {
			out.print(json);
			out.close();
		}
	}
	
	/**
	 * 增加称重数据
	 */
	public void weightAdd() {

		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();

			String usrid = request.getParameter("usrid");
			String Serialnumid = request.getParameter("Serialnumid");
			String fweight = request.getParameter("weight");
			String fheight = request.getParameter("height");
			String fbmi = request.getParameter("bmi");
			String fcalorie = request.getParameter("calorie");
			String ffatcontent = request.getParameter("fatcontent");
			String fbonecontent = request.getParameter("bonecontent");
			String fmusclecontent = request.getParameter("musclecontent");
			String fwatercontent = request.getParameter("watercontent");
			String fvisceralfatcontent = request.getParameter("visceralfatcontent");
			String fremark = Tools.DecodeUtf8String(request.getParameter("fremark"));
			
			UserWatch user =null;
			if(usrid!=null && usrid.length()>0){
				user = userService.Find(usrid);
			}
			
			if (user == null) {				
				json.put("state", 2);
				json.put("info", "新增重量数据失败，此APP用户不存在");		
				out.print(json);
				out.close();				
				return ;
			}
			
			HashMap<String, String> mapSerial = new HashMap<String, String>();
			mapSerial.put("user.funiqueid",usrid);
			mapSerial.put("a.funiqueid",Serialnumid);
			List<Serialnumber> listSerialNumber = this.serialNumService.GetAll(0, 100, mapSerial);
		    if(listSerialNumber==null || listSerialNumber.size()==0)
	        {
	        	json.put("state", 3);
				json.put("info", "新增重量数据失败，找不到此用户与序列号的绑定关系，请确认设备序列号已经注册绑定");		
				out.print(json);
				out.close();				
				return ;
	        }
			
		    SerialnumberWeight snWeight = new SerialnumberWeight();	 
		    snWeight.setFappcountid(usrid);
		    snWeight.setFserialnumid(Serialnumid);
		    
	        HashMap<String, String> map = new HashMap<String, String>();
	        map.put("a.funiqueid", Serialnumid);
	        map.put("a.fappcountid", usrid);
	        	        
			List<SerialnumberDatarelate>  listdbUser= this.serialNumberSportSerivce.ListSerialnumberDatarelate(0, 10, map);
			SerialnumberDatarelate relInfo  = null;
			if(listdbUser!=null && listdbUser.size()>0)
	        {
				relInfo = listdbUser.get(0);
	        }
			
			if(relInfo!=null)
			{
				if(relInfo.getFsndusrid()!=null && !relInfo.getFsndusrid().equals(""))
				{
					if(relInfo.getFstarttime() == null || relInfo.getFendtime() ==null){
						snWeight.setFsndusrid(relInfo.getFsndusrid());
					}
					if(relInfo.getFstarttime() != null && relInfo.getFendtime() !=null){
						Timestamp nowdate1 = ByteConverter.getTimeStamp();
						
						if(nowdate1.after(relInfo.getFstarttime()) && nowdate1.before(relInfo.getFendtime()))
						{
							snWeight.setFsndusrid(relInfo.getFsndusrid());
						}
					}
				}
			}

			
			if(fweight!=null && !fweight.equals("")){
				snWeight.setFweight(new java.math.BigDecimal(fweight));
			}
			if(fheight!=null && !fheight.equals("")){
				snWeight.setFheight(new java.math.BigDecimal(fheight));
			}
			if(fbmi!=null && !fbmi.equals("")){
				snWeight.setFbmi(new java.math.BigDecimal(fbmi));
			}
			if(fcalorie!=null && !fcalorie.equals("")){
				snWeight.setFcalorie(new java.math.BigDecimal(fcalorie));
			}
			if(ffatcontent!=null && !ffatcontent.equals("")){
				snWeight.setFfatcontent(new java.math.BigDecimal(ffatcontent));
			}
			if(fbonecontent!=null && !fbonecontent.equals("")){
				snWeight.setFbonecontent(new java.math.BigDecimal(fbonecontent));
			}
		        	
			if(fmusclecontent!=null && !fmusclecontent.equals("")){
				snWeight.setFmusclecontent(new java.math.BigDecimal(fmusclecontent));
			}
			if(fwatercontent!=null && !fwatercontent.equals("")){
				snWeight.setFwatercontent(new java.math.BigDecimal(fwatercontent));
			}
			if(fvisceralfatcontent!=null && !fvisceralfatcontent.equals("")){
				snWeight.setFvisceralfatcontent(new java.math.BigDecimal(fvisceralfatcontent));
			}
			snWeight.setFremark(fremark);
			serialNumberSportSerivce.AddSerialnumberWeight(snWeight);

			json.put("state", 1);
			json.put("info", "成功增加体重数据。");
			
			JsonConfig  jconfig = Jsonconf.getUserJsonConf(request);
			jconfig.setExcludes(new String[] {"fincreaseid","FAddTime", "FDataStatus"});			
			json.put("data", JSONObject.fromObject(snWeight, jconfig));

		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());

		} finally {
			out.print(json);
			out.close();
		}
	}
	
	/**
	 * 查询称重数据
	 */
	public void weightQuery() {

		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();

			String usrid = request.getParameter("usrid");
			String Serialnumid = request.getParameter("Serialnumid");
			
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
				
				out.print(json);
				out.close();				
				return;
			}
						
			UserWatch user =null;
			if(usrid!=null && usrid.length()>0){
				user = userService.Find(usrid);
			}
			
			if (user == null) {				
				json.put("state", 3);
				json.put("info", "查询重量数据失败，此APP用户不存在");		
				out.print(json);
				out.close();				
				return ;
			}
			
			Serialnumber serialnumber =null;
			if(Serialnumid!=null && Serialnumid.length()>0){
				serialnumber = serialNumService.Find(Serialnumid);
			}
			if (serialnumber == null) {				
				json.put("state", 4);
				json.put("info", "此设备id不存在，id为"+Serialnumid);		
				out.print(json);
				out.close();				
				return ;
			}
			
			HashMap<String, String> mapSerial = new HashMap<String, String>();
			mapSerial.put("user.funiqueid",usrid);
			mapSerial.put("a.funiqueid",Serialnumid);
			List<Serialnumber> listSerialNumber = this.serialNumService.GetAll(0, 100, mapSerial);
		    if(listSerialNumber==null || listSerialNumber.size()==0)
	        {
	        	json.put("state", 5);
				json.put("info", "查询重量数据失败，找不到此用户与序列号的绑定关系，请确认设备序列号已经注册绑定");		
				out.print(json);
				out.close();				
				return ;
	        }
			
		    SerialnumberWeight snWeight = new SerialnumberWeight();	 
		    
		    //pagesize 每次显示多少条数据；pageindex 显示第几页，0为第一页；排序为记录新增时间倒序；		    		    
	        HashMap<String, String> map = new HashMap<String, String>();
	        map.put("FSerialnumid", Serialnumid);
	        map.put("FAppcountid", usrid);	        	        
			List<SerialnumberWeight>  listdbWeight= this.serialNumberSportSerivce.ListSerialnumberWeight(npIndex,npSize, map);
			
			json.put("state", 1);
			json.put("info", "成功查询体重数据。");			
			JsonConfig  jconfig = Jsonconf.getUserJsonConf(request);
			
			json.put("user", JSONObject.fromObject(user, jconfig));
			json.put("serialnumber", JSONObject.fromObject(serialnumber, Jsonconf.getSerialnumJsonConf(request)));						
			
			jconfig.setExcludes(new String[] {"fincreaseid","FAddTime", "FDataStatus"});
			json.put("data", JSONArray.fromObject(listdbWeight, jconfig));

		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());

		} finally {
			out.print(json);
			out.close();
		}
	}
}
