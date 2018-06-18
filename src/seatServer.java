import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sss.idao.DAOFactory;
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
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zxw on 17-12-20.
 */
@WebServlet(name = "seat",urlPatterns = "/api/seat")
public class seatServer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset = utf-8");
        request.setCharacterEncoding("UTF-8");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String []arr;
        JSONObject json = new JSONObject();
        JSONArray jsonarr;
        JSONArray all = new JSONArray();
        Writer out = response.getWriter();
        ArrayList<String[]> list1 = new ArrayList<String[]>();
        int id = Integer.valueOf(request.getParameter("id"));
        ArrayList<Seat> list = null;
        list = DAOFactory.createSeatDAO().findSeatStateByStudioId(id);
        System.out.println(list);
        if (list.size() == 0){
            json.put("status",false);
            out.write(json.toString());
            return;
        }
        System.out.println(list.size());
        for (Seat s:list){
            jsonarr = new JSONArray();
            jsonarr.add(s.getStudio_id());
            jsonarr.add(s.getSeat_id());
            jsonarr.add(s.getSeat_row());
            jsonarr.add(s.getSeat_column());
            jsonarr.add(s.getSeat_status());

            all.add(jsonarr);
        }

        json.put("status",true);
        json.put("object",all);

        out.write(json.toString());
    }


    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Writer out = response.getWriter();
        JSONObject json = new JSONObject();

        Seat seat = new Seat();

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String s = null;
        String data = "";
        while ((s = br.readLine())!=null){
            data = data.concat(s).concat("\n");
        }
        data = data.substring(0,data.length()-1);
        System.out.println(data);
        HashMap<String,String>hm = new HashMap<String, String>();
        try{
            String listp[] = data.split("&");
            for (String x:listp){
                String z[] = x.split("=");
                if (z.length == 1){
                    continue;
                }
                hm.put(z[0],z[1]);
            }
        }catch (Exception e){

        }
        System.out.println(hm);
        System.out.println(hm.get("studio_id"));
        try{
            seat.setStudio_id(Integer.valueOf(hm.get("studio_id")));
            seat.setSeat_row(Integer.valueOf(hm.get("seat_row")));
            seat.setSeat_column(Integer.valueOf(hm.get("seat_column")));
            seat.setSeat_status(Integer.valueOf(hm.get("seat_status")));
            System.out.println(seat.getSeat_row());
        }catch (Exception e){
            e.printStackTrace();
            json.put("state",false);
            out.write(json.toString());
            return;
        }
        if (DAOFactory.createSeatDAO().update(seat)){
            json.put("state",true);
        }else {
            json.put("state",false);
        }
        out.write(json.toString());
    }

}
