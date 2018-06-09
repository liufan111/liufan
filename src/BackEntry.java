import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONObject;
/**
 * Created by zxw on 17-11-19.
 */
@WebServlet(name = "BackEntry",urlPatterns = "/BackEntry")
public class BackEntry extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json; charset=utf-8");
        HttpSession session = request.getSession(false);
        JSONObject obj = new JSONObject();
        HashMap<String,String> hp = new HashMap<String,String >();
        if(session == null){
            obj.put("state",false);
        }else{
            obj.put("state",true);
            //管理员可以访问url：
            if((Integer)session.getAttribute("type") == 1){
                hp.put("员工管理","/mana/employee.jsp");
                hp.put("登陆用户管理","/mana/user.jsp");
                hp.put("个人信息","/mana/aboutMe.jsp");
                hp.put("影厅管理","/mana/studio.jsp");
                hp.put("座位管理","/mana/seat.jsp");
            }
            //经理可以访问的url:
            else if ((Integer)session.getAttribute("type") == 0){
                hp.put("演出计划管理","/manager/schedule.jsp");
            }
            //售票可以访问的url：
            else {
                hp.put("售票","/seller/sale.jsp");
                hp.put("个人信息","/seller/aboutMe.jsp");
                hp.put("上传剧目海报","/play/uploadPic.jsp");
                hp.put("剧目演出计划","/seller/playSchedule.jsp");

            }
        }
        obj.put("entry",hp);
        response.getWriter().write(obj.toString());
    }

}
