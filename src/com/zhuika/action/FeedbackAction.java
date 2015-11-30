
package com.zhuika.action;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.stereotype.Controller;

import com.users.ejb.BaseTypecode;
import com.users.ejb.EjbEnum;
import com.watch.ejb.UserFeedback;
import com.zhuika.dao.IBindPhoneDao;
import com.zhuika.dao.IFeedbackDao;
import com.zhuika.dao.IInfoDao;
import com.zhuika.dao.ISerialNumberDao;
import com.zhuika.discard.DiscardClient;
import com.zhuika.entity.BindPhone;
import com.zhuika.entity.Info;
import com.zhuika.entity.SerialNumber;
import com.zhuika.factory.DAOException;
import com.zhuika.util.Jsonconf;
import com.zhuika.util.Tools;

@Controller
public class FeedbackAction extends BaseAction implements ServletRequestAware,ServletResponseAware{
	private static final long serialVersionUID = 1L;
    private HttpServletRequest request;
    private HttpServletResponse response;
    @Resource
    private IFeedbackDao feedbackDao;

     
	public void setFeedbackDao(IFeedbackDao feedbackDao) {
		this.feedbackDao = feedbackDao;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response=response;	
	}
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;	
	}
    public void getCatetory() throws DAOException{
    	PrintWriter out=null;
    	JSONObject json=new JSONObject();
    	try {
    		request.setCharacterEncoding("utf-8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8"); 
			out=response.getWriter();			
			HashMap<String, String> map = new HashMap<String, String>();	
			map.put("FCategory", String.valueOf(EjbEnum.ParaCategory.FeedBackCatetory.value()));
			
			List<BaseTypecode> listCategory = this.feedbackDao.getCatetory(map) ;			
			if(listCategory!=null && listCategory.size()>0){

				json.put("state", 1);
				json.put("info", "成功查询类别");
				JsonConfig jconfig = Jsonconf.getCommonJsonConf();
				jconfig.setExcludes(new String[] { "fcategory","fincreaseid","forder","fremark","ftypeid","faddtime","fvalue1","fvalue2","fvalue3","fvalue4","fvalue5"}); 
				
				json.put("data",  JSONArray.fromObject(listCategory,jconfig));
			}
			else
			{
				json.put("state", 2);
				json.put("info", "无相关留言类别");
				json.put("data", null);
			}
		} catch (Exception e) {
			json.put("state", -1);	
			json.put("info", e.getMessage());	
			json.put("data", null);
		} finally{
			out.print(json);
			out.close();
		}
    }
    public void postFeedBack() throws DAOException{
    	PrintWriter out=null;
    	JSONObject json=new JSONObject();
    	try {
    		request.setCharacterEncoding("utf-8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8"); 
			out=response.getWriter();
			String category=request.getParameter("category");
			String FUserIDStr = request.getParameter("usrid");
			String content = Tools.DecodeUtf8String(request.getParameter("content"));
			
			UserFeedback userFeedbackInfo = new UserFeedback();
			userFeedbackInfo.setFcategory(Integer.parseInt(category));
			userFeedbackInfo.setFuseridstr(FUserIDStr);
			userFeedbackInfo.setFcontent(content);
			
			this.feedbackDao.Add(userFeedbackInfo);
			
			json.put("state", 1);
			json.put("info", "成功留言");		
		} catch (Exception e) {
			json.put("state", -1);	
			json.put("info", e.getMessage());		
		} finally{
			out.print(json);
			out.close();
		}
    }
 
    public void getFeedBack() throws DAOException{
    	PrintWriter out=null;
    	JSONObject json=new JSONObject();
    	try {
    		request.setCharacterEncoding("utf-8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8"); 
			out=response.getWriter();
			String category=request.getParameter("category");
			String usrid = request.getParameter("usrid");
			

			HashMap<String, String> map = new HashMap<String, String>();	
			map.put("category", category);
			map.put("usid", usrid);
			
			List<UserFeedback> listFeedbacks=this.feedbackDao.ListUserFeedback(0, 100, map);
			
			json.put("state", 1);
			json.put("info", "获取留言");		
			
			JsonConfig jconfig = Jsonconf.getCommonJsonConf();
			jconfig.setExcludes(new String[] { "fdatastatus","ffieldstatus","fincreaseid","fisanswer","fisdelete","forder","fparentid"}); 
			
			json.put("data",  JSONArray.fromObject(listFeedbacks,jconfig));
			
	
		} catch (Exception e) {
			json.put("state", -1);	
			json.put("info", e.getMessage());	
			json.put("data", null);
		} finally{
			out.print(json);
			out.close();
		}
    }
}

