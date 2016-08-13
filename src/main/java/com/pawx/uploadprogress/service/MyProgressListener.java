package com.pawx.uploadprogress.service;
import javax.servlet.http.HttpSession;  
  


import org.apache.commons.fileupload.ProgressListener;  
import org.springframework.stereotype.Component;  
  
@Component  
public class MyProgressListener implements ProgressListener {  
      
    private HttpSession session;  
  
    public void setSession(HttpSession session){  
        this.session=session;  
        ProgressEntity status = new ProgressEntity();  
        session.setAttribute("status", status);  
    }  
  
    /* 
     * pBytesRead 到目前为止读取文件的比特数 pContentLength 文件总大小 pItems 目前正在读取第几个文件 
     */  
    public void update(long pBytesRead, long pContentLength, int pItems) {  
        ProgressEntity status = (ProgressEntity) session.getAttribute("status");  
        status.setpBytesRead(pBytesRead);  
        status.setpContentLength(pContentLength);  
        status.setpItems(pItems);  
        System.out.println(status.toString());
    }  
}  