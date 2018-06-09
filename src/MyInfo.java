import jdk.nashorn.api.scripting.AbstractJSObject;
import jdk.nashorn.api.scripting.JSObject;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by zxw on 17-11-19.
 */
@WebServlet(name = "MyInfo",urlPatterns = "/MyInfo")
public class MyInfo extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        HttpSession session = request.getSession(false);
        JSONObject obj = new JSONObject();
        if(session == null){
            obj.put("login",false);
        }else{
            HashMap<String,String> mess = new HashMap<String,String >();
            mess.put("用户编号",session.getAttribute("emp_no").toString());
            mess.put("用户姓名",session.getAttribute("emp_name").toString());
            mess.put("用户联系方式",session.getAttribute("emp_tel_num").toString() );
            mess.put("住址",session.getAttribute("emp_addr").toString());
            mess.put("邮件",session.getAttribute("emp_email").toString());

            obj.put("login",true);
            obj.put("emp_no",session.getAttribute("emp_no"));
            obj.put("emp_name",session.getAttribute("emp_name"));
            obj.put("head_path",session.getAttribute("head_path"));
            obj.put("type",session.getAttribute("type"));
            obj.put("mess",mess);
            System.out.println(obj.toString());
        }
        Writer out = response.getWriter();
        out.write(obj.toString());
    }
}
