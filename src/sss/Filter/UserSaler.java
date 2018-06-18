package sss.Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 */
@WebFilter(filterName="UserSaler",urlPatterns="/seller/*")
public class UserSaler implements Filter {
    public UserSaler(){

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        System.out.println("Saler filter start");
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession(false);
        if(session!=null){
            if((Integer)session.getAttribute("type")==-1){
                chain.doFilter(req,resp);
            }else{
                request.setAttribute("desc","无权访问");
                request.getRequestDispatcher("/error.jsp").forward(request,resp);
            }
        }else{
            request.setAttribute("desc","无权访问");
            request.getRequestDispatcher("/eror.jsp").forward(request,resp);
        }
    }

    @Override
    public void destroy() {

    }
}
