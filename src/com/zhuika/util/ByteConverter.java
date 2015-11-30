package com.zhuika.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.annotation.Resource;

import com.zhuika.dao.IInfoDao;
import com.zhuika.dao.impl.InfoDaoImpl;
import com.zhuika.discard.DiscardClient;
import com.zhuika.entity.Info;

public class ByteConverter {

	
	public static byte[] shortToBytes(short x,boolean endian)
	{
		byte[] ret = new byte[2];
		//Little Endian		
		if(endian == Boolean.TRUE){
		    ret[0] = (byte) x;
		    ret[1] = (byte) (x >> 8);
		}
		else{
		    //Big Endian
		    ret[0] = (byte) (x >> 8);
		    ret[1] = (byte) x;
		}	   
		
//		ByteBuffer buffer = ByteBuffer.allocate(2);
//		buffer.putShort(value);
//		return buffer.array();
		
	    return ret;
	}
	
    public static Timestamp getTimeStamp()
    {    	   
    	Timestamp nowdate1 = new Timestamp(System.currentTimeMillis());
    	return nowdate1;
    
    }
    
    //str:"2011-05-09 11:49:45";  
    public static Timestamp getTimeStamp(String str)
    {
		Timestamp ts = new Timestamp(System.currentTimeMillis());   
		try {   
		    	ts = Timestamp.valueOf(str);   		   
		    } catch (Exception e) {   
		    	e.printStackTrace();   
		    	return Timestamp.valueOf("1990-01-01 01:01:01");
		    }  		    	      
		   return ts;
    }
        	
	public static byte[] GetDateTime()
    {
    	//Timestamp dtime = ByteConverter.getTimeStamp();
    	Calendar cal1 = Calendar.getInstance(); 


        byte[] year = ByteConverter.shortToBytes((short)cal1.YEAR,true);

        byte[] bdatetime = new byte[7];
        bdatetime[0] = year[0];
        bdatetime[1] = year[1];
         bdatetime[2] = (byte)cal1.MONTH;
         bdatetime[3]= (byte)cal1.DAY_OF_MONTH;
         bdatetime[4]= (byte)cal1.HOUR;
         bdatetime[5] = (byte)cal1.MINUTE;
         bdatetime[6]= (byte)cal1.SECOND;

         return bdatetime;
    }
	
	public static byte[] GetSendData(byte[] bData,String serialno,byte[] cmd) throws UnsupportedEncodingException
      {
		 //byte[] framedata = {0x24,0x24,0x00,0x11,0x80,0x68,0x00,0x00,0x10,0x01,0x22,0x00,0x01};
		 
        
          //byte[] bData = {0x41,0x06,0x06,0x41,0x09,0x09, 0x40, 0x10, 0x17 };//sData.getBytes("UTF-8");;
          short length = (short)(17 + bData.length);
         
          ByteBuffer buffer = ByteBuffer.allocate(length); 
          
          //ByteBuffer 
          byte[] head = { 0x40,0x40 };
          buffer.put(head);
                               
          //总帧长度
          byte[] blength = ByteConverter.shortToBytes(length, false);
          buffer.put(blength);
          
          //客户端编号
          //byte[] clientId = { 0x3, 0x02,0x00,0x00,0x00,0x00,0x10 };
          byte[] clientId =HexString2Bytes(serialno);
          buffer.put(clientId);

          //byte[] cmd = { 0x20,0x01 };
          buffer.put(cmd);
       
          //数据内容 bData
          buffer.put(bData);
      
           
          //byte[] crc = { 0x02, 0x04 };
//          int crcint =ComputeCRC(buffer.array(),length-4) ;
//          short crcshort =(short)crcint;
//          byte[] crc =Hex.shortToByteArray(crcshort);
//          buffer.put(crc);
          
          byte[] crcCal = ComputeCRC(buffer.array(),length-4);
          byte[] crc = { crcCal[0], crcCal[1] };
          buffer.put(crc);

          byte[] end = { 0x0D, 0x0A };
          buffer.put(end);
          
  		 byte[] bytes =buffer.array();		
  		 String sInfo = Hex.encodeHexStr(bytes);

          return bytes;
      }
	
	//fuck   
	//isHexData 是否已经是 hex 字符串
	public static String GetSendData(String serialno,String cmd,String bData,boolean isHexChar) 
    {
		if(!isHexChar){
			bData = Hex.encodeHexStr(bData.getBytes());
		}
		 
		String info = "4040"+"0013" + serialno + cmd+ bData+"0032"+"0d0a";
				
		byte[] b = HexString2Bytes(info);
				
		String length = Integer.toHexString(b.length);
		
		String newinfo = "404000"+length+serialno+cmd+bData+"0032"+"0d0a";
		
		String crcString = ByteConverter.getCrcString(newinfo);
		newinfo = newinfo.substring(0,info.length()-8);			
		newinfo = newinfo+crcString+"0d0a";
		
		return newinfo;
		
    }
	
