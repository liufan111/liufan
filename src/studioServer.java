import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sss.idao.DAOFactory;
import sss.model.Studio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.*;

/**
 * Created by zxw on 17-12-18.
 */
@WebServlet(name = "studio",urlPatterns = "/api/studio")
public class studioServer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset = utf-8");
        request.setCharacterEncoding("UTF-8");
        Writer out = response.getWriter();
        JSONObject json = new JSONObject();
        Studio s = new Studio();
        try {
            s.setStudio_name(request.getParameter("studio_name"));
            s.setStudio_row_count(Integer.valueOf(request.getParameter("studio_rows")));
            s.setStudio_col_count(Integer.valueOf(request.getParameter("studio_cols")));
            s.setStudio_introduction(request.getParameter("studio_detial"));
            s.setStudio_flag(Integer.valueOf(request.getParameter("flag")));
        }catch (Exception e){
            System.out.println("演出厅信息获取失败");
            json.put("state",false);
            out.write(json.toString());
            return;
        }
        if (DAOFactory.createStudioDAO().insert(s)){
            System.out.println("演出厅插入数据成功");

            System.out.println(s);
            json.put("state",true);
            System.out.println(s.getStudio_id());
//            json.put("studio_id",s.getStudio_id());
            out.write(json.toString());
        }else {
            System.out.println("演出厅插入数据失败");
            json.put("state",false);
            out.write(json.toString());
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json; charset=utf-8");
        String []arr;
        JSONObject json = new JSONObject();
        JSONArray jsonarr;
        JSONArray all = new JSONArray();
        ArrayList<String[]> list1 = new ArrayList<String[]>();
        int page = 1;
        int nums = 10;
        Writer out = response.getWriter();
        int id;
        if(request.getParameter("id") != null){
            try {
                id = Integer.valueOf(request.getParameter("id"));
            }catch (Exception e){
                json.put("state",false);
                out.write(json.toString());
                return;
            }
            Studio s = DAOFactory.createStudioDAO().findStudioById(id);
            if(s!= null){
                json.put("status",true);
                jsonarr = new JSONArray();
                jsonarr.add(String.valueOf(s.getStudio_id()));
                jsonarr.add(s.getStudio_name());
                jsonarr.add(String.valueOf(s.getStudio_row_count()));
                jsonarr.add(String.valueOf(s.getStudio_col_count()));
                jsonarr.add(s.getStudio_introduction());
                jsonarr.add(String.valueOf(s.getStudio_flag()));
                json.put("object",jsonarr);
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
            int offset = (page -1) *nums;
            ArrayList<Studio>list = null;
            String name = request.getParameter("name");
            if(name == null || name.equals(""))
                list = DAOFactory.createStudioDAO().findStudioAll(offset,nums);
            else
                list = DAOFactory.createStudioDAO().findStudioByname(name,offset,nums);

            if (list.size() == 0){
                json.put("status",false);
                out.write(json.toString());
                return;
            }
            for (Studio s : list
                    ){
                jsonarr = new JSONArray();
                jsonarr.add(String.valueOf(s.getStudio_id()));
                jsonarr.add(s.getStudio_name());
                jsonarr.add(String.valueOf(s.getStudio_row_count()));
                jsonarr.add(String.valueOf(s.getStudio_col_count()));
                jsonarr.add(s.getStudio_introduction());
                jsonarr.add(String.valueOf(s.getStudio_flag()));

                all.add(jsonarr);
            }
            json.put("status",true);
            json.put("object", all);

            out.write(json.toString());
        }
    }
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json; charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        Writer out = response.getWriter();
        JSONObject json = new JSONObject();
        int id;

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
                hm.put(z[0], z[1]);
            }
        }catch (Exception e){

        }


        try {
            id = Integer.valueOf(hm.get("id"));

        }
        catch (java.lang.Exception e){
            System.out.print("err");
            json.put("status",false);
            out.write(json.toString());
            return ;
        }
        if(DAOFactory.createStudioDAO().delete(id)){
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

        Studio stu = new Studio();
        stu.setStudio_introduction("");
        //stu.setStudio_flag(1);

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
            stu.setStudio_id(Integer.valueOf(hm.get("id")));
            stu.setStudio_name(hm.get("studio_name"));
            stu.setStudio_row_count(Integer.valueOf(hm.get("studio_rows")));
            stu.setStudio_col_count(Integer.valueOf(hm.get("studio_cols")));
            stu.setStudio_flag(Integer.valueOf(hm.get("flag")));
        }catch (Exception e){
            e.printStackTrace();
            json.put("state",false);
            out.write(json.toString());
            return;
        }

        if(hm.get("studio_detial") != null){
            stu.setStudio_introduction(hm.get("studio_detial"));
        }

        if(DAOFactory.createStudioDAO().update(stu)){
            json.put("state",true);
        }else{
            json.put("state",false);
        }
        out.write(json.toString());


    }
}
