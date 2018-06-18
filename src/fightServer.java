import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sss.idao.DAOFactory;
import sss.model.Ticket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

@WebServlet(name = "fight",urlPatterns = "/api/fight")
public class fightServer extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json charset=utf-8");
        String[] arr;
        JSONObject json = new JSONObject();
        JSONArray jsonarr;
        JSONArray all = new JSONArray();
        ArrayList<String[]> list = new ArrayList<String[]>();
        Writer out = resp.getWriter();
        int id;
        int sched_id;
        if (req.getParameter("id") != null) {
            try {
                id = Integer.valueOf(req.getParameter("id"));
                sched_id = Integer.valueOf(req.getParameter("sched_id"));
                System.out.println("id");
                System.out.println("sched_id");
            } catch (Exception e) {
                json.put("status", false);
                out.write(json.toString());
                return;
            }
            ArrayList<Ticket> list1 = DAOFactory.createTicketDAO().findTicketIDBySeatId(id,sched_id);
            for (Ticket tt:list1){
                jsonarr = new JSONArray();
                jsonarr.add(tt.getTicket_id());
                jsonarr.add(tt.getSeat_id());
                jsonarr.add(tt.getSched_id());
                jsonarr.add(tt.getTicket_price());
                jsonarr.add(tt.getTicket_status());
                jsonarr.add(tt.getTicket_locked_time());

                all.add(jsonarr);
            }

            json.put("status",true);
            json.put("object",all);

            out.write(json.toString());
        }
    }
}
