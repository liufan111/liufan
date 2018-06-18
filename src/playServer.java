import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sss.idao.DAOFactory;
import sss.model.Play;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(name="play",urlPatterns="/api/play")
public class playServer extends HttpServlet{

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset = utf-8");
        request.setCharacterEncoding("UTF-8");
        Writer out = response.getWriter();
        JSONObject json = new JSONObject();
        Play p = new Play();
        try {
            p.setPlay_type_id(Integer.valueOf(request.getParameter("play_type_id")));
            p.setPlay_lang_id(Integer.valueOf(request.getParameter("play_lang_id")));
            p.setPlay_name(request.getParameter("play_name"));
            p.setPlay_introduction(request.getParameter("play_introduction"));
            p.setPlay_image(request.getParameter("play_image"));
            p.setPlay_length(Integer.valueOf(request.getParameter("play_length")));
            p.setPlay_ticket_price(BigDecimal.valueOf(Integer.valueOf(request.getParameter("play_ticket_price"))));
            p.setPlay_status(Integer.valueOf(request.getParameter("play_status")));
        }catch (Exception e){
            System.out.println("剧目信息获取失败" + e);
            json.put("state",false);
            out.write(json.toString());
            return;
        }
        int aa = DAOFactory.createPlayDAO().insert(p);
        if (aa != -1){
            System.out.println("剧目插入数据成功");
            json.put("state",true);

            out.write(json.toString());
        }else {
            System.out.println("剧目插入数据失败");
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
            Play p = DAOFactory.createPlayDAO().findPlayById(id);
            if(p!= null){
                json.put("status",true);
                jsonarr = new JSONArray();
                jsonarr.add(String.valueOf(p.getPlay_id()));
                jsonarr.add(String.valueOf(p.getPlay_type_id()));
                jsonarr.add(String.valueOf(p.getPlay_lang_id()));
                jsonarr.add(p.getPlay_name());
                jsonarr.add(p.getPlay_introduction());
                jsonarr.add(p.getPlay_image());
                jsonarr.add(String.valueOf(p.getPlay_length()));
                jsonarr.add(String.valueOf(p.getPlay_ticket_price()));
                jsonarr.add(String.valueOf(p.getPlay_status()));
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
                ArrayList<Play>list = null;
                String name = request.getParameter("name");
                if(name == null || name.equals(""))
                    list = DAOFactory.createPlayDAO().findPlayAll(offset,nums);
                else
                    list = DAOFactory.createPlayDAO().findPlayByname(name,offset,nums);

                if (list.size() == 0){
                    json.put("status",false);
                    out.write(json.toString());
                    return;
                }
                for (Play p : list
                        ){
                    jsonarr = new JSONArray();
                    jsonarr.add(String.valueOf(p.getPlay_id()));
                    jsonarr.add(String.valueOf(p.getPlay_type_id()));
                    jsonarr.add(String.valueOf(p.getPlay_lang_id()));
                    jsonarr.add(p.getPlay_name());
                    jsonarr.add(p.getPlay_introduction());
                    jsonarr.add(p.getPlay_image());
                    jsonarr.add(String.valueOf(p.getPlay_length()));
                    jsonarr.add(String.valueOf(p.getPlay_ticket_price()));
                    jsonarr.add(String.valueOf(p.getPlay_status()));

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

//            id = Integer.valueOf(hm.get("id"));

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
                System.out.println("需要删除的id:"+id);


            }
            catch (java.lang.Exception e){
                System.out.print("err");
                json.put("status",false);
                out.write(json.toString());
                return ;
            }

            //请求

            if(DAOFactory.createPlayDAO().delete(id)){
                json.put("status",true);
                out.write(json.toString());
            }else{
                json.put("status",false);
                System.out.print("err");
                out.write(json.toString());
            }
        }
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/json; charset=utf-8");
            request.setCharacterEncoding("UTF-8");
            Writer out = response.getWriter();
            JSONObject json = new JSONObject();

            Play pl = new Play();
            pl.setPlay_introduction("");


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
                pl.setPlay_id(Integer.valueOf(hm.get("play_id")));
                pl.setPlay_type_id(Integer.valueOf(hm.get("play_type_id")));
                pl.setPlay_lang_id(Integer.valueOf(hm.get("play_lang_id")));
                pl.setPlay_name(hm.get("play_name"));
                pl.setPlay_introduction(hm.get("play_introduction"));
                pl.setPlay_image(hm.get("play_image"));
                pl.setPlay_length(Integer.valueOf(hm.get("play_length")));
                pl.setPlay_ticket_price(new BigDecimal(hm.get("play_ticket_price")));
//                pl.setPlay_ticket_price(BigDecimal.valueOf(hm.get("play_ticket_price")));
                pl.setPlay_status(Integer.valueOf(hm.get("play_status")));
                System.out.println(pl);

            }catch (Exception e){
                e.printStackTrace();
                json.put("state",false);
                out.write(json.toString());
                return;
            }
            if(hm.get("play_introduction") != null){
                pl.setPlay_introduction(hm.get("play_introduction"));
            }

            if(DAOFactory.createPlayDAO().update(pl)){
                json.put("state",true);
            }else{
                json.put("state",false);
            }
            out.write(json.toString());


        }

}