	public static String getCrcString(String info)
	{
		byte[] b2 = HexString2Bytes(info);
		//int calInt = ComputeCRCInt(b2,b2.length-4);
		//String checksum = Integer.toHexString(calInt).toUpperCase();
		byte[] byteCrc = ComputeCRC(b2,b2.length-4);
		
		String checksum = Hex.encodeHexStr(byteCrc);
				
		System.out.println("checksum1:" + checksum);
		if (checksum.length() == 4) {

		} else if (checksum.length() == 3) {
			checksum = "0" + checksum;
		} else if (checksum.length() == 2) {
			checksum = "00" + checksum;
		} else if (checksum.length() == 1) {
			checksum = "000" + checksum;
		} else {
			checksum = checksum.substring(0, 4);
		}

		return checksum;
	}
	
	public static int ComputeCRCInt(byte[] val, int len)
	{
		
	    long crc;
	    long q;
	    byte c;
	    int i = 0;
		
	    crc = 0;
	    for (i = 0; i < len; i++)
	    {
	        c = val[i];
	        q = (crc ^ c) & 0x0f;
	        crc = (crc >> 4) ^ (q * 0x1081);
	        q = (crc ^ (c >> 4)) & 0xf;
	        crc = (crc >> 4) ^ (q * 0x1081);
	    }
	    //return (byte)crc << 8 | (byte)(crc >> 8);
	    return (int) crc;
	}
	
	public static byte[] ComputeCRC(byte[] val, int len)
	{
		int d = ComputeCRCInt(val,len);
		
		byte[] result = new byte[2];   
		result[0] = (byte)((d >> 8) & 0xFF);
		result[1] = (byte)(d & 0xFF);
		
		return result;
	}
	
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
	
