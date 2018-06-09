package sss.Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by zxw on 17-11-19.
 */
@WebFilter(filterName = "Useradmin",urlPatterns = "/mana/*")
public class Useradmin implements Filter {
    public Useradmin(){

    }
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("filter start");
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession(false);
        if (session != null){
            if((Integer)session.getAttribute("type") == 1){
                chain.doFilter(req, resp);
            }else {
                request.setAttribute("desc","无权访问");
                request.getRequestDispatcher("/error.jsp").forward(request,resp);
            }
        }else{
            request.setAttribute("desc","无权访问");
            request.getRequestDispatcher("/error.jsp").forward(request,resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
