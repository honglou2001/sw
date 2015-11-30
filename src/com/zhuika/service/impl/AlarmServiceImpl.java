package com.zhuika.service.impl;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.watch.ejb.Serialnumber;
import com.watch.ejb.UserAlarm;
import com.zhuika.dao.IAlarmDao;
import com.zhuika.dao.IInfoDao;
import com.zhuika.dao.ISerialNumberDao;
import com.zhuika.discard.DiscardClient;
import com.zhuika.entity.Alarm;
import com.zhuika.entity.Info;
import com.zhuika.service.AlarmService;
import com.zhuika.util.ByteConverter;
import com.zhuika.util.Hex;
import com.zhuika.util.ejbproxy;
@Service("alarmService")
public class AlarmServiceImpl implements AlarmService {
	@Resource
    private IAlarmDao alarmDao;
	@Resource
	private IInfoDao infoDao;
    @Resource
    private ISerialNumberDao serialNumberDao;
	public void addAlarm(Alarm alarm) {
		alarmDao.save(alarm);
		sendMessage(alarm);
	}	

	public void updateAlarm(Alarm alarm) {
		alarmDao.update(alarm);
		sendMessage(alarm);
	}

	public Alarm getAlarm(String serialNumber) {
		
		return alarmDao.findByName(serialNumber);
	}
	private void sendMessage(Alarm alarm) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		//06:06.1,09:00.1,16:23.1
		String[] getAlarm = alarm.getAlarm().split(",");

