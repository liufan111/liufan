import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sss.idao.DAOFactory;
import sss.model.Sale;
import sss.model.Sale_item;
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
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(name = "jing",urlPatterns = "/api/jing")
public class jingServer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        Writer out = response.getWriter();
        JSONObject json = new JSONObject();
        Ticket t = new Ticket();
        try {
            t.setTicket_id(Integer.valueOf(request.getParameter("ticket_id")));
            t.setSeat_id(Integer.valueOf(request.getParameter("seat_id")));
            t.setSched_id(Integer.valueOf(request.getParameter("sched_id")));
            t.setTicket_price(BigDecimal.valueOf(Integer.valueOf(request.getParameter("ticket_price"))));
            t.setTicket_status(Integer.valueOf(request.getParameter("ticket_status")));
            t.setTicket_locked_time(Timestamp.valueOf(request.getParameter("ticket_locked_time")));
        } catch (Exception e) {
            System.out.println("信息获取失败！");
            json.put("state", false);
            out.write(json.toString());
            return;
        }
        if (DAOFactory.createTicketDAO().insert(t)) {
            json.put("state", true);
            out.write(json.toString());
        } else {
            System.out.println("插入数据失败！");
            json.put("state", false);
            out.write(json.toString());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json charset=utf-8");
        String[] arr;
        JSONObject json = new JSONObject();
        JSONArray jsonarr;
        JSONArray all = new JSONArray();
        ArrayList<String[]> list1 = new ArrayList<String[]>();
        int page = 1;
        int nums = 100;
        Writer out = resp.getWriter();
        int id;
        if (req.getParameter("id") != null) {
            //System.out.println("aaaaaaaaaaaaaaaaa"+(req.getParameter("id")));
            try {
                id = Integer.valueOf(req.getParameter("id"));
            } catch (Exception e) {
                json.put("status", false);
                out.write(json.toString());
                return;
            }
            Ticket t = DAOFactory.createTicketDAO().findTicketById(id);
            if (t != null) {
                json.put("status", true);
                jsonarr = new JSONArray();
                jsonarr.add(String.valueOf(t.getTicket_id()));
                jsonarr.add(String.valueOf(t.getSeat_id()));
                jsonarr.add(String.valueOf(t.getSched_id()));
                jsonarr.add(String.valueOf(t.getTicket_price()));
                jsonarr.add(String.valueOf(t.getTicket_status()));
                jsonarr.add(String.valueOf(t.getTicket_locked_time()));

                json.put("object", jsonarr);

            } else {
                json.put("status", false);
            }
            out.write(json.toString());
        } else {
            //System.out.println("dddddddddddd");
            try {
                page = Integer.valueOf(req.getParameter("page"));
            } catch (Exception e) {

            }
            try {
                nums = Integer.valueOf(req.getParameter("nums"));
            } catch (Exception e) {

            }
            int offset = (page - 1) * nums;
            ArrayList<Ticket> list = null;
            String name = req.getParameter("name");
            //System.out.println(name);
            if (name == null || name.equals("")) {
                //System.out.println("aaaaaaaaaaaaaaa");
                list = DAOFactory.createTicketDAO().findTicketAll(offset, nums);
            } else {
                list = DAOFactory.createTicketDAO().findTicketByname(name, offset, nums);
            }
            if (list.size() == 0) {
                json.put("status", false);
                out.write(json.toString());
                return;
            }
            for (Ticket t : list) {
                jsonarr = new JSONArray();
                jsonarr.add(String.valueOf(t.getTicket_id()));
                jsonarr.add(String.valueOf(t.getSeat_id()));
                jsonarr.add(String.valueOf(t.getSched_id()));
                jsonarr.add(String.valueOf(t.getTicket_price()));
                jsonarr.add(String.valueOf(t.getTicket_status()));
                jsonarr.add(String.valueOf(t.getTicket_locked_time()));

                all.add(jsonarr);
            }
            json.put("status", true);
            json.put("object", all);

            out.write(json.toString());
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json charset=utf-8");
        request.setCharacterEncoding("utf-8");
        Writer out = response.getWriter();
        JSONObject json = new JSONObject();
        int id;

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String s = null;
        String data = "";
        while ((s = br.readLine()) != null) {
            data = data.concat(s).concat("\n");
        }
        data = data.substring(0, data.length() - 1);
        System.out.println(data);
        HashMap<String, String> hm = new HashMap<String, String>();
        try {
            String listp[] = data.split("&");
            for (String x : listp) {
                String z[] = x.split("=");
                hm.put(z[0], z[1]);
            }
        } catch (Exception e) {
        }
        try {
            id = Integer.valueOf(hm.get("id"));

        } catch (Exception e) {
            System.out.println("err");
            json.put("status", false);
            out.write(json.toString());
            return;
        }
        if (DAOFactory.createTicketDAO().delete(id)) {
            json.put("status", true);
            out.write(json.toString());
        } else {
            json.put("status", false);
            System.out.println("her");
            out.write(json.toString());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json charset=utf-8");
        req.setCharacterEncoding("utf-8");
        Writer out = resp.getWriter();
        JSONObject json = new JSONObject();

        Ticket t = new Ticket();
        Sale sale = new Sale();
        //Sale_item si = new Sale_item();

        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String s = null;
        String data = "";
        while ((s = br.readLine()) != null) {
            data = data.concat(s).concat("\n");
        }
        data = data.substring(0, data.length() - 1);
        System.out.println(data);
        HashMap<String, String> hm = new HashMap<String, String>();
        try {
            String listp[] = data.split("&");
            for (String x : listp) {
                String z[] = x.split("=");
                if (z.length == 1) {
                    continue;
                }
                hm.put(z[0], z[1]);
            }
        } catch (Exception e) {
        }
        try {
            //t.setTicket_id(Integer.valueOf(hm.get("ticket_id")));
            t.setSeat_id(Integer.valueOf(hm.get("seat_id")));
            t.setSched_id(Integer.valueOf(hm.get("sched_id")));
            //t.setTicket_price(BigDecimal.valueOf(Integer.valueOf(hm.get("ticket_price"))));
            t.setTicket_status(Integer.valueOf(hm.get("ticket_status")));
            t.setTicket_locked_time(Timestamp.valueOf(hm.get("sale_time")));

            System.out.println(t.getSeat_id() + "," + t.getSched_id() + "," + t.getTicket_status());

            sale.setEmp_id(Integer.valueOf(hm.get("emp_id")));
            sale.setSale_time(Timestamp.valueOf(hm.get("sale_time")));
            sale.setSale_payment(new BigDecimal(hm.get("sale_payment")));
            sale.setSale_change(new BigDecimal(hm.get("sale_change")));
            sale.setSale_type(Integer.valueOf(hm.get("sale_type")));
            sale.setSale_status(Integer.valueOf(hm.get("sale_status")));

            System.out.println(sale.getEmp_id() + "," + sale.getSale_time() + "," + sale.getSale_payment() + ","
                    + sale.getSale_change() + "," + sale.getSale_type() + "," + sale.getSale_status());

//            si.setTicket_id(Integer.valueOf(hm.get("ticket_id")));
//            si.setSale_ID(Integer.valueOf(hm.get("emp_id")));
//            si.setSale_item_price(Integer.valueOf(hm.get("sale_payment")));
//
//            System.out.println(si.getTicket_id()+","+si.getSale_ID()+","+si.getSale_item_price());


        } catch (Exception e) {
            e.printStackTrace();
            json.put("status", false);
            out.write(json.toString());
            return;
        }
        if (DAOFactory.createTicketDAO().updateTicket(t)) {
            System.out.println("更改座位状态成功！");
            json.put("status", true);
            DAOFactory.createSaleDAO().insert(sale);
        }
        else {
            json.put("status", false);
        }
        out.write(json.toString());
    }
}
