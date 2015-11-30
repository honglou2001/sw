package com.zhuika.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.users.ejb.SerialnumberDetach;
import com.users.ejb.SerialnumberFee;
import com.watch.ejb.LocElectfence;
import com.watch.ejb.Serialnumber;
import com.zhuika.dao.ILocationInfoDao;
import com.zhuika.dao.impl.LocElectfenceDaoIml;
import com.zhuika.entity.LocationInfo;
import com.zhuika.service.SerialNumberService;
import com.zhuika.util.Jsonconf;
import com.zhuika.util.Tools;

public class LocEleAction extends BaseAction implements ServletRequestAware,
		ServletResponseAware {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	@Resource
	private SerialNumberService serialNumService;
    @Resource
    private ILocationInfoDao liDao;
    
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	// 电子围栏进出
	private JSONArray getElcInfoJson(String serialNumber) {
		JSONArray arrayObj = new JSONArray();

		LocElectfenceDaoIml locelect = new LocElectfenceDaoIml();
		HashMap<String, String> queryMap = new HashMap<String, String>();

		queryMap.put("serialNumber", serialNumber);

		List<LocElectfence> listLocElectfence = locelect.GetAll(1, 2, queryMap);

		if (!listLocElectfence.isEmpty()) {

			JsonConfig jsonConfig = new JsonConfig();

			jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class,
					new JsonValueProcessor() {
						private SimpleDateFormat sd = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");// new
														// SimpleDateFormat("yyyy-MM-dd");

						public Object processObjectValue(String key,
								Object value, JsonConfig jsonConfig) {
							return value == null ? "" : sd.format(value);
						}

						public Object processArrayValue(Object value,
								JsonConfig jsonConfig) {
							return null;
						}
					});

			jsonConfig.setExcludes(new String[] { "faddtime", "feltfenceid",
					"feltlatitude", "feltlongitude", "ffieldstatus",
					"fincreaseid", "flatitude", "flocfenid", "flongitude","battery" });

			arrayObj = JSONArray.fromObject(listLocElectfence, jsonConfig);
		}

		return arrayObj;
	}
	
	// 手表脱落最新
		private JSONArray getDetachJson(String serialNumber) {
			JSONArray arrayObj = null;
			HashMap<String, String> mapSerial = new HashMap<String, String>();
			mapSerial.put("FSNID", serialNumber);
			
			List<SerialnumberDetach> listDetach = serialNumService.ListSerialnumberDetach(0, 1, mapSerial);						
					
			JsonConfig  jconfig = Jsonconf.getCommonJsonConf();						
			jconfig.setExcludes(new String[] {"fincreaseid", "ffieldstatus","fsnid","fuserid","fdetachid"});

			arrayObj = JSONArray.fromObject(listDetach, jconfig);
			
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

		
		private JSONArray getBatteryJson(String serialNumber) {
			
			JSONArray arrayObj = null;
			
			LocationInfo battery=liDao.findBySeriaNumber(serialNumber);
			
			
			JsonConfig  jconfig = Jsonconf.getCommonJsonConf();						
			jconfig.setExcludes(new String[] {"location", "text", "lng" ,"lat","id","faddtime"});
			
			
			arrayObj = JSONArray.fromObject(battery, jconfig);
			
		    return arrayObj;
		}

	public void queryMsgCenter() {

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
			
			JSONArray locfecnce = this.getElcInfoJson(serialnumber.getSerialnumber());
			JSONArray detach = this.getDetachJson(serialnumber.getSerialnumber());
			JSONArray fee = this.getFeeBalanceJson(serialnumid);
			JSONArray battery = this.getBatteryJson(serialnumber.getSerialnumber());
			
			JsonConfig jsonSerialNumConfig = Jsonconf.getSertialNumJsonConf(request);
			jsonSerialNumConfig.setExcludes(new String[] {"floglastip", "fphonetime", "fqrcode" ,"id","fislostinfo","fislosttime"});

			
			json.put("state", 1);
			json.put("info", "成功查询");
			
			json.put("serialNumber", JSONArray.fromObject(serialnumber, jsonSerialNumConfig));
			json.put("notifyFecnce", locfecnce);
			json.put("nofityDetach", detach);
			json.put("notifyFee", fee);
			json.put("notifyBattery", battery);

		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());
			json.put("serialNumber", null);
			json.put("notifyFecnce", null);
			json.put("nofityDetach", null);
			json.put("notifyFee", null);
			json.put("notifyBattery", null);
		} finally {
			out.print(json);
			out.close();
		}
	}

	public void queryLocEleChange() {
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();

			String serialNumber = request.getParameter("serialNumber");
			String areaNumber = request.getParameter("areaNumber");
			String areaName = request.getParameter("areaName");

			areaName = Tools.DecodeUtf8String(areaName);

			LocElectfenceDaoIml locelect = new LocElectfenceDaoIml();

			try {
				HashMap<String, String> queryMap = new HashMap<String, String>();

				queryMap.put("serialNumber", serialNumber);
				queryMap.put("areaNumber", areaNumber);
				queryMap.put("areaName", areaName);

				List<LocElectfence> listLocElectfence = locelect.GetAll(1, 2,
						queryMap);

				// List<LocElectfence> listLocElectfence= new
				// ArrayList<LocElectfence>();

				System.out.println(listLocElectfence.size());
				if (!listLocElectfence.isEmpty()) {

					JsonConfig jsonConfig = new JsonConfig();

					jsonConfig.registerJsonValueProcessor(
							java.sql.Timestamp.class, new JsonValueProcessor() {
								private SimpleDateFormat sd = new SimpleDateFormat(
										"yyyy-MM-dd HH:mm:ss");// new
																// SimpleDateFormat("yyyy-MM-dd");

								public Object processObjectValue(String key,
										Object value, JsonConfig jsonConfig) {
									return value == null ? "" : sd
											.format(value);
								}

								public Object processArrayValue(Object value,
										JsonConfig jsonConfig) {
									return null;
								}
							});

					jsonConfig.setExcludes(new String[] { "faddtime",
							"feltfenceid", "feltlatitude", "feltlongitude",
							"ffieldstatus", "fincreaseid", "flatitude",
							"flocfenid", "flongitude" });

					// jsonArr.add(JSONArray.fromObject(listLocElectfence,jsonConfig));
					// jsonArr.add(listLocElectfence);

					json.put("state", 1);
					json.put("info", "成功查询");
					json.put("data",
							JSONArray.fromObject(listLocElectfence, jsonConfig));
				} else {
					json.put("state", 1);
					json.put("info", "成功查询，数据为空");
					json.put("data", null);
				}

			} catch (RuntimeException ex) {
				json.put("state", 1);
				json.put("info", ex.getMessage());
				json.put("data", null);
			}
		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());
			json.put("data", null);
		} finally {
			out.print(json);
			out.close();
		}
	}
}
