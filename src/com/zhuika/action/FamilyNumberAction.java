package com.zhuika.action;




import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.stereotype.Controller;

import com.RestTest;
import com.common.ejb.EjbCmdData;
import com.users.ejb.SerialnumberApiphone;
import com.users.ejb.SerialnumberFee;
import com.watch.ejb.FriendContact;
import com.watch.ejb.Serialnumber;
import com.watch.ejb.UserWatch;
import com.zhuika.entity.BindPhone;
import com.zhuika.factory.DAOException;
import com.zhuika.service.FamilyNoService;
import com.zhuika.service.SerialNumberService;
import com.zhuika.service.UserService;
import com.zhuika.util.ApiResponse;
import com.zhuika.util.ByteConverter;
import com.zhuika.util.Jsonconf;
import com.zhuika.util.Tools;



@Controller
public class FamilyNumberAction extends BaseAction implements ServletRequestAware,
		ServletResponseAware {

	private final static Logger logger = Logger.getLogger(FamilyNumberAction.class.getName());  
	
	private static final long serialVersionUID = 1L;
    private HttpServletRequest request;
    private HttpServletResponse response;
    @Resource
    private FamilyNoService familyNoService;
	@Resource
	private SerialNumberService serialNumService;
	@Resource
	private UserService userService;
    
    private static final String acountid = "ab9abe2cacfc9a5a2829f2222993018a";
    private static final String token = "91b6bc5e40148c2a74335d2b730b8c2c";
    private static final String appid = "a8c88624ad064474a59225609832c505";
    
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;		
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response=response;	
	}
	public void addFamilyNo(){
    	PrintWriter out=null;
    	JSONObject json=new JSONObject();
    	try {
    		request.setCharacterEncoding("utf-8");
			//response.setContentType("text/html;charset=utf8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");   		
			out=response.getWriter();
			String serialNumber=request.getParameter("serialNumber");
			String masterNo=request.getParameter("masterNo");
			String sos=request.getParameter("sos");
			String listenNo=request.getParameter("listenNo");
			System.out.println(serialNumber);
			System.out.println(masterNo);
			System.out.println(sos);
			System.out.println(listenNo);
			BindPhone bindPhone=familyNoService.getBindPhone(serialNumber);
			System.out.println(bindPhone.toString());				
			bindPhone.setSos(sos);
			bindPhone.setStatus("0");
			bindPhone.setListenNo("listen_num="+listenNo);
			bindPhone.setMasterNo("sos_num1="+masterNo);
			System.out.println(bindPhone.toString());
			familyNoService.updateBindPhone(bindPhone);			
			json.put("state", "1");		
			json.put("info", "成功添加亲情号码");	
		} catch (Exception e) {
			json.put("state", "-1");
			json.put("info", e.getMessage());
		} finally{
			out.print(json);
			out.close();
		}
    }
	
