package sss.api;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
import org.json.simple.JSONObject;
import sss.idao.DAOFactory;
import sss.model.Play;
import sss.model.User;

@WebServlet(name = "UserHead",urlPatterns = "/api/new_head")
public class UserHeadServer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SmartUpload su = new SmartUpload();
        JspFactory jf = JspFactory.getDefaultFactory();
        PageContext pc = jf.getPageContext(this,request,response,"",true,8192,true);

        su.initialize(pc);
        su.setMaxFileSize(1024*1024*1024);

        try{
            su.upload();

        }catch (SmartUploadException e){
            e.printStackTrace();
        }

        com.jspsmart.upload.File myF = su.getFiles().getFile(0);
        if(!myF.isMissing()){
            String file_name = myF.getFileName();
            String kuo_name = file_name.substring(file_name.lastIndexOf('.'),file_name.length());
            String new_path = getServletContext().getRealPath("/")+"img/UserHead/";
            File aadir = new File(new_path);
            if(!aadir.exists()){
                aadir.mkdirs();
            }
            String user_no = (String)request.getSession(false).getAttribute("emp_no");
            String trace = new_path+user_no+kuo_name;
            System.out.println(trace);

            try{
                myF.saveAs(trace,su.SAVE_PHYSICAL);
                User user = DAOFactory.createUserDAO().findUserByNo(user_no);
                user.setHead_path("/img/UserHead/"+user_no+kuo_name);
                DAOFactory.createUserDAO().update(user);
                request.getSession(false).setAttribute("head_path",user.getHead_path());
            }catch (SmartUploadException e){
                e.printStackTrace();
            }
            response.sendRedirect("/admin/aboutMe.jsp");
        }


    }

}