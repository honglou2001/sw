package com.zhuika.util;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.common.ejb.EjbCmdData;
import com.watch.ejb.MqTask;
import com.zhuika.dao.IMqDao;
import com.zhuika.dao.impl.MqTaskDaoIml;

public class ejbproxy {

	public static Context getInitialConnection() {
		final String INIT_FACTORY = "org.jnp.interfaces.NamingContextFactory";
		final String SERVER_URL = "jnp://localhost:1099";
		Context ctx = null;
		try {
			Properties props = new Properties();

			props.put(Context.INITIAL_CONTEXT_FACTORY, INIT_FACTORY);
			props.put(Context.PROVIDER_URL, SERVER_URL);
			ctx = new InitialContext(props);
		} catch (NamingException ne) {
			// TODO: handle exception
			System.err.println("不能连接WebLogic Server在：" + SERVER_URL);
			ne.printStackTrace();
		}
		return ctx;
	}
	
	public static void saveToMq(String snnumber,String cmd,String info,String usrid,String remark,String param)
	{
		MqTask mqTaskInfo = new MqTask();
		mqTaskInfo.setFcmd(cmd);
		mqTaskInfo.setFsnid(snnumber);
		mqTaskInfo.setFsenddata(info);
		mqTaskInfo.setFuserid(usrid);
		mqTaskInfo.setFtrycount(3);
		
		if(remark==null || remark.equals("")){
			String cmdName = EjbCmdData.getCmdNameByCmd(cmd);
			mqTaskInfo.setFremark(cmdName);
		}
		else{			
			mqTaskInfo.setFremark(remark);
		}
		
		mqTaskInfo.setFparam(param);
		
		IMqDao mqDao = new MqTaskDaoIml();
		
		mqDao.AddMqTask(mqTaskInfo);
	}
}