	public static int CRC_XModem(byte[] bytes, int len) {
		int crc = 0x00; // initial value
		int polynomial = 0x1021;
		for (int index = 0; index <len; index++) {
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

	
	public static void sendToServerAscii(IInfoDao infoDao,String toSerialNumber,String info)
	{		
		if(!info.equals("")){
			Info i=infoDao.findBySerialNumber(toSerialNumber);	
			if(i!=null){
				String ip = i.getIp();
				int port = i.getPort();
				
				String[] args={ip,String.valueOf(port),info};
				try {
					DiscardClient.getClient(args);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();					
				}
			}
			else
			{
				throw new RuntimeException(String.format("服务器无手表%s的相关信息", toSerialNumber));
			}
		}
	}
	
	public static void sendToServerByte(IInfoDao infoDao,String toSerialNumber,byte[] toSend)
	{		
		if(toSend!=null){
			Info i=infoDao.findBySerialNumber(toSerialNumber);	
			if(i!=null){
				String ip = i.getIp();
				int port = i.getPort();
				
				String[] args={ip,String.valueOf(port),"info"};
				try {
					DiscardClient.getClient(args,toSend);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();					
				}
			}
			else
			{
				throw new RuntimeException(String.format("服务器无手表%s的相关信息", toSerialNumber));
			}
		}
	}
	
	public static int Computetable(byte[] bytes,int len)
	{
		int crc = 0x0000;
		 for (int i = 0; i < len; i++){
        //for (byte b : bytes) {
            crc = (crc >>> 8) ^ table[(crc ^ bytes[i]) & 0xff];
        }
        
        return crc;
	}
	
	static int[] table = {
            0x0000, 0xC0C1, 0xC181, 0x0140, 0xC301, 0x03C0, 0x0280, 0xC241,
            0xC601, 0x06C0, 0x0780, 0xC741, 0x0500, 0xC5C1, 0xC481, 0x0440,
            0xCC01, 0x0CC0, 0x0D80, 0xCD41, 0x0F00, 0xCFC1, 0xCE81, 0x0E40,
            0x0A00, 0xCAC1, 0xCB81, 0x0B40, 0xC901, 0x09C0, 0x0880, 0xC841,
            0xD801, 0x18C0, 0x1980, 0xD941, 0x1B00, 0xDBC1, 0xDA81, 0x1A40,
            0x1E00, 0xDEC1, 0xDF81, 0x1F40, 0xDD01, 0x1DC0, 0x1C80, 0xDC41,
            0x1400, 0xD4C1, 0xD581, 0x1540, 0xD701, 0x17C0, 0x1680, 0xD641,
            0xD201, 0x12C0, 0x1380, 0xD341, 0x1100, 0xD1C1, 0xD081, 0x1040,
            0xF001, 0x30C0, 0x3180, 0xF141, 0x3300, 0xF3C1, 0xF281, 0x3240,
            0x3600, 0xF6C1, 0xF781, 0x3740, 0xF501, 0x35C0, 0x3480, 0xF441,
            0x3C00, 0xFCC1, 0xFD81, 0x3D40, 0xFF01, 0x3FC0, 0x3E80, 0xFE41,
            0xFA01, 0x3AC0, 0x3B80, 0xFB41, 0x3900, 0xF9C1, 0xF881, 0x3840,
            0x2800, 0xE8C1, 0xE981, 0x2940, 0xEB01, 0x2BC0, 0x2A80, 0xEA41,
            0xEE01, 0x2EC0, 0x2F80, 0xEF41, 0x2D00, 0xEDC1, 0xEC81, 0x2C40,
            0xE401, 0x24C0, 0x2580, 0xE541, 0x2700, 0xE7C1, 0xE681, 0x2640,
            0x2200, 0xE2C1, 0xE381, 0x2340, 0xE101, 0x21C0, 0x2080, 0xE041,
            0xA001, 0x60C0, 0x6180, 0xA141, 0x6300, 0xA3C1, 0xA281, 0x6240,
            0x6600, 0xA6C1, 0xA781, 0x6740, 0xA501, 0x65C0, 0x6480, 0xA441,
            0x6C00, 0xACC1, 0xAD81, 0x6D40, 0xAF01, 0x6FC0, 0x6E80, 0xAE41,
            0xAA01, 0x6AC0, 0x6B80, 0xAB41, 0x6900, 0xA9C1, 0xA881, 0x6840,
            0x7800, 0xB8C1, 0xB981, 0x7940, 0xBB01, 0x7BC0, 0x7A80, 0xBA41,
            0xBE01, 0x7EC0, 0x7F80, 0xBF41, 0x7D00, 0xBDC1, 0xBC81, 0x7C40,
            0xB401, 0x74C0, 0x7580, 0xB541, 0x7700, 0xB7C1, 0xB681, 0x7640,
            0x7200, 0xB2C1, 0xB381, 0x7340, 0xB101, 0x71C0, 0x7080, 0xB041,
            0x5000, 0x90C1, 0x9181, 0x5140, 0x9301, 0x53C0, 0x5280, 0x9241,
            0x9601, 0x56C0, 0x5780, 0x9741, 0x5500, 0x95C1, 0x9481, 0x5440,
            0x9C01, 0x5CC0, 0x5D80, 0x9D41, 0x5F00, 0x9FC1, 0x9E81, 0x5E40,
            0x5A00, 0x9AC1, 0x9B81, 0x5B40, 0x9901, 0x59C0, 0x5880, 0x9841,
            0x8801, 0x48C0, 0x4980, 0x8941, 0x4B00, 0x8BC1, 0x8A81, 0x4A40,
            0x4E00, 0x8EC1, 0x8F81, 0x4F40, 0x8D01, 0x4DC0, 0x4C80, 0x8C41,
            0x4400, 0x84C1, 0x8581, 0x4540, 0x8701, 0x47C0, 0x4680, 0x8641,
            0x8201, 0x42C0, 0x4380, 0x8341, 0x4100, 0x81C1, 0x8081, 0x4040,
        };

	
		//	public static int ComputeCRC(byte[] val, int len)
		//	{
		//		
		//	    long crc;
		//	    long q;
		//	    byte c;
		//	    int i = 0;
		//		
		//	    crc = 0;
		//	    for (i = 0; i < len; i++)
		//	    {
		//	        c = val[i];
		//	        q = (crc ^ c) & 0x0f;
		//	        crc = (crc >> 4) ^ (q * 0x1081);
		//	        q = (crc ^ (c >> 4)) & 0xf;
		//	        crc = (crc >> 4) ^ (q * 0x1081);
		//	    }
		//	    return (byte)crc << 8 | (byte)(crc >> 8);
		//	}
		//	
		//	public static int ComputeCRC(byte[] val, int len)
		//	{
		//		
		//	    long crc;
		//	    long q;
		//	    int c;
		//	    int i = 0;
		//		
		//	    crc = 0;
		//	    for (i = 0; i < len; i++)
		//	    {
		//	        c = val[i];
		//	        q = (crc ^ c) & 0x0f;
		//	        crc = (crc >> 4) ^ (q * 0x1081);
		//	        q = (crc ^ (c >> 4)) & 0xf;
		//	        crc = (crc >> 4) ^ (q * 0x1081);
		//	    }
		//	    return ((int)crc << 8) | (int)(crc >> 8);
		//	}
 
	
}