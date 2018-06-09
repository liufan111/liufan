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

/**
 * Created by zxw on 17-12-20.
 */
@WebServlet(name = "seat",urlPatterns = "/api/seat")
public class seatServer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset = utf-8");
        request.setCharacterEncoding("UTF-8");

        Writer out = response.getWriter();
        JSONObject json = new JSONObject();
        Seat seat = new Seat();
        try{
//            seat.setStudio_id(Integer.valueOf(request.getParameter("studio_id")));
            seat.setSeat_row(Integer.valueOf(request.getParameter("studio_rows")));
            seat.setSeat_column(Integer.valueOf(request.getParameter("studio_cols")));
            seat.setSeat_status(Integer.valueOf(request.getParameter("flag")));
            System.out.println(seat);
        }catch (Exception e){
            System.out.println("座位信息获取失败");
            json.put("state",false);
            out.write(json.toString());
            return;
        }

        if (DAOFactory.createSeatDAO().insert(seat)){
            json.put("state",true);
            out.write(json.toString());
        }else{
            System.out.println("座位插入数据失败");
            json.put("state",false);
            out.write(json.toString());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
