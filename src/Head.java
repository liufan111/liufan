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

@WebServlet(name = "Head",urlPatterns = "/head")
public class Head extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SmartUpload su = new SmartUpload();
        JspFactory jf = JspFactory.getDefaultFactory();
        PageContext pc = jf.getPageContext(this,request,response,"",true,8192,true);

        su.initialize(pc);
        su.setMaxFileSize(1024*1024);

        try{
            su.upload();

        }catch (SmartUploadException e){
            e.printStackTrace();
        }

        com.jspsmart.upload.File myF = su.getFiles().getFile(0);
        if(!myF.isMissing()){
            String play_no=su.getRequest().getParameter("play_id");
            System.out.println("play_id"+play_no);
            System.out.println("上传图片");
            String file_name = myF.getFileName();
            System.out.println("filename "+file_name);
            String kuo_name = file_name.substring(file_name.lastIndexOf('.'),file_name.length());
            System.out.println("kuo_name "+kuo_name);
            String new_path = getServletContext().getRealPath("/")+"img/";
            System.out.println("new_path "+new_path);
            File aadir = new File(new_path);
            if(!aadir.exists()){
                aadir.mkdirs();
            }
            //String play_no=su.getRequest().getParameter("play_id");
            int play_id=Integer.parseInt(play_no);
            System.out.println("play_id"+play_id);
            String trace = new_path+play_id+kuo_name;
            System.out.println(trace);

            try{
                System.out.println("进入数据库");
                myF.saveAs(trace,su.SAVE_PHYSICAL);
                Play play = DAOFactory.createPlayDAO().findPlayById(play_id);
                //User user = DAOFactory.createUserDAO().findUserByNo(user_no);
                play.setPlay_image("/img/"+play_id+kuo_name);
                //user.setHead_path("/img/"+user_no+kuo_name);
                DAOFactory.createPlayDAO().update(play);
                //DAOFactory.createUserDAO().update(user);
                request.getSession(false).setAttribute("play_image",play.getPlay_image());
                //request.getSession(false).setAttribute("head_path",user.getHead_path());
            }catch (SmartUploadException e){
                e.printStackTrace();
            }
            response.sendRedirect("/mana/play.jsp");
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.getWriter().write("error");
        doPost(request, response);
    }
}

