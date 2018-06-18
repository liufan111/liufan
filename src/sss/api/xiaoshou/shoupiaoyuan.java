package sss.api.xiaoshou;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sss.idao.DAOFactory;
import sss.model.Employee;
import sss.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zxw on 17-12-19.
 */
@WebServlet(name = "shoupiaoyuan",urlPatterns = "/api/xiaoshou/shoupiaoyuan")
public class shoupiaoyuan extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        String []arr;
        JSONObject json = new JSONObject();
        JSONArray jsonArr;
        JSONArray all = new JSONArray();
        ArrayList <String[]> list2 = new ArrayList<String[]>();
        int page = 1;
        int nums = 10;
        Writer out = response.getWriter();
        String id;
        ArrayList<User> list = null;
        list = DAOFactory.createUserDAO().findUserShoupiao();
        if (list.size() == 0){
            json.put("status",false);
            out.write(json.toString());
            return;
        }
        for (User user : list
                ){
            jsonArr = new JSONArray();
            jsonArr.add(Integer.parseInt(user.getEmp_no()));
            jsonArr.add(user.getEmp_pass());
            jsonArr.add(user.getType());
            all.add(jsonArr);
        }
        json.put("status",true);
        json.put("object",all);
        out.write(json.toString());
    }

}
