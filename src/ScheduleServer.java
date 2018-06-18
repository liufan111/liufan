import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sss.idao.DAOFactory;
import sss.model.Schedule;
import sss.model.Seat;
import sss.model.Ticket;

import javax.jnlp.IntegrationService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;
@WebServlet(name = "schedule" ,urlPatterns = "/api/schedule")
public class ScheduleServer extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset = utf-8");
        request.setCharacterEncoding("UTF-8");
        Writer out = response.getWriter();
        JSONObject json = new JSONObject();
        Schedule s = new Schedule();
        try {

            s.setPlay_id(Integer.valueOf(request.getParameter("play_id")));
            s.setStudio_id(Integer.valueOf(request.getParameter("studio_id")));
            s.setSched_time(Timestamp.valueOf(request.getParameter("sched_time")));
            s.setSched_ticket_price(new BigDecimal(request.getParameter("sched_ticket_price")));
            System.out.println(s.getPlay_id()+"   "+s.getStudio_id()+"  "+s.getSched_time()+ "   "+s.getSched_ticket_price());
        }catch (Exception e){
            System.out.println("信息获取失败"+e);
            json.put("state",false);
            out.write(json.toString());
            return;
        }
        int bb=DAOFactory.createScheduleDAO().insert(s);
        System.out.println("返回id"+bb);
        if (bb!=-1){
            System.out.println("演出计划插入数据成功！");
            json.put("state",true);
            out.write(json.toString());
            System.out.println("演出厅id"+s.getStudio_id());
            ArrayList<Seat> st=DAOFactory.createSeatDAO().findSeatStateByStudioId(s.getStudio_id());
            if (st.size() == 0){
               System.out.println("生票失败！");
                out.write(json.toString());
                return;
            }
            for (Seat t : st) {
                System.out.println("座位信息");
                Ticket tt = new Ticket();
                tt.setSched_id(bb);
                System.out.println("演出计划id"+bb);
                tt.setSeat_id(t.getSeat_id());
                tt.setTicket_price(s.getSched_ticket_price());
                if(t.getSeat_status()==1) {
                    tt.setTicket_status(0);
                }else{
                    tt.setTicket_status(-1);
                }
                if(DAOFactory.createTicketDAO().insert(tt)){
                    System.out.println("票已生成");
                }
            }
        }else {
            System.out.println("演出计划插入数据失败");
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
                System.out.println("传进来的id"+id);
            }catch (Exception e){
                json.put("state",false);
                System.out.println("错了");
                out.write(json.toString());
                return;
            }
            ArrayList<Schedule> s = DAOFactory.createScheduleDAO().findScheduleById(id);
            if (s.size() == 0){
                json.put("status",false);
                out.write(json.toString());
                return;
            }
            for (Schedule sch : s
                    ){
                jsonarr = new JSONArray();
                jsonarr.add(String.valueOf(sch.getSched_id()));
                jsonarr.add(String.valueOf(sch.getStudio_id()));
                jsonarr.add(String.valueOf(sch.getPlay_id()));
                jsonarr.add(String.valueOf(sch.getSched_time()));
                jsonarr.add(String.valueOf(sch.getSched_ticket_price()));

                all.add(jsonarr);
            }
            json.put("status",true);
            json.put("object",all);
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
            ArrayList<Schedule>list = null;
            String name = request.getParameter("name");
            //System.out.println(name+1);
            if(name == null || name.equals("")){

                System.out.println("查找全部的");
                list = DAOFactory.createScheduleDAO().findScheduleAll(offset, nums);
            }
            else {
                list = DAOFactory.createScheduleDAO().findStudioByiid(Integer.valueOf(name), offset, nums);
            }
            if (list.size() == 0){
                json.put("status",false);
                out.write(json.toString());
                return;
            }
            for (Schedule sch : list){
                jsonarr = new JSONArray();
                jsonarr.add(String.valueOf(sch.getSched_id()));
                jsonarr.add(String.valueOf(sch.getStudio_id()));
                jsonarr.add(String.valueOf(sch.getPlay_id()));
                jsonarr.add(String.valueOf(sch.getSched_time()));
                jsonarr.add(String.valueOf(sch.getSched_ticket_price()));

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
        int  id;

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
        catch (Exception e){
            System.out.print("err");
            json.put("status",false);
            out.write(json.toString());
            return ;
        }
        if(DAOFactory.createTicketDAO().delete(id)) {
            if (DAOFactory.createScheduleDAO().delete(id)) {
                json.put("status", true);
                out.write(json.toString());
            } else {
                json.put("status", false);
                System.out.print("serr");
                out.write(json.toString());
            }
        }
        else{
            System.out.println("票未删除!");
        }
    }
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json; charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        Writer out = response.getWriter();
        JSONObject json = new JSONObject();
        Schedule sch = new Schedule();
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
            sch.setSched_id(Integer.valueOf(hm.get("sched_id")));
            sch.setStudio_id(Integer.valueOf(hm.get("studio_id")));
            sch.setPlay_id(Integer.valueOf(hm.get("play_id")));
            sch.setSched_time(Timestamp.valueOf(hm.get("sched_time")));
            sch.setSched_ticket_price(new BigDecimal(hm.get("sched_ticket_price")));
            System.out.println("sched_id"+sch.getSched_id()+"sched_price"+sch.getSched_ticket_price());
        }catch (Exception e){
            e.printStackTrace();
            json.put("state",false);
            out.write(json.toString());
            return;
        }
        if(DAOFactory.createScheduleDAO().update(sch)){
            json.put("state",true);
            System.out.println("更新成功");
            if(DAOFactory.createTicketDAO().delete(sch.getSched_id())){
                System.out.println("原票已删");
                ArrayList<Seat> st=DAOFactory.createSeatDAO().findSeatStateByStudioId(sch.getStudio_id());
                if (st.size() == 0){
                    System.out.println("生票失败！");
                    out.write(json.toString());
                    return;
                }
                for (Seat t : st) {
                    System.out.println("座位信息");
                    Ticket tt = new Ticket();
                    tt.setSched_id(sch.getSched_id());
                    tt.setSeat_id(t.getSeat_id());
                    tt.setTicket_price(sch.getSched_ticket_price());
                    if (t.getSeat_status() == 1) {
                        tt.setTicket_status(0);
                    } else {
                        tt.setTicket_status(-1);
                    }
                    if (DAOFactory.createTicketDAO().insert(tt)) {
                        System.out.println("票已生成");
                    }
                }
            }
        }else{
            json.put("state",false);
            System.out.println("无法插入");
        }
        out.write(json.toString());
    }
}


