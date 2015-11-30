package com.zhuika.service.impl;


import com.watch.ejb.LocElectfence;
import com.zhuika.dao.impl.LocElectfenceDaoIml;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import com.opensymphony.xwork2.ActionSupport; 

/**
 * <p>Title: ejb title </p>
 * <p>Description: t_loc_electfence Client Service 处理类</p>
 * @author yangqinxu 电话：137****5317
 * @version 1.0 时间  2015-6-25 10:12:11
 */

public class LocElectfenceImpl extends ActionSupport {	
    private static final long serialVersionUID = 1L;
    private LocElectfenceDaoIml  locelectfenceDao;  
    private Map<String,Object> dataMap;
    private String message;     
    private LocElectfence locelectfence;
    private int page; 
    private int errcode;
    
    public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
   
	public LocElectfence getLocelectfence() {
		return locelectfence;
	}

	public void setLocelectfence(LocElectfence rlocElectfenceInfo) {
		this.locelectfence =  rlocElectfenceInfo;
	}    
    
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}		    
    
	public Map<String, Object> getDataMap() {
		return dataMap;
	}
    
    public String getMessage() {  
        return message;  
    }  
  
    public void setMessage(String message) {  
        this.message = message;  
    }
    
    public LocElectfenceDaoIml getLocelectfenceDao() {
		return locelectfenceDao;
	}
    
	public void setLocelectfenceDao (LocElectfenceDaoIml rlocelectfenceDao) {		     
         this.locelectfenceDao = rlocelectfenceDao;
	}
       
	public String AddLocElectfence() {	
    
    try {
 	  if(this.locelectfence.getFlocfenid() == null || this.locelectfence.getFlocfenid().length() <= 0){		
		  this.locelectfenceDao.Add(this.locelectfence);	
		  this.message ="新增成功";
	  }
      else
	  {
          this.locelectfenceDao.Update(this.locelectfence);	
		  this.message ="更新成功";
	  }     
      } catch (RuntimeException ex) {
		  this.message = ex.getMessage();
		  this.errcode = ex.hashCode();
	   }
	  
	  dataMap = new HashMap<String, Object>();
      dataMap.put("errcode", this.errcode);
	  dataMap.put("success", true);
	  dataMap.put("message", this.message);
	  
	  return SUCCESS;   			
	}
  
	private HashMap<String, String> GetQueryMap()
	{				
        HashMap<String, String> map = new HashMap<String, String>();
		if(this.locelectfence!=null){
        if(this.locelectfence.getFlocfenid()!=null && !this.locelectfence.getFlocfenid().equals("")){
			map.put("flocfenid", this.locelectfence.getFlocfenid().toString());
         }
        if(this.locelectfence.getFincreaseid()!=null && !this.locelectfence.getFincreaseid().equals("")){
			map.put("fincreaseid", this.locelectfence.getFincreaseid().toString());
         }
        if(this.locelectfence.getFeltfenceid()!=null && !this.locelectfence.getFeltfenceid().equals("")){
			map.put("feltfenceid", this.locelectfence.getFeltfenceid().toString());
         }
        if(this.locelectfence.getFserialnumber()!=null && !this.locelectfence.getFserialnumber().equals("")){
			map.put("fserialnumber", this.locelectfence.getFserialnumber().toString());
         }
        if(this.locelectfence.getFdatastatus()!=null && !this.locelectfence.getFdatastatus().equals("")){
			map.put("fdatastatus", this.locelectfence.getFdatastatus().toString());
         }
        if(this.locelectfence.getFfieldstatus()!=null && !this.locelectfence.getFfieldstatus().equals("")){
			map.put("ffieldstatus", this.locelectfence.getFfieldstatus().toString());
         }
        if(this.locelectfence.getFeltlongitude()!=null && !this.locelectfence.getFeltlongitude().equals("")){
			map.put("feltlongitude", this.locelectfence.getFeltlongitude().toString());
         }
        if(this.locelectfence.getFeltlatitude()!=null && !this.locelectfence.getFeltlatitude().equals("")){
			map.put("feltlatitude", this.locelectfence.getFeltlatitude().toString());
         }
        if(this.locelectfence.getFeltscope()!=null && !this.locelectfence.getFeltscope().equals("")){
			map.put("feltscope", this.locelectfence.getFeltscope().toString());
         }
        if(this.locelectfence.getFeltaddress()!=null && !this.locelectfence.getFeltaddress().equals("")){
			map.put("feltaddress", this.locelectfence.getFeltaddress().toString());
         }
        if(this.locelectfence.getFlongitude()!=null && !this.locelectfence.getFlongitude().equals("")){
			map.put("flongitude", this.locelectfence.getFlongitude().toString());
         }
        if(this.locelectfence.getFlatitude()!=null && !this.locelectfence.getFlatitude().equals("")){
			map.put("flatitude", this.locelectfence.getFlatitude().toString());
         }
        if(this.locelectfence.getFaddress()!=null && !this.locelectfence.getFaddress().equals("")){
			map.put("faddress", this.locelectfence.getFaddress().toString());
         }
        if(this.locelectfence.getFdistance()!=null && !this.locelectfence.getFdistance().equals("")){
			map.put("fdistance", this.locelectfence.getFdistance().toString());
         }
        if(this.locelectfence.getFaddtime()!=null && !this.locelectfence.getFaddtime().equals("")){
			map.put("faddtime", this.locelectfence.getFaddtime().toString());
         }
        if(this.locelectfence.getFupdatetime()!=null && !this.locelectfence.getFupdatetime().equals("")){
			map.put("fupdatetime", this.locelectfence.getFupdatetime().toString());
         }
        if(this.locelectfence.getFremark()!=null && !this.locelectfence.getFremark().equals("")){
			map.put("fremark", this.locelectfence.getFremark().toString());
         }
        }
		
		return map;		
	}
    
	public String QueryLocElectfence() {
		dataMap = new HashMap<String, Object>();		
		int offset = this.getPage();		
		
		int pagesize = 10;
		
		if (offset>0){
			offset = (offset-1) * pagesize;
		}
        
        HashMap<String, String> map = this.GetQueryMap();
        
        try {
		List<LocElectfence>  listLocElectfence= this.locelectfenceDao.GetAll(offset, pagesize,map);
		
		int size = this.locelectfenceDao.GetCount(map);
				
		dataMap.put("rows", listLocElectfence);
		dataMap.put("total", size);
        
 	    } catch (RuntimeException ex) {
			this.message = ex.getMessage();
			this.errcode = ex.hashCode();
		}  
        
        dataMap.put("errcode", this.errcode);
		dataMap.put("message", this.message);
        
		return SUCCESS;
	} 
    
 	public String FindLocElectfence() {		    
		 if(this.locelectfence.getFlocfenid() == null || this.locelectfence.getFlocfenid().length() <= 0){		
			 
		 }else
		 {
			 this.locelectfence = this.locelectfenceDao.Find(this.locelectfence.getFlocfenid());
			 
			 dataMap = new HashMap<String, Object>();		
			 dataMap.put("locelectfence", this.locelectfence);
			 
		 }
	     return SUCCESS;
	    
		} 
        
        
    public String DeleteLocElectfence() {
        try {
		this.locelectfenceDao
				.Delete(this.locelectfence.getFlocfenid());

		this.message = "成功删除";
        } catch (RuntimeException ex) {
			this.message = ex.getMessage();
			this.errcode = ex.hashCode();
		}	
        
		dataMap = new HashMap<String, Object>();
		dataMap.put("id", this.locelectfence.getFlocfenid());
		dataMap.put("errcode", this.errcode);
        dataMap.put("success", true);
		dataMap.put("message", this.message);

		return SUCCESS;
	}
    
	public String List() {		 
	    return SUCCESS;
	}
}