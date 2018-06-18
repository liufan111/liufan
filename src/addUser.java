import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sss.idao.DAOFactory;
import sss.model.EmpUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

@WebServlet(name = "adduser",urlPatterns = "/api/adduser" )
public class addUser extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/json;charset = utf-8");
        request.setCharacterEncoding("UTF-8");
        Writer out = response.getWriter();
        JSONObject json = new JSONObject();
        JSONArray jsonArr;
        JSONArray all = new JSONArray();

        ArrayList<EmpUser> list = null;
        list = DAOFactory.createUserDAO().findEmpnotinUser();
        if (list.size() == 0){
            json.put("status",false);
            out.write(json.toString());
            return;
        }
        for (EmpUser user : list){
            jsonArr = new JSONArray();
            jsonArr.add(user.getEmp_no());
            jsonArr.add(user.getEmp_name());
            all.add(jsonArr);
        }
        json.put("status",true);
        json.put("object",all);
        out.write(json.toString());
    }
}

