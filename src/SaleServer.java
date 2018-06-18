import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sss.idao.DAOFactory;
import sss.model.Sale;

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

@WebServlet(name = "sale",urlPatterns = "/api/sale")
public class SaleServer extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        Writer out = response.getWriter();
        JSONObject json = new JSONObject();
        Sale sale = new Sale();
        try{
            sale.setSale_ID(Integer.valueOf(request.getParameter("sale_ID")));
            //System.out.println(sale.getSale_ID());
            sale.setEmp_id(Integer.valueOf(request.getParameter("emp_id")));
            //System.out.println(sale.getEmp_id());
            sale.setSale_time(Timestamp.valueOf(request.getParameter("sale_time")));
            //System.out.println(sale.getSale_time());
            sale.setSale_payment(BigDecimal.valueOf(Integer.valueOf(request.getParameter("sale_payment"))));
            //System.out.println(sale.getSale_payment());
            sale.setSale_change(BigDecimal.valueOf(Integer.valueOf(request.getParameter("sale_change"))));
            //System.out.println(sale.getSale_change());
            sale.setSale_type(Integer.valueOf(request.getParameter("flag1")));
            //System.out.println(sale.getSale_type());
            sale.setSale_status(Integer.valueOf(request.getParameter("flag2")));
            //System.out.println(sale.getSale_status());

        }catch (Exception e){
            System.out.println("信息获取失败！");
            json.put("state",false);
            out.write(json.toString());
            return;
        }
        if (DAOFactory.createSaleDAO().insert(sale)){
            json.put("state",true);
            out.write(json.toString());
        }else{
            System.out.println("插入数据失败！");
            json.put("state",false);
            out.write(json.toString());
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json charset=utf-8");
        String[] arr;
        JSONObject json = new JSONObject();
        JSONArray jsonarr;
        JSONArray all = new JSONArray();
        ArrayList<String[]> list1 = new ArrayList<String[]>();
        int page = 1;
        int nums = 10;
        Writer out = resp.getWriter();
        String id;
        if(req.getParameter("id")!=null){
            try{
                id = req.getParameter("id");

            }catch (Exception e){
                json.put("status",false);
                out.write(json.toString());
                return;
            }
            Sale s = DAOFactory.createSaleDAO().findSaleById(id);
            if (s!=null){
                json.put("status",false);
                jsonarr = new JSONArray();
                jsonarr.add(String.valueOf(s.getSale_ID()));
                jsonarr.add(String.valueOf(s.getEmp_id()));
                jsonarr.add(String.valueOf(s.getSale_time()));
                jsonarr.add(String.valueOf(s.getSale_payment()));
                jsonarr.add(String.valueOf(s.getSale_change()));
                jsonarr.add(String.valueOf(s.getSale_type()));
                jsonarr.add(String.valueOf(s.getSale_status()));

                json.put("object",jsonarr);

            }else{
                json.put("status",false);
            }
            out.write(json.toString());
        }else{
            try{
                page = Integer.valueOf(req.getParameter("page"));
            }catch(Exception e){

            }
            try{
                nums = Integer.valueOf(req.getParameter("nums"));
            }catch (Exception e){

            }
            int offset = (page-1)*nums;
            ArrayList<Sale>list = null;
            String empno =req.getParameter("sid");
            //System.out.println(empno);
            if(empno == null || empno.equals(""))
                list = DAOFactory.createSaleDAO().findSaleAll(offset,nums);
            else{
                list = DAOFactory.createSaleDAO().findSaleById(empno,offset,nums);
            }
            if (list.size()==0){
                json.put("status",false);
                out.write(json.toString());
                return;
            }
            for(Sale s : list){
                jsonarr = new JSONArray();
                jsonarr.add(String.valueOf(s.getSale_ID()));
                jsonarr.add(String.valueOf(s.getEmp_id()));
                jsonarr.add(String.valueOf(s.getSale_time()));
                jsonarr.add(String.valueOf(s.getSale_payment()));
                jsonarr.add(String.valueOf(s.getSale_change()));
                jsonarr.add(String.valueOf(s.getSale_type()));
                jsonarr.add(String.valueOf(s.getSale_status()));

                all.add(jsonarr);
            }
            json.put("status",true);
            json.put("object",all);

            out.write(json.toString());
        }
    }

    protected void doDelete(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        response.setContentType("text/json charset=utf-8");
        request.setCharacterEncoding("utf-8");
        Writer out = response.getWriter();
        JSONObject json = new JSONObject();
        int id;

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String s= null;
        String data = "";
        while((s=br.readLine())!=null){
            data = data.concat(s).concat("\n");
        }
        data = data.substring(0,data.length()-1);
        //System.out.println("-----------------------------");
        //System.out.println(data);
        HashMap<String,String> hm = new HashMap<String,String>();
        try{
            String listp[] = data.split("&");
            for(String x:listp){
                String z[] = x.split("=");
                hm.put(z[0],z[1]);
            }
        }catch(Exception e){
        }
        try{
            id = Integer.valueOf(hm.get("id"));
        }catch(Exception e){

            System.out.println("err");
            json.put("status",false);
            out.write(json.toString());
            return;
        }
        if(DAOFactory.createSaleDAO().delete(id)){
            json.put("status",true);
            out.write(json.toString());
        }else{
            json.put("status",false);
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

        Sale sale  = new Sale();

        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String s = null;
        String data = "";
        while((s=br.readLine())!=null){
            data = data.concat(s).concat("\n");
        }
        data = data.substring(0,data.length()-1);
        System.out.println(data);
        HashMap<String,String> hm = new HashMap<>();
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
            sale.setSale_ID(Integer.valueOf(hm.get("sale_ID")));
            sale.setEmp_id(Integer.valueOf(hm.get("emp_id")));
            sale.setSale_time(Timestamp.valueOf(hm.get("sale_time")));
            System.out.println(hm.get("sale_payment"));
            sale.setSale_payment(new BigDecimal((hm.get("sale_payment"))));
            sale.setSale_change(new BigDecimal((hm.get("sale_change"))));
            sale.setSale_type(new Integer(hm.get("sale_type")));
            sale.setSale_status(new Integer(hm.get("sale_status")));
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("失败");
            json.put("status",false);
            out.write(json.toString());
            return;
        }
        if (DAOFactory.createSaleDAO().update(sale)){
            json.put("status",true);
            out.write(json.toString());
        }
        else{
            json.put("status",false);
            System.out.println("无法插入");
            out.write(json.toString());
        }
        out.write(json.toString());
    }
}
