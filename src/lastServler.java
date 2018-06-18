import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sss.idao.DAOFactory;
import sss.model.Seat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

@WebServlet(name = "last",urlPatterns = "/api/last")
public class lastServler extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String []arr;
        JSONObject json = new JSONObject();
        JSONArray jsonarr;
        JSONArray all = new JSONArray();
        Writer out = response.getWriter();
        ArrayList<String[]> list1 = new ArrayList<String[]>();
        int id = Integer.valueOf(request.getParameter("id"));
        int row = Integer.valueOf(request.getParameter("seat_row"));
        int col = Integer.valueOf(request.getParameter("seat_column"));
        ArrayList<Seat> list = null;
        list = DAOFactory.createSeatDAO().findSeatidByStudioId(id,row,col);
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
}