//	private class sosno
//	{
//		private String number;
//
//		public String getNumber() {
//			return number;
//		}
//
//		public void setNumber(String number) {
//			this.number = number;
//		}
//	}
	
	public void searchFamilyNo() throws DAOException{
    	PrintWriter out=null;
    	JSONObject json=new JSONObject();
    	try {
    		request.setCharacterEncoding("utf-8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");
			out=response.getWriter();
			String serialNumber=request.getParameter("serialNumber");
			System.out.println(serialNumber);
			BindPhone bindPhone=familyNoService.getBindPhone(serialNumber)	;			
			if(bindPhone!=null){
				json.put("state", 1);
				json.put("info", "成功查询");
				json.put("masterNo", bindPhone.getMasterNo().substring(bindPhone.getMasterNo().indexOf("=")+1));
				
				JSONArray ja = new JSONArray();

				//json.put("data", ja);
				
//				HashMap<String, String> hashSos = new HashMap<String, String>();
				String sosstr = bindPhone.getSos();
				if(sosstr!=null && sosstr.length()>0)
				{
					String[] strArr= sosstr.split(",");
					if(strArr!=null&&strArr.length>0)
					{
						for(int i=0;i<strArr.length;i++)
						{
							if(strArr[i]!=null && strArr[i].indexOf("=")>0)
							{
								String[] strVal= strArr[i].split("=");								
								// 经度,纬度
								JSONObject json1 = new JSONObject();																
								if(strVal!=null &&strVal.length==2)
								{
									json1.put(strVal[0], strVal[1]);// 
								}
								else if(strVal!=null &&strVal.length==1)
								{
									json1.put(strVal[0], "");//
								}								
								ja.add(json1);									
							}
						}
					}
				}
				
				json.put("sos",ja);
				json.put("listenNo", bindPhone.getListenNo().substring(bindPhone.getListenNo().indexOf("=")+1));		    
			}		
		} catch (Exception e) {			
			json.put("state", -1);
			json.put("info", e.getMessage());
		} finally{
			out.print(json);
			out.close();
		}
    }
	
	public void callBackAPI(){
    	PrintWriter out=null;
    	JSONObject json=new JSONObject();
    	try {
    		request.setCharacterEncoding("utf-8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");   		
			out=response.getWriter();
			String fromNum=request.getParameter("fromNum");
			String toNum=request.getParameter("toNum");
			String type=request.getParameter("type");
			
			String fromNumClient  = "";
			//需提前映射
//			HashMap<String, String> map = new HashMap<String, String>();
//			map.put("18058149508", "68520027654483");
//			map.put("13713975317", "68520027660803");
			
			if(type==null || (!type.equals("") && type.equals("1"))){
				
				HashMap<String, String> mapCondition = new HashMap<String, String>();
				//此处可建立缓存
				List<SerialnumberApiphone> listApi = familyNoService.ListSerialnumberApiphone(0, 10000, mapCondition);
				boolean exist = false;
				for(int i=0;i<listApi.size();i++)
				{
					if(fromNum.equals(listApi.get(i).getFmobile()))
					{
						exist = true;
						fromNumClient = listApi.get(i).getFclientnumber();
						break;
					}
				}
				
				if(!exist){
					json.put("state", "2");		
					json.put("info", "呼叫失败，没有此主叫号码对应的平台号码，主叫号码请使用平台的clientNumber");	
					json.put("data", null);					
					out.print(json);
					out.close();					
					return;
				}
				
					
			}else if(type!=null && !type.equals("") && type.equals("2")){
				fromNumClient = fromNum;	
				
				HashMap<String, String> mapCondition = new HashMap<String, String>();
				//此处可建立缓存
				List<SerialnumberApiphone> listApi = familyNoService.ListSerialnumberApiphone(0, 10000, mapCondition);
				boolean exist = false;
				for(int i=0;i<listApi.size();i++)
				{
					if(fromNum.equals(listApi.get(i).getFclientnumber()))
					{
						exist = true;
						fromNum = listApi.get(i).getFmobile();
						break;
					}
				}
				
				if(!exist){
					fromNum = "";
//					json.put("state", "3");		
//					json.put("info", "呼叫失败，没有此主叫号码对应的平台号码，主叫号码请使用平台的clientNumber");	
//					json.put("data", null);					
//					out.print(json);
//					out.close();					
//					return;
				}
			}
			
			if(fromNumClient!=null && !fromNumClient.equals("")&&toNum!=null && !toNum.equals(""))
				{
					//68520027654483 - 18058149508
					//68520027660803 - 13713975317
					//075561355202
						String result = RestTest.testCallback(true, acountid, token, appid, fromNumClient, toNum,fromNum);					
						
						if(result.indexOf("00000")>0)
						{
							json.put("state", "1");		
							json.put("info", String.format("成功呼叫号码,clientNumber:%s,ToNumber:%s,calledDisplayNum:%s",fromNumClient,toNum,fromNum ));	
							json.put("data", result);
						}
						else
						{
							json.put("state", "4");		
							json.put("info", "呼叫失败，接口返回不成功");	
							json.put("data", result);
						}
				}

		
		} catch (Exception e) {
			json.put("state", "-1");
			json.put("info", e.getMessage());
			json.put("data", null);
		} finally{
			out.print(json);
			out.close();
		}
    }
	
	public void sendSMS(){
    	PrintWriter out=null;
    	JSONObject json=new JSONObject();
    	try {
    		request.setCharacterEncoding("utf-8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");   		
			out=response.getWriter();
			String toNum=request.getParameter("toNum");
			//模板id,ex:13567
			String templateid=request.getParameter("templateid");			
			//msg-根据云之迅参数模块，每个参数用英文逗号,隔开,ex:1001,学校,离开
			String param=Tools.DecodeUtf8String(request.getParameter("param"));
			
			if(toNum!=null && !toNum.equals("") && param!=null && !param.equals("")){
				
				String result = RestTest.testTemplateSMS(true, acountid, token, appid, templateid, toNum, param);
				if(result.indexOf("00000")>0)
				{
					json.put("state", "1");		
					json.put("info", String.format("成功发送短信,目标号码:%s,模板id:%s,发送参数:%s",toNum,templateid,param ));	
					json.put("data", result);
				}
				else
				{
					json.put("state", "3");		
					json.put("info", "发送失败，接口返回不成功");	
					json.put("data", result);
				}				
					
			}
			else
			{
				json.put("state", "2");		
				json.put("info", "发送号码及内容部能为空");	
				json.put("data", null);					
				out.print(json);
				out.close();					
				return;
			}
		
		} catch (Exception e) {
			json.put("state", "-1");
			json.put("info", e.getMessage());
			json.put("data", null);
		} finally{
			out.print(json);
			out.close();
		}
    }
	
	public void createClientNum(){
    	PrintWriter out=null;
    	JSONObject json=new JSONObject();
    	try {
    		request.setCharacterEncoding("utf-8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");   		
			out=response.getWriter();
			String phonenum=request.getParameter("phonenum");			
			//clientType  String  必选  0 开发者计费；1 云平台计费。默认为0。 
			String clientType=request.getParameter("clientType");
			//charge  String  必选  充值金额（开发者计费即ClientType为0时，为可选参数），默认为0。 
			String charge=request.getParameter("charge");
			
			if(clientType==null || clientType.equals(""))
			{
				clientType = "1";
			}
			if(charge==null || charge.equals(""))
			{
				charge = "1";
			}		
			
			if(phonenum!=null && !phonenum.equals(""))
			{													
			     
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("FMobile", phonenum);
							
				List<SerialnumberApiphone> listApi = familyNoService.ListSerialnumberApiphone(0, 1, map);
				
				if(listApi!=null && listApi.size()>0)
				{
					json.put("state", 2);
					json.put("info", "已经存在此手机号码的云平台的ClientNumber,phonenum="+phonenum);
					json.put("data", null);
					
					out.print(json);
					out.close();
					
					return;
				}
				
				String[] args11={"2","json",acountid,token,appid,phonenum,clientType,charge,phonenum,"","",""};
				
				String result =RestTest.testCreateClient(true, args11[2], args11[3], args11[4], args11[5], args11[6], args11[7], args11[8]);
				
				JSONObject jsonObject = JSONObject.fromObject(result);
				String resp = jsonObject.getString("resp");
				JSONObject respObj = JSONObject.fromObject(resp);
				String respCode = respObj.getString("respCode");
				
				if(result.indexOf("00000")>0)
				{
					String clientInfo = respObj.getString("client");
					JSONObject infoObject = JSONObject.fromObject(clientInfo);					
					ApiResponse info=(ApiResponse)JSONObject.toBean(infoObject, ApiResponse.class);
					
					SerialnumberApiphone apiObj = new SerialnumberApiphone();
					apiObj.setFappid(appid);
					apiObj.setFclientnumber(String.valueOf(info.getClientNumber()));
					apiObj.setFclientpwd(info.getClientPwd());
					apiObj.setFbalance(info.getBalance());
					apiObj.setFclienttype(clientType);
					
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//					String str = sdf.format(info.getCreateDate());//时间存储为字符串					
					apiObj.setFcreatedate(Timestamp.valueOf(info.getCreateDate()));
					
					apiObj.setFmobile(phonenum);
					familyNoService.AddSerialnumberApiphone(apiObj);
					
					json.put("state", "1");		
					json.put("info", "成功呼叫创建号码");	
					json.put("respCode", respCode);
					json.put("data", result);
				}
				else
				{
					json.put("state", "2");		
					json.put("info", "创建失败，接口返回不成功");	
					json.put("respCode", respCode);
					json.put("data", result);
				}
			}
			else
			{
				json.put("state", "3");		
				json.put("info", "创建失败，参数不能为空");	
				json.put("data", null);
			}		
		
		} catch (Exception e) {
			json.put("state", "-1");
			json.put("info", e.getMessage());
			json.put("data", null);
		} finally{
			out.print(json);
			out.close();
		}
    }
	
	//检查是否同网
	//是否同网：0:同网   1:不同网,同网直拨，不同网回拨
	private int checknNetwork(String fromNumber, String toNumber)
	{
		boolean checkNetWork = EjbCmdData.checkNetworkSegment(fromNumber, toNumber);
		
		if(checkNetWork==true){
			return 0;
		}else{
			return 1;
		}
	}
	
	public void addContacts(){
    	PrintWriter out=null;
    	JSONObject json=new JSONObject();
    	try {
    		request.setCharacterEncoding("utf-8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");   		
			out=response.getWriter();
			//如 18058149508
			String serialnumid = request.getParameter("serialnumid");
			String usrid = request.getParameter("usrid");
			String contactNum=request.getParameter("phonenum");
			//0:普通号码  1:sos号码  2:监听号 3:主控号  
			String type=request.getParameter("type");
			String contactName = Tools.DecodeUtf8String(request.getParameter("contactName"));
			String fromNumber = "";
			
			int fproperties = Integer.parseInt(type);
			
			Serialnumber serialNumTo = serialNumService.Find(serialnumid);
			if(serialNumTo == null)
			{
				json.put("state", 2);
				json.put("info", "找不到此序列号的信息");
				json.put("serialnumid", serialnumid);
				
				out.print(json);
				out.close();
				
				return;
			}
			
			fromNumber = serialNumTo.getFphonenum();
			if(fromNumber == null||fromNumber.equals(""))
			{
				json.put("state", 3);
				json.put("info", "此序列号的对应的手机号码为空");
				json.put("serialnumid", serialnumid);				
				out.print(json);
				out.close();				
				return;
			}
			
			UserWatch postUser = userService.Find(usrid);
			if(postUser == null)
			{
				json.put("state", 4);
				json.put("info", "找不到此APP用户的信息");
				json.put("usrid", usrid);
				
				out.print(json);
				out.close();
				
				return;
			}
			
			if(contactNum!=null && !contactNum.equals(""))
			{												
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("FToSnID", serialnumid);				
				map.put("FPhoneNum", contactNum);
										
				List<FriendContact> listContact = userService.ListFriendContact(0,500,map);
				if(listContact!=null && listContact.size()>0)
				{
					json.put("state", 5);
					json.put("info", "添加失败，此目标手表已存在此联系电话号码");
					json.put("Serialnumber", null);
					json.put("data", null);
					out.print(json);
					out.close();
					
					return;
				}
				
				int dialtype = this.checknNetwork(fromNumber, contactNum);
				
				//如为sos，固定为直拨
				if(type.equals("1"))
				{
					dialtype = 0;
				}
				
				userService.saveToContact(postUser, contactName, contactNum, fproperties, dialtype, serialNumTo);

				json.put("state", "1");		
				json.put("info", "成功添加联系人");	
				json.put("data", null);
				
			}
			else
			{
				json.put("state", "3");		
				json.put("info", "查询失败，参数不能为空");	
				json.put("data", null);
			}		
		
		} catch (Exception e) {
			json.put("state", "-1");
			json.put("info", e.getMessage());
			json.put("data", null);
		} finally{
			out.print(json);
			out.close();
		}
    }
	
	public void updateContacts(){
    	PrintWriter out=null;
    	JSONObject json=new JSONObject();
    	try {
    		request.setCharacterEncoding("utf-8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");   		
			out=response.getWriter();
			//如 18058149508
			String fcontactid = request.getParameter("fcontactid");
			String usrid = request.getParameter("usrid");
			String contactNum=request.getParameter("phonenum");
			//0:普通号码  1:sos号码  2:监听号 3:主控号  
			String type=request.getParameter("type");
			String contactName = Tools.DecodeUtf8String(request.getParameter("contactName"));
			String action=request.getParameter("action");
			String findex=request.getParameter("findex");
			boolean bAction = false;
			
			if(action!=null)
			{
				if(action.equals("0")||action.equals("1")||action.equals("2"))
				{
					bAction = true;
				}
			}
			
			if(findex!=null)
			{
				if(Integer.parseInt(findex)>100 || Integer.parseInt(findex)<0)
				{
					json.put("state", 6);
					json.put("info", "索引findex参数不合法");
					json.put("fcontactid", fcontactid);
					
					out.print(json);
					out.close();
					
					return;
				}
			}			
				
			int fproperties = Integer.parseInt(type);
			
			FriendContact contactInfo = userService.findFriendContact(fcontactid);
			if(contactInfo == null || bAction==false)
			{
				json.put("state", 3);
				json.put("info", "修改失败，找不到此序通讯录信息，或参数action不合法");
				json.put("fcontactid", fcontactid);
				
				out.print(json);
				out.close();
				
				return;
			}
			
			String serialid = contactInfo.getFtosnid();
			Serialnumber serialNumTo = this.serialNumService.Find(serialid);
			if(serialNumTo==null)
			{
				json.put("state", 4);
				json.put("info", "修改失败，找不到此序通讯录对应的手表序列号信息");
				json.put("fcontactid", fcontactid);				
				out.print(json);
				out.close();
				
				return;
			}
			String fromNumber = serialNumTo.getFphonenum();
			UserWatch postUser = userService.Find(usrid);
			if(postUser == null)
			{
				json.put("state", 5);
				json.put("info", "找不到此APP用户的信息");
				json.put("usrid", usrid);
				
				out.print(json);
				out.close();
				
				return;
			}
			if(contactNum!=null && !contactNum.equals(""))
			{												

				int dialtype = this.checknNetwork(fromNumber, contactNum);
				//如为sos，固定为直拨
				if(type.equals("1"))
				{
					dialtype = 0;
				}
				
				contactInfo.setFdialtype(dialtype);
				contactInfo.setFname(contactName);
				contactInfo.setFphonenum(contactNum);
				contactInfo.setFproperties(fproperties);
				
				if(findex!=null && findex.length()>0){
					contactInfo.setFindex(Integer.parseInt(findex));
				}
				if(action!=null && action.length()>0){
					contactInfo.setFaction(Integer.parseInt(action));
				}				
				
				
				userService.UpdateFriendContact(contactInfo,serialNumTo.getSerialnumber());

				json.put("state", "1");		
				json.put("info", "成功修改通讯录信息");	
				
				JsonConfig  jconfig = Jsonconf.getCommonJsonConf();						
				json.put("data", JSONObject.fromObject(contactInfo, jconfig));
				
			}
			else
			{
				json.put("state", "3");		
				json.put("info", "修改失败，参数不能为空");	
				json.put("data", null);
			}		
		
		} catch (Exception e) {
			json.put("state", "-1");
			json.put("info", e.getMessage());
			json.put("data", null);
		} finally{
			out.print(json);
			out.close();
		}
    }
	
	public void queryContacts(){
    	PrintWriter out=null;
    	JSONObject json=new JSONObject();
    	try {
    		request.setCharacterEncoding("utf-8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");   		
			out=response.getWriter();
			//如 18058149508
			String serialnumid = request.getParameter("serialnumid");
			String contactNum=request.getParameter("phonenum");

			Serialnumber serialNumTo = serialNumService.Find(serialnumid);
			if(serialNumTo == null)
			{
				json.put("state", 2);
				json.put("info", "找不到此序列号的信息");
				json.put("serialnumid", serialnumid);
				
				out.print(json);
				out.close();
				
				return;
			}
																				
			HashMap<String, String> map = new HashMap<String, String>();
			if(serialnumid!=null &&serialnumid.length()>0){
				map.put("FToSnID", serialnumid);
			}
			if(contactNum!=null &&contactNum.length()>0){
				map.put("FPhoneNum", contactNum);
			}
			
			if(map.size()==0)
			{
				json.put("state", 3);
				json.put("info", "查询失败，查询条件为空");
				json.put("Serialnumber", null);
				json.put("data", null);
				out.print(json);
				out.close();
				
				return;
			}
			
			List<FriendContact> listContact = userService.ListFriendContact(0,500,map);
			
			JsonConfig jsonConfig = Jsonconf.getSertialNumJsonConf(request);

			json.put("state", "1");		
			json.put("info", "成功查询手表对应的联系人信息");	
			json.put("Serialnumber", JSONObject.fromObject(serialNumTo, jsonConfig));
			json.put("data", JSONArray.fromObject(listContact, jsonConfig));
				

		} catch (Exception e) {
			json.put("state", "-1");
			json.put("info", e.getMessage());
			json.put("Serialnumber", null);
			json.put("data", null);
		} finally{
			out.print(json);
			out.close();
		}
    }
	
	public void sendContacts(){
    	PrintWriter out=null;
    	JSONObject json=new JSONObject();
    	try {
    		request.setCharacterEncoding("utf-8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");   		
			out=response.getWriter();
			//如 18058149508
			String fcontactid = request.getParameter("fcontactid");
			String action = request.getParameter("action");
			String findex=request.getParameter("findex");
			boolean bAction = false;
			
			if(action!=null)
			{
				if(action.equals("0")||action.equals("1")||action.equals("2"))
				{
					bAction = true;
				}
			}
			if(findex!=null)
			{
				if(Integer.parseInt(findex)>100 || Integer.parseInt(findex)<0)
				{
					json.put("state", 6);
					json.put("info", "索引findex参数不合法");
					json.put("fcontactid", fcontactid);
					
					out.print(json);
					out.close();
					
					return;
				}
			}
			
			FriendContact contactInfo = userService.findFriendContact(fcontactid);
			if(contactInfo == null || bAction==false)
			{
				json.put("state", 3);
				json.put("info", "发送失败，找不到此序通讯录信息，或参数action不合法");
				json.put("fcontactid", fcontactid);
				
				out.print(json);
				out.close();
				
				return;
			}
			
			if(action!=null &&!action.equals(""))
			{
				contactInfo.setFaction(Integer.parseInt(action));
			}
			String serialid = contactInfo.getFtosnid();
			Serialnumber serialnumber = this.serialNumService.Find(serialid);
			if(serialnumber==null)
			{
				json.put("state", 4);
				json.put("info", "发送失败，找不到此序通讯录对应的手表序列号信息");
				json.put("fcontactid", fcontactid);
				
				out.print(json);
				out.close();
				
				return;
			}
				
			String toSerialNumber = serialnumber.getSerialnumber();
			
			int state = 1;
			String info = "成功发送通讯录到手表指令队列";
			
			if(serialnumber.getOnline()==null || !serialnumber.getOnline().equals("1"))
			{
				state = 2;
				info = "检测到设备不在线，发送可能失败";
			}
			
			if(findex!=null&&!findex.equals(""))
			{
				contactInfo.setFindex(Integer.parseInt(findex));
			}
				
			userService.SendContactToWatch(contactInfo, toSerialNumber);
			json.put("state", state);		
			json.put("info", info);	
			json.put("fcontactid", fcontactid);
			
			JsonConfig jsonConfig = Jsonconf.getCommonJsonConf();			
			json.put("contactInfo", JSONObject.fromObject(contactInfo, jsonConfig));			
		
		} catch (Exception e) {
			json.put("state", "-1");
			json.put("info", e.getMessage());
			json.put("fcontactid", null);
			json.put("contactInfo", null);
		} finally{
			out.print(json);
			out.close();
		}
    }
	public void queryClientNum(){
    	PrintWriter out=null;
    	JSONObject json=new JSONObject();
    	try {
    		request.setCharacterEncoding("utf-8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");   		
			out=response.getWriter();
			//如 18058149508
			String phonenum=request.getParameter("phonenum");

						
			if(phonenum!=null && !phonenum.equals(""))
			{																
				//acountid,token,appid,					
				String result =RestTest.testfindClientByMobile(true, acountid,token, phonenum, appid);
				
				JSONObject jsonObject = JSONObject.fromObject(result);
				String resp = jsonObject.getString("resp");
				JSONObject respObj = JSONObject.fromObject(resp);
				String respCode = respObj.getString("respCode");
				
				if(result.indexOf("00000")>0)
				{
					String clientInfo = respObj.getString("client");
					JSONObject infoObject = JSONObject.fromObject(clientInfo);					
					ApiResponse info=(ApiResponse)JSONObject.toBean(infoObject, ApiResponse.class);
					
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("FClientNumber", info.getClientNumber());
					//map.put("FMobile", phonenum);
								
					List<SerialnumberApiphone> listApi = familyNoService.ListSerialnumberApiphone(0, 1, map);
					
					if(listApi==null ||listApi.size()==0)
					{
						SerialnumberApiphone apiObj = new SerialnumberApiphone();
						apiObj.setFappid(appid);
						apiObj.setFclientnumber(String.valueOf(info.getClientNumber()));
						apiObj.setFclientpwd(info.getClientPwd());
						apiObj.setFbalance(info.getBalance());
						apiObj.setFclienttype(info.getClientType());

						
//						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//						String str = sdf.format(info.getCreateDate());//时间存储为字符串					
						apiObj.setFcreatedate(Timestamp.valueOf(info.getCreateDate()));						
						apiObj.setFmobile(info.getMobile());
						familyNoService.AddSerialnumberApiphone(apiObj);
					}
					
					json.put("state", "1");		
					json.put("info", String.format("成功查询，phonenum:%s",phonenum));	
					json.put("respCode", respCode);
					json.put("data", result);
				}
				else
				{
					json.put("state", "2");		
					json.put("info", "创建失败，接口返回不成功");	
					json.put("respCode", respCode);
					json.put("data", result);
				}
			}
			else
			{
				json.put("state", "3");		
				json.put("info", "查询失败，参数不能为空");	
				json.put("data", null);
			}		
		
		} catch (Exception e) {
			json.put("state", "-1");
			json.put("info", e.getMessage());
			json.put("data", null);
		} finally{
			out.print(json);
			out.close();
		}
    }
	
	public void chargeClientNum(){
    	PrintWriter out=null;
    	JSONObject json=new JSONObject();
    	try {
    		request.setCharacterEncoding("utf-8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");   		
			out=response.getWriter();
			//如 68520027654483
			String clientNumber=request.getParameter("clientNumber");
			//chargeType  String  必选  0 充值；1 回收。
			String chargeType=request.getParameter("chargeType");
			//charge  String  必选  充值或回收的金额。 
			String charge=request.getParameter("charge");
						
			if(clientNumber!=null && !clientNumber.equals(""))
			{																

				String result = RestTest.testChargeClient(true, acountid,token, clientNumber, chargeType, charge, appid);
				
				if(result.indexOf("00000")>0)
				{
					json.put("state", "1");		
					json.put("info", String.format("成功充值，clientNumber:%s,chargeType:%s,charge:%s",clientNumber,chargeType,charge));	
					json.put("data", result);
				}
				else
				{
					json.put("state", "2");		
					json.put("info", "创建失败，接口返回不成功");	
					json.put("data", result);
				}
			}
			else
			{
				json.put("state", "3");		
				json.put("info", "创建失败，参数不能为空");	
				json.put("data", null);
			}		
		
		} catch (Exception e) {
			json.put("state", "-1");
			json.put("info", e.getMessage());
			json.put("data", null);
		} finally{
			out.print(json);
			out.close();
		}
    }
	
	
	public void queryClientList(){
    	PrintWriter out=null;
    	JSONObject json=new JSONObject();
    	try {
    		request.setCharacterEncoding("utf-8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");   		
			out=response.getWriter();
			//如 68520027654483
			String start=request.getParameter("start");
			//chargeType  String  必选  0 充值；1 回收。
			String limit=request.getParameter("limit");
			//charge  String  必选  充值或回收的金额。 
			String charge=request.getParameter("charge");
						
			if(start!=null && !start.equals(""))
			{																

				String result = RestTest.testfindClients(true, acountid,token,appid, start, limit);
				
				if(result.indexOf("00000")>0)
				{
					json.put("state", "1");		
					json.put("info", String.format("成功分页查询，start:%s,limit:%s",start,limit));	
					json.put("data", result);
				}
				else
				{
					json.put("state", "2");		
					json.put("info", "创建失败，接口返回不成功");	
					json.put("data", result);
				}
			}
			else
			{
				json.put("state", "3");		
				json.put("info", "创建失败，参数不能为空");	
				json.put("data", null);
			}		
		
		} catch (Exception e) {
			json.put("state", "-1");
			json.put("info", e.getMessage());
			json.put("data", null);
		} finally{
			out.print(json);
			out.close();
		}
    }
	
	private boolean verify(Map<String, String> params) {		  
		return true;
	}

	public void alipayCallBack(){
		
		PrintWriter out=null;
    	JSONObject json=new JSONObject();
    	
		try {
			request.setCharacterEncoding("utf-8");
//			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");   
			out=response.getWriter();	
			
			String out_trade_no = "";
			String total_fee = "";
			
			String trade_no="";
			String trade_status = "";
			
			//out_trade_no、total_fee
			//serialNumService
			
			try{
				StringBuffer  paramBuffer1 = new StringBuffer();
				Map<String,String> headerMap =  Tools.getHeadersInfo(this.request);
				if(headerMap!=null && headerMap.size()>0)
				{								
					for (Iterator<String> iter = headerMap.keySet().iterator(); iter.hasNext();) {
						String name = (String) iter.next();
						String val = headerMap.get(name);										
						paramBuffer1.append(name+":"+val);
						paramBuffer1.append(";");					
					}								
				}
				logger.info(String.format("alipayCallBack,headerMap:%s", paramBuffer1.toString()));
			}
			catch(Exception ex)
			{
				logger.error("alipayCallBack_header"+ex.toString());
				
			}
			
			Map<String,String> params = new HashMap<String,String>();
			try {
				StringBuffer  paramBuffer = new StringBuffer();
				//获取支付宝POST过来反馈信息
				
				Map requestParams = request.getParameterMap();
				for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
					String name = (String) iter.next();
					String[] values = (String[]) requestParams.get(name);
					String valueStr = "";
					for (int i = 0; i < values.length; i++) {
						valueStr = (i == values.length - 1) ? valueStr + values[i]
								: valueStr + values[i] + ",";
					}
					//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
					//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
					params.put(name, valueStr);					
					paramBuffer.append(name+":"+valueStr);
					paramBuffer.append(";");
					
					if(name.equals("out_trade_no"))
					{						
						out_trade_no = valueStr;						
					}
					if(name.equals("total_fee"))
					{						
						total_fee = valueStr;						
					}	
					if(name.equals("trade_no"))
					{						
						trade_no = valueStr;						
					}					
					if(name.equals("trade_status"))
					{						
						trade_status = valueStr;						
					}						
					
					
				}
				
				logger.info(String.format("alipayCallBack,paramBuffer:%s", paramBuffer.toString()));
			
			}
			catch(Exception ex)
			{
				logger.error("alipayCallBack_paramBuffer"+ex.toString());
				
			}
			
			if(out_trade_no!=null &&!out_trade_no.equals(""))
			{
				SerialnumberFee serialnumberFeeInfo = this.serialNumService.findSerialnumberFee(out_trade_no);
				if(serialnumberFeeInfo!=null)
				{
					java.math.BigDecimal feedecimal = new java.math.BigDecimal(total_fee);
					serialnumberFeeInfo.setFdeposit(feedecimal);
					serialnumberFeeInfo.setFremark("支付宝实际充值");
					serialnumberFeeInfo.setFtradeno(trade_no);
					serialnumberFeeInfo.setFtradestatus(trade_status);
					serialnumberFeeInfo.setFtradetime(ByteConverter.getTimeStamp());
					
					this.serialNumService.UpdateSerialnumberFee(serialnumberFeeInfo);
				}
				else
				{
					logger.info(String.format("pay callback fail,out_trade_no:%s",out_trade_no));	
					out.println("fail");
					out.close(); 
					return ;
				}
			}
			

			
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//商户订单号
			out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//支付宝交易号
			trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//交易状态
			trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
			
			logger.info(String.format("alipayCallBack,out_trade_no:%s,trade_no:%s,trade_status:%s", out_trade_no,trade_no,trade_status));
			
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			if(this.verify(params)){//验证成功
				//////////////////////////////////////////////////////////////////////////////////////////
				//请在这里加上商户的业务逻辑程序代码
				//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——			
				if(trade_status.equals("TRADE_FINISHED")){
					//判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//如果有做过处理，不执行商户的业务程序
					
					//注意：
					//该种交易状态只在两种情况下出现
					//1、开通了普通即时到账，买家付款成功后。
					//2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
				} else if (trade_status.equals("TRADE_SUCCESS")){
					//判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//如果有做过处理，不执行商户的业务程序					
					//注意：
					//该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
				}
	
				//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
					
				out.println("success");	//请不要修改或删除
	
				//////////////////////////////////////////////////////////////////////////////////////////
			}else{//验证失败
				out.println("fail");
			}
			
			} catch (Exception e)  {
				// TODO Auto-generated catch block
				out.println("fail");
				
				logger.error(String.format("alipayCallBack,Err:%s", e.toString()));
				
			}
			finally{
	//			out.print(json);
				out.close();
			}
	}
	
	public void callResultNotify(){
    	PrintWriter out=null;
    	JSONObject json=new JSONObject();
    	try {
    		request.setCharacterEncoding("utf-8");
//			response.setContentType("text/json"); 			   					
			response.setContentType("text/xml;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			
			out=response.getWriter();
			
		
			    logger.info("--------------call back StringBuffer----------------");
			    StringBuffer sBuffer = Tools.getInputStream(request);
			    logger.info(sBuffer.toString());
		        logger.info("--------------call back StringBuffer----------------");
		        
		        
//		        URL url = new URL(urlBuf);
//		        HttpURLConnection httpURLConnection = (HttpURLConnection) url
//		          .openConnection();
//		        httpURLConnection.setDoOutput(true);
//		        httpURLConnection.setDoInput(true);
//		        httpURLConnection.setRequestMethod("POST");   httpURLConnection.setConnectTimeout(5000);
//		        httpURLConnection.setReadTimeout(5000);
//		        httpURLConnection.connect();
//		        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
//		          httpURLConnection.getOutputStream(), "GBK"));
		        
		        
		        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		        out.println("<response>");
		        out.println("<retcode>0</retcode>");
		        out.println("<reason>100013</reason>");
		        out.println("</response>");
		        out.flush();
		        
//		        json.put("state", "1");
//				json.put("info","ok");
		
		} catch (Exception e) {
			json.put("state", "-1");
			json.put("info", e.getMessage());
			json.put("data", null);
		} finally{
//			out.print(json);
			out.close();
		}
    }
}
