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
@WebServlet(name = "user",urlPatterns = "/api/user" )
public class userServer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset = utf-8");
        request.setCharacterEncoding("UTF-8");
        Writer out = response.getWriter();
        JSONObject json = new JSONObject();
        User user = new User();
        try {
            user.setEmp_no(request.getParameter("emp_no"));
            user.setEmp_pass(request.getParameter("emp_pass"));
            user.setType(Integer.parseInt(request.getParameter("type")));
            user.setHead_path(request.getParameter("head_path"));
//
        }catch (Exception e){
            System.out.println("信息获取失败");
            json.put("state",false);
            out.write(json.toString());
            return;
        }
        if(DAOFactory.createUserDAO().insert(user)){
            json.put("state",true);
            out.write(json.toString());
        }else {
            System.out.println("插入数据失败");
            json.put("state",false);
            out.write(json.toString());
        }
    }
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
        if(request.getParameter("id") != null){
            try {
                id = request.getParameter("id");
            }catch (Exception e){
                json.put("state",false);
                out.write(json.toString());
                return;
            }
            User user = DAOFactory.createUserDAO().findUserById(id);
//            User user = DAOFactory.createUserDAO().findUserById(id);
            if(user != null){
                json.put("status",false);
                jsonArr = new JSONArray();
                jsonArr.add(user.getEmp_no());
                jsonArr.add(user.getEmp_pass());
                jsonArr.add(user.getType());
                json.put("object",jsonArr);
            }else {
                json.put("status",false);
            }
            out.write(json.toString());
        }else {
            try {
                page = Integer.valueOf(request.getParameter("page"));
            }catch (java.lang.Exception e){

            }
            try {
                nums = Integer.valueOf(request.getParameter("nums"));
            }catch (java.lang.Exception e){

            }
            int offset = (page-1) * nums;
            ArrayList<User> list = null;
            String name = request.getParameter("name");
            System.out.println(name+1);
            if(name == null || name.equals("")){
                System.out.println(name+"rtytrfg");
                list = DAOFactory.createUserDAO().findUserAll(offset,nums);
                System.out.println(name+"34343344343");
            }
            else{
//                list = DAOFactory.createUserDAO().findUserById(name);
//                System.out.println(name);
            }
            if (list.size() == 0){
                json.put("status",false);
                out.write(json.toString());
                return;
            }
            for (User user : list
                    ){
                jsonArr = new JSONArray();
                jsonArr.add(user.getEmp_no());
                jsonArr.add(user.getEmp_pass());
                jsonArr.add(user.getType());
                all.add(jsonArr);
            }
            json.put("status",true);
            json.put("object",all);
            out.write(json.toString());
        }
    }
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json; charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        Writer out = response.getWriter();
        JSONObject json = new JSONObject();
        String id;
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String s = null;
        String data = "";
        while((s = br.readLine()) != null) {
            data = data.concat(s).concat("\n");

        }
        data = data.substring(0,data.length()-1);
        System.out.println(data+"adfdfdfdfdfdfddfdfdfd");
        HashMap<String,String> hm = new HashMap<String,String>();
        try {
            String listp[] = data.split("&");
            for (String x : listp) {
                String z[] = x.split("=");
                hm.put(z[0], z[1]);
            }
        }catch (Exception e){

        }
        try {
            id = hm.get("id");
            System.out.println(id+"aaaaaaaaaaaaaaaaaaaaaa");
        }
        catch (java.lang.Exception e){
            System.out.print("err");
            json.put("status",false);
            out.write(json.toString());
            return ;
        }
        if(DAOFactory.createUserDAO().delete(id)){
            json.put("status",true);
            out.write(json.toString());
        }else{
            json.put("status",false);
            System.out.print("serr");
            out.write(json.toString());
        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json; charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        Writer out = response.getWriter();
        JSONObject json = new JSONObject();

        User user = new User();

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String s = null;
        String data = "";
        while((s = br.readLine()) != null) {
            data = data.concat(s).concat("\n");

        }
        data = data.substring(0,data.length()-1);
        System.out.println(data);
        HashMap<String,String> hm = new HashMap<String,String>();
        try {
            String listp[] = data.split("&");
            for (String x : listp) {
                String z[] = x.split("=");
                if(z.length == 1){
                    continue;
                }
                hm.put(z[0], z[1]);
            }
        }catch (Exception e){}

        try{
            user.setEmp_no(hm.get("emp_no"));
            user.setEmp_pass(hm.get("emp_pass"));
            user.setType(Integer.valueOf(hm.get("type")));
            user.setHead_path(hm.get("head_path"));
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("失败");
            json.put("state",false);
            out.write(json.toString());
            return;
        }
        if(DAOFactory.createUserDAO().update(user)){
            json.put("state",true);
        }else{
            json.put("state",false);
            System.out.println("无法插入");
        }
        out.write(json.toString());

    }
}
