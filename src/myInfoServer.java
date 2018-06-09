import org.json.simple.JSONObject;
import sss.model.Employee;
import sss.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by zxw on 17-12-19.
 */
@WebServlet(name = "myInfo",urlPatterns = "/api/myInfo")
public class myInfoServer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset = utf-8");
        request.setCharacterEncoding("UTF-8");
        Writer out = response.getWriter();
        JSONObject json = new JSONObject();
        User user = new User();
        Employee emp = new Employee();
        try {
            //用户名
            emp.setEmp_name(request.getParameter("emp_name"));

        }catch (Exception e){

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
