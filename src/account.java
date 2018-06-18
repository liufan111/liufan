import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;

import sss.idao.DAOFactory;
import sss.idao.*;
import sss.model.*;
import org.json.simple.JSONObject;
/**
 * Created by zxw on 17-11-19.
 */
@WebServlet(name = "account",urlPatterns = "/account")
public class account extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json; charset=utf-8");
        String name = request.getParameter("username");
        String pass = request.getParameter("password");
        String select = request.getParameter("select");
        int sate;
        //管理员
        if(select.equals("administrator")){
            sate = 1;
        }
        //经理
        else if (select.equals("manager")){
            sate = 0;
        }
        //售票员
        else {
            sate = -1;
        }
        System.out.println(name+pass+sate);
        JSONObject json = new JSONObject();
        if(name==null || name.equals("") || pass == null || pass.equals("")){
            json.put("state",false);
            json.put("info","帐号密码不能为空！");
        }else{

            User user = new User();
            IUser iuser = DAOFactory.createUserDAO();
            user = iuser.findUserByNo(name);
            if(user != null && user.getEmp_pass().equals(pass)){
                if(sate == user.getType()){

                    json.put("state", true);
                    if (sate == 1){
                        json.put("href","/admin/user.jsp");
                    }
                    //经理访问的
                    else if (sate == 0){
                        json.put("href","/mana/schedule.jsp");
//                        json.put("href","/mana/easy/studio.jsp");
                    }
                    //售票员访问的
                    else {
                        json.put("href","/seller/playRoot.jsp");
                    }

                    request.getSession().invalidate();
                    Employee emp = DAOFactory.creatEmployeeDAO().findEmployeeByNo(name);
                    HttpSession session = request.getSession();
                    session.setAttribute("emp_no",name);
                    session.setAttribute("emp_name",emp.getEmp_name());
                    session.setAttribute("type",user.getType());
                    session.setAttribute("head_path",user.getHead_path());
                    session.setAttribute("emp_tel_num",emp.getEmp_tel_num());
                    session.setAttribute("emp_addr",emp.getEmp_addr());
                    session.setAttribute("emp_email",emp.getEmp_email());
                    String url = request.getParameter("url");
                    System.out.println(url);
                    if(url != null && !url.equals("")){
                        System.out.println("sdasdas");
                        json.put("href",url);
                    }
                }else {
                    json.put("state",false);
                    json.put("info","身份不正确");
                }

            }else {
                json.put("state",false);
                json.put("info","帐号密码不匹配！");
            }

        }

        Writer out = response.getWriter();
        out.write(json.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}