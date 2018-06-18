package sss.idao;

import sss.dao.*;
import sss.model.Ticket;

/**
 * Created by zxw on 17-11-19.
 */

public class DAOFactory
{
    public static IEmployee creatEmployeeDAO()
    {
        return new EmployeeDAO();
    }

    public static IUser createUserDAO() { return new UserDAO(); }

    public static IStudio createStudioDAO() {
        return new StudioDAO();
    }

    public static ISeat createSeatDAO(){
        return new SeatDAO();
    }

    public static IPlay createPlayDAO(){return new PlayDAO();}

    public static ISale createSaleDAO(){ return new SaleDAO();}

    public static ISchedule createScheduleDAO(){ return new ScheduleDAO();}

    public static ITicket createTicketDAO(){ return new TicketDAO();}

    public static ISale_item createSale_itemDAO(){ return new Sale_itemDAO();}

    public static Ixiaoshou createxiaoshouDAO(){return new xiaoshouDAO();}
}