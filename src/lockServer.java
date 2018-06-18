import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sss.idao.DAOFactory;
import sss.model.Studio;
import sss.model.Ticket;

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


@WebServlet(name = "lock",urlPatterns = "/api/lock")
public class lockServer extends HttpServlet{
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
            }catch (Exception e){

            }
            try {
                nums = Integer.valueOf(request.getParameter("nums"));
            }catch (Exception e){

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
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json charset=utf-8");
        req.setCharacterEncoding("utf-8");
        Writer out = resp.getWriter();
        JSONObject json = new JSONObject();

        Ticket t  = new Ticket();

        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String s = null;
        String data = "";
        while((s=br.readLine())!=null){
            data = data.concat(s).concat("\n");
        }
        data = data.substring(0,data.length()-1);
        System.out.println(data);
        HashMap<String,String> hm = new HashMap<String, String>();
        try{
            String listp[] = data.split("&");
            for (String x : listp){
                String z[] = x.split("=");
                if (z.length==1){
                    continue;
                }
                hm.put(z[0],z[1]);
            }
        }catch(Exception e){
        }
        try{
            //t.setTicket_id(Integer.valueOf(hm.get("ticket_id")));
            t.setSeat_id(Integer.valueOf(hm.get("seat_id")));
            t.setSched_id(Integer.valueOf(hm.get("sched_id")));
            //t.setTicket_price(BigDecimal.valueOf(Integer.valueOf(hm.get("ticket_price"))));
            t.setTicket_status(Integer.valueOf(hm.get("ticket_status")));
            //t.setTicket_locked_time(Date.valueOf(hm.get("ticket_locket_time")));
            System.out.println("修改所状态："+t.getSeat_id()+","+t.getSched_id()+","+t.getTicket_status());
        }catch(Exception e){
            e.printStackTrace();
            json.put("status",false);
            out.write(json.toString());
            return;
        }
        if (DAOFactory.createTicketDAO().updateTicket(t)){
            System.out.println("解锁成功！");
            json.put("status",true);
        }
        else{
            json.put("status",false);
        }
        out.write(json.toString());
    }
}
