import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sss.idao.DAOFactory;
import sss.idao.IEmployee;
import sss.model.Employee;
import sss.model.Studio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zxw on 17-12-19.
 */
@WebServlet(name = "employee",urlPatterns = "/api/employee" )
public class employeeServer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset = utf-8");
        request.setCharacterEncoding("UTF-8");
        Writer out = response.getWriter();
        JSONObject json = new JSONObject();
        Employee emp = new Employee();
        try {
            emp.setEmp_no(request.getParameter("emp_no"));
            emp.setEmp_name(request.getParameter("emp_name"));
            emp.setEmp_tel_num(request.getParameter("emp_tel_num"));
            emp.setEmp_addr(request.getParameter("emp_addr"));
            emp.setEmp_email(request.getParameter("emp_email"));
        }catch (Exception e){
            System.out.println("信息获取失败");
            json.put("state",false);
            out.write(json.toString());
            return;
        }
        if(DAOFactory.creatEmployeeDAO().insert(emp)){
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
        int id;
        String emp_no;
        if(request.getParameter("id") != null){
            try {
                id = Integer.valueOf(request.getParameter("id"));
            }catch (Exception e){
                json.put("state",false);
                out.write(json.toString());
                return;
            }
            Employee emp = DAOFactory.creatEmployeeDAO().findEmployeeById(id);
            if(emp != null){
                json.put("status",true);
                jsonArr = new JSONArray();
                jsonArr.add(String.valueOf(emp.getEmp_id()));
                jsonArr.add(emp.getEmp_no());
                jsonArr.add(emp.getEmp_name());
                jsonArr.add(emp.getEmp_tel_num());
                jsonArr.add(emp.getEmp_addr());
                jsonArr.add(emp.getEmp_email());
                json.put("object",jsonArr);
            }else {
                json.put("status",false);
            }
            out.write(json.toString());
        }else if (request.getParameter("emp_no") != null){
            System.out.println(request.getParameter("emp_no"));
            try {
                emp_no = request.getParameter("emp_no");
            }catch (Exception e){
                json.put("state",false);
                out.write(json.toString());
                return;
            }
            Employee employee = DAOFactory.creatEmployeeDAO().findEmployeeByNo(emp_no);
            if (employee!=null){
                json.put("status",false);
                out.write(json.toString());
            }else {
                json.put("status",true);
                out.write(json.toString());
            }
        }
        else {
            try {
                page = Integer.valueOf(request.getParameter("page"));
            }catch (java.lang.Exception e){

            }
            try {
                nums = Integer.valueOf(request.getParameter("nums"));
            }catch (java.lang.Exception e){

            }
            int offset = (page-1) * nums;
            ArrayList<Employee>list = null;
            String name = request.getParameter("name");
            emp_no = request.getParameter("emp_no");
            System.out.println(name+1);
            if(name == null || name.equals("")){
                System.out.println(name+"rtytrfg");
                list = DAOFactory.creatEmployeeDAO().findEmployeeAll(offset,nums);
                System.out.println(name+"rtytrfg");
            }
            else{
                list = DAOFactory.creatEmployeeDAO().findEmployeeByName(name,offset,nums);
                System.out.println(name);
            }
            if (list.size() == 0){
                json.put("status",false);
                out.write(json.toString());
                return;
            }
            for (Employee emp : list){
                jsonArr = new JSONArray();
                jsonArr.add(String.valueOf(emp.getEmp_id()));
                jsonArr.add(emp.getEmp_no());
                jsonArr.add(emp.getEmp_name());
                jsonArr.add(emp.getEmp_tel_num());
                jsonArr.add(emp.getEmp_addr());
                jsonArr.add(emp.getEmp_email());
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
        int id;
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
            id = Integer.valueOf(hm.get("id"));
            System.out.println(id+"aaaaaaaaaaaaaaaaaaaaaa");
        }
        catch (java.lang.Exception e){
            System.out.print("err");
            json.put("status",false);
            out.write(json.toString());
            return ;
        }
        if(DAOFactory.creatEmployeeDAO().delete(id)){
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

        Employee emp = new Employee();
        emp.setEmp_addr("");

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
        }catch (Exception e){

        }
        String no = hm.get("emp_no");
        emp = DAOFactory.creatEmployeeDAO().findEmployeeByNo(no);

        try{
            //emp.setEmp_id(Integer.valueOf(hm.get("emp_id")));
            emp.setEmp_no(hm.get("emp_no"));
            emp.setEmp_name(hm.get("emp_name"));
            emp.setEmp_tel_num(hm.get("emp_tel_num"));
            emp.setEmp_addr(hm.get("emp_addr"));
            emp.setEmp_email(hm.get("emp_email"));
            HttpSession session = request.getSession(false);
            session.setAttribute("emp_name",hm.get("emp_name"));
            session.setAttribute("emp_tel_num",hm.get("emp_tel_num"));
            session.setAttribute("emp_addr",hm.get("emp_addr"));
            session.setAttribute("emp_email",hm.get("emp_email"));
        }catch (Exception e){
            e.printStackTrace();
            json.put("state",false);
            out.write(json.toString());
            return;
        }
        if(DAOFactory.creatEmployeeDAO().update(emp)){
            json.put("state",true);
        }else{
            json.put("state",false);
        }
        out.write(json.toString());
        }

    }
