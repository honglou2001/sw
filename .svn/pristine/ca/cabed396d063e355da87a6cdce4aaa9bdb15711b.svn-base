package com.zhuika.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.stereotype.Repository;
import com.zhuika.dao.IInfoDao;
import com.zhuika.dao.IRtPositionDao;
import com.zhuika.discard.DiscardClient;
import com.zhuika.entity.Info;
import com.zhuika.entity.RtPosition;
import com.zhuika.factory.DAOException;



@Repository
public class RtPositionAction extends BaseAction implements ServletRequestAware,ServletResponseAware{
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
    private HttpServletResponse response;
    @Resource
    private IRtPositionDao rtpDao;
    private IInfoDao infoDao;
    
	public void setInfoDao(IInfoDao infoDao) {
		this.infoDao = infoDao;
	}  
	public void setRtpDao(IRtPositionDao rtpDao) {
		this.rtpDao = rtpDao;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response=response;		
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request=request;	
	}
	public void updateRtp() throws DAOException{
		PrintWriter out=null;
		JSONObject json=new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			//response.setContentType("text/html;charset=utf8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");
			out=response.getWriter();
			String serialNumber=request.getParameter("serialNumber");		
			RtPosition rtp=rtpDao.findBySerialNumber(serialNumber);
			Calendar cal=Calendar.getInstance();
			Date date=cal.getTime();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String createTime=sdf.format(date);
			rtp.setStatus("0");
			rtp.setCreateTime(createTime);			
			rtpDao.updateRtPosition(rtp);
			String info = "40400012" + serialNumber + "9982" + "01"+"0032"+"0d0a";
			byte b[] = HexString2Bytes(info);
			System.out.println("b.toString:" + Arrays.toString(b));
			System.out.println("b.length:" + b.length);
			Info i=infoDao.findBySerialNumber(serialNumber);			
			System.out.println(i.getIp()+":"+i.getPort());
			String[] args={i.getIp(),String.valueOf(i.getPort()),info};
			DiscardClient.getClient(args);
			json.put("state", "1");		
			json.put("info", "成功发送实时定位指令");
		} catch (Exception e) {
			json.put("state", "-1");
			json.put("info", e.getMessage());
		} finally{
			out.print(json);
			out.close();
		}
	}
	public void searchRtp() throws DAOException{
		PrintWriter out=null;
		JSONObject json=new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			//response.setContentType("text/html;charset=utf8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");
			out=response.getWriter();
			String serialNumber=request.getParameter("serialNumber");	
			RtPosition rtp=rtpDao.findBySerialNumber(serialNumber);
			if(rtp!=null){
				if("1".equals(rtp.getQuery())){
					json.put("state", 1);
					json.put("info", "定位成功");	
					json.put("lng", rtp.getLng());
					json.put("lat", rtp.getLat());
					rtp.setQuery("0");
					rtpDao.updateRtPosition(rtp);
				}else{
					json.put("state", 0);
					json.put("info", "没有定到位");	
				}
			}else{
				json.put("state", -1);
				json.put("info", "定位表无此序列号信息");	
			}
		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());	
		} finally{
			out.print(json);
			out.close();
		}
	}
	// 从十六进制字符串到字节数组转换
	private static byte[] HexString2Bytes(String hexstr) {
	  	byte[] b = new byte[hexstr.length() / 2];
	  	int j = 0;
	  	for (int i = 0; i < b.length; i++) {
	  		char c0 = hexstr.charAt(j++);
	  		char c1 = hexstr.charAt(j++);
	  		b[i] = (byte) ((parse(c0) << 4) | parse(c1));
	  	}
	  	return b;
	}

	private static int parse(char c) {
	  	if (c >= 'a')
	  		return (c - 'a' + 10) & 0x0f;
	  	if (c >= 'A')
	  		return (c - 'A' + 10) & 0x0f;
	  	return (c - '0') & 0x0f;
	  }

	public static int CRC_XModem(byte[] bytes) {
	  	int crc = 0x00;
	  	int polynomial = 0x1021;
	  	for (int index = 0; index < bytes.length; index++) {
	  		byte b = bytes[index];
	  		for (int i = 0; i < 8; i++) {
	  			boolean bit = ((b >> (7 - i) & 1) == 1);
	  			boolean c15 = ((crc >> 15 & 1) == 1);
	  			crc <<= 1;
	  			if (c15 ^ bit)
	  				crc ^= polynomial;
	  		}
	  	}
	  	crc &= 0xffff;
	  	return crc;
	}
}
