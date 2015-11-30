package com.zhuika.action;

import java.io.PrintWriter;
import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.stereotype.Controller;
import com.zhuika.dao.IBindPhoneDao;
import com.zhuika.dao.IInfoDao;
import com.zhuika.dao.ISerialNumberDao;
import com.zhuika.discard.DiscardClient;
import com.zhuika.entity.BindPhone;
import com.zhuika.entity.Info;
import com.zhuika.entity.SerialNumber;
import com.zhuika.factory.DAOException;
@Controller
public class ListenAction extends BaseAction implements ServletRequestAware,ServletResponseAware{
	private static final long serialVersionUID = 1L;
    private HttpServletRequest request;
    private HttpServletResponse response;
    @Resource
    private IBindPhoneDao bindPhoneDao;
    private ISerialNumberDao serialNumberDao;
    private IInfoDao infoDao;
     
	public void setInfoDao(IInfoDao infoDao) {
		this.infoDao = infoDao;
	}  
	public void setSerialNumberDao(ISerialNumberDao serialNumberDao) {
		this.serialNumberDao = serialNumberDao;
	}
	public void setBindPhoneDao(IBindPhoneDao bindPhoneDao) {
		this.bindPhoneDao = bindPhoneDao;
	}
	public void setServletResponse(HttpServletResponse response) {
		this.response=response;	
	}
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;	
	}
    public void searchListen() throws DAOException{
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
			BindPhone bp=bindPhoneDao.findBySeriaNumber(serialNumber);
			if(bp!=null){
			String listenNo=bp.getListenNo();
			System.out.println(listenNo);
			if(listenNo.length()==(listenNo.indexOf("=")+1)){
				json.put("state", 2);
				json.put("info", "没有设置监听号码");
			}else{
				SerialNumber sn=serialNumberDao.findBySerialNumber(serialNumber);
				json.put("state", 1);
				json.put("info", "监听的状态,listenStatus为2可以发送监听");
				json.put("listenStatus", sn.getListenStatus());
			}
			}
			else
			{
				json.put("state", 3);
				json.put("info", "查询不到此手环绑定的手机号码");
			}
		} catch (Exception e) {
			json.put("state", -1);	
			json.put("info", e.getMessage());		
		} finally{
			out.print(json);
			out.close();
		}
    }
    public void sendListen() throws DAOException{
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
			System.out.println(sn==null);
			//sn.setListenStatus("0");
			sn.setListenStatus("2");
			System.out.println(sn.toString());
			serialNumberDao.updateSerialNumber(sn);
			String info = "40400012" + serialNumber + "4130" + "01"+"0032"+"0d0a";
			byte b[] = HexString2Bytes(info);
			System.out.println("b.toString:" + Arrays.toString(b));
			System.out.println("b.length:" + b.length);
			Info i=infoDao.findBySerialNumber(serialNumber);			
			System.out.println(i.getIp()+":"+i.getPort());
			String[] args={i.getIp(),String.valueOf(i.getPort()),info};
			DiscardClient.getClient(args);
			json.put("state", 1);
			json.put("info", "发送监听成功");		
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