		String sendAlarm = "";
		for (int i = 0; i < getAlarm.length; i++) {
			if (getAlarm[i].split("\\.")[1].equals("1")) {
				list.add(i + 1);
			}
			sendAlarm += getAlarm[i].split("\\.")[0] + ";";
		}
		int res = 0;
		for (int i = 0; i < list.size(); i++) {
			res += list.get(i);
		}
		sendAlarm = sendAlarm.replaceAll(":", "").substring(0,sendAlarm.replaceAll(":", "").length() - 1);
		System.out.println("sendAlarm:" + sendAlarm);
		String info = "40400050" + alarm.getSerialNumber() + "2001";
		String content=null;
		if (res == 0) {
			info = info + "00" + Hex.encodeHexStr(sendAlarm.getBytes());	
			content=alarm.getSerialNumber() + "2001"+"00" + Hex.encodeHexStr(sendAlarm.getBytes());	
		} else if (res == 1) {
			info = info + "01" + Hex.encodeHexStr(sendAlarm.getBytes());
			content=alarm.getSerialNumber() + "2001"+"01" + Hex.encodeHexStr(sendAlarm.getBytes());
		} else if (res == 2) {
			info = info + "02" + Hex.encodeHexStr(sendAlarm.getBytes());	
			content=alarm.getSerialNumber() + "2001"+"02" + Hex.encodeHexStr(sendAlarm.getBytes());
		} else if (res == 3 && list.size() == 1) {
			info = info + "04" + Hex.encodeHexStr(sendAlarm.getBytes());
			content=alarm.getSerialNumber() + "2001"+"04" + Hex.encodeHexStr(sendAlarm.getBytes());
		} else if (res == 3 && list.size() == 2) {
			info = info + "03" + Hex.encodeHexStr(sendAlarm.getBytes());
			content=alarm.getSerialNumber() + "2001"+"03" + Hex.encodeHexStr(sendAlarm.getBytes());
		} else if (res == 4) {
			info = info + "05" + Hex.encodeHexStr(sendAlarm.getBytes());
			content=alarm.getSerialNumber() + "2001"+"05" + Hex.encodeHexStr(sendAlarm.getBytes());
		} else if (res == 5) {
			info = info + "06" + Hex.encodeHexStr(sendAlarm.getBytes());
			content=alarm.getSerialNumber() + "2001"+"06" + Hex.encodeHexStr(sendAlarm.getBytes());
		} else if (res == 6) {
			info = info + "07" + Hex.encodeHexStr(sendAlarm.getBytes());
			content=alarm.getSerialNumber() + "2001"+"07" + Hex.encodeHexStr(sendAlarm.getBytes());
		}			
		info=info+"0032"+"0d0a";			
		byte b[] = HexString2Bytes(info);
//		System.out.println("b.toString:" + Arrays.toString(b));
//		System.out.println("b.length:" + b.length);
//		System.out.println(Integer.toHexString(b.length));
		info="404000"+Integer.toHexString(b.length)+content+"0032"+"0d0a";
		Info i=infoDao.findBySerialNumber(alarm.getSerialNumber());			
//		b=HexString2Bytes(info);
//		System.out.println(i.getIp()+":"+i.getPort());
		String[] args={i.getIp(),String.valueOf(i.getPort()),info};
		try {
			DiscardClient.getClient(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//alarmid=821ff4a8-36f5-4bdf-8e77-804866b54248&alarmName=name&alarmVal=1:06:06&isValid=1
	private void sendMessageToWatch(UserAlarm alarm) throws UnsupportedEncodingException {	
		
		//获取已有闹钟信息，作为整体发送给手环
		String serialnumid=alarm.getFsnidstr();							
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("FSNIDStr", serialnumid);		
		List<UserAlarm> lists = this.ListUserAlarm(0, 100,map);
		
		SentListToWatch(lists,serialnumid);
		
	}
	
	
	private void SentListToWatch(List<UserAlarm> lists,String serialnumid)
	{
		byte bWeekValid = 0x00;
		byte hour = 0x00;
		byte minute = 0x00;
		int count = lists.size();
		
		ByteBuffer buffer = ByteBuffer.allocate(3*count); 				
		for(int i=0;i<count;i++){
			String alarmVal =  lists.get(i).getFtime();		
			String[] timeVal = alarmVal.split(":");
			
			if(timeVal!=null && timeVal.length==3)
			{
				//星期几及开关
				bWeekValid = Integer.valueOf(timeVal[0]).byteValue();
				//bWeekValid= Byte.parseByte(timeVal[0]);
				buffer.put(bWeekValid);
				
				//小时
				hour = Byte.parseByte(timeVal[1]);
				buffer.put(hour);
				
				//分钟
				minute = Byte.parseByte(timeVal[2]);
				buffer.put(minute);
	
			}			
		}
					
		byte[] toSend = null;
		Serialnumber serialnumber  = serialNumberDao.Find(serialnumid);	
				
		try {
			byte[] cmd = { 0x20,0x01 };
			toSend = ByteConverter.GetSendData(buffer.array(),serialnumber.getSerialnumber(),cmd);
			
			String dataHexStr=Hex.encodeHexStr(toSend);				
			ejbproxy.saveToMq(serialnumber.getSerialnumber(), "2001", dataHexStr, "", "闹钟下发","");
			
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
		//获取要发送到哪个客户端			
		Info i=infoDao.findBySerialNumber(serialnumber.getSerialnumber());			
		String[] args={i.getIp(),String.valueOf(i.getPort()),"info"};
		try {
			DiscardClient.getClient(args,toSend);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
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

			@Override
			public void Add(UserAlarm userAlarmInfo) {
				// TODO Auto-generated method stub
				alarmDao.Add(userAlarmInfo);
				
				//发送到手表客户端
				try {
					this.sendMessageToWatch(userAlarmInfo);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void Update(UserAlarm userAlarmInfo) {
				// TODO Auto-generated method stub
				alarmDao.Update(userAlarmInfo);

				//发送到手表客户端
				try {
					this.sendMessageToWatch(userAlarmInfo);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			@Override
			public void Delete(String id) {
				// TODO Auto-generated method stub
				
				UserAlarm userAlarmInfo =this.find(id);
				
				alarmDao.Delete(id);																				
				
				//发送到手表客户端
				try {
					this.sendMessageToWatch(userAlarmInfo);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
								
			}

			@Override
			public UserAlarm find(String id) {
				// TODO Auto-generated method stub
				return alarmDao.find(id);
			}

			@Override
			public int GetCount(HashMap<String, String> map) {
				// TODO Auto-generated method stub
				return alarmDao.GetCount(map);
			}

			@Override
			public List<UserAlarm> ListUserAlarm(int offset, int length,
					HashMap<String, String> map) {
				// TODO Auto-generated method stub
				return alarmDao.ListUserAlarm(offset, length, map);
			}
}
