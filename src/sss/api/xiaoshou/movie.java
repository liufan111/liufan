package sss.api.xiaoshou;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sss.idao.DAOFactory;
import sss.model.Employee;
import sss.model.Play;
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
@WebServlet(name = "movie",urlPatterns = "/api/xiaoshou/movie")
public class movie extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        String []arr;
        JSONObject json = new JSONObject();
        JSONArray jsonArr;
        JSONArray all = new JSONArray();
        int page = 1;
        int nums = 10;
        Writer out = response.getWriter();
        String id;
        ArrayList<Play> list = null;
        list = DAOFactory.createPlayDAO().findPlayAll(0,100);
        if (list.size() == 0){
            json.put("status",false);
            out.write(json.toString());
            return;
        }
        for (Play play : list
                ){
            jsonArr = new JSONArray();
            jsonArr.add(play.getPlay_id());
            jsonArr.add(play.getPlay_name());
            all.add(jsonArr);
        }
        json.put("status",true);
        json.put("object",all);
        out.write(json.toString());
    }

}
