import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sss.idao.DAOFactory;
import sss.model.Sale;
import sss.model.Sale_item;
import sss.model.Seat;

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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(name = "first",urlPatterns = "/api/first")
public class firstServer extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String []arr;
        JSONObject json = new JSONObject();
        JSONArray jsonarr;
        JSONArray all = new JSONArray();
        Writer out = response.getWriter();
        ArrayList<String[]> list1 = new ArrayList<String[]>();
        int emp_id = Integer.valueOf(request.getParameter("emp_id"));
        Timestamp sale_time = Timestamp.valueOf(request.getParameter("sale_time"));
        BigDecimal sale_payment = BigDecimal.valueOf(Integer.valueOf(request.getParameter("sale_payment")));
        BigDecimal sale_change = BigDecimal.valueOf(Integer.valueOf(request.getParameter("sale_change")));
        int sale_type = Integer.valueOf(request.getParameter("sale_type"));
        int sale_status = Integer.valueOf(request.getParameter("sale_status"));
        //System.out.println("到了：aaaaaaaaaaaaaaaaa"+(request.getParameter("id")));
        ArrayList<Sale> list = null;
        list = DAOFactory.createSaleDAO().findSaleId(emp_id,sale_time,sale_payment,sale_change,sale_type,sale_status);
        //System.out.println("llllllllllllllllllllllllll"+list);
        if (list.size() == 0){
            json.put("status",false);
            out.write(json.toString());
            return;
        }
        //System.out.println(list.size());
        for (Sale s:list){
            jsonarr = new JSONArray();
            jsonarr.add(s.getSale_ID());

            all.add(jsonarr);
        }

        json.put("status",true);
        json.put("object",all);

        out.write(json.toString());
    }
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json charset=utf-8");
        req.setCharacterEncoding("utf-8");
        Writer out = resp.getWriter();
        JSONObject json = new JSONObject();

        Sale_item si = new Sale_item();

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
            si.setTicket_id(Integer.valueOf(hm.get("ticket_id")));
            si.setSale_ID(Integer.valueOf(hm.get("sale_id")));
            si.setSale_item_price(Integer.valueOf(hm.get("sale_payment")));

            System.out.println(si.getTicket_id()+","+si.getSale_ID()+","+si.getSale_item_price());


        } catch (Exception e) {
            e.printStackTrace();
            json.put("status", false);
            out.write(json.toString());
            return;
        }
        if (DAOFactory.createSale_itemDAO().insert(si)) {
            System.out.println("插入销售数据成功！");
            json.put("status", true);
        }
        else {
            json.put("status", false);
        }
        out.write(json.toString());
    }
}
