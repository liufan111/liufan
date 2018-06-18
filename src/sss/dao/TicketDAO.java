package sss.dao;

import sss.ConnectionManager;
import sss.idao.ITicket;
import sss.model.Ticket;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TicketDAO implements ITicket{

    @Override
    public boolean insert(Ticket ticket) {
        boolean result = false;
        if(ticket == null)
        return result;

        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement ps = null;

        /*for (int i = seat_min;i<=seat_max;i++){

        }*/
        try {
            String sql = "insert into ticket(seat_id,sched_id,ticket_price,ticket_status) values(?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, ticket.getSeat_id());
            ps.setInt(2, ticket.getSched_id());
            ps.setBigDecimal(3, ticket.getTicket_price());
            ps.setInt(4,ticket.getTicket_status());
            ps.executeUpdate();
            result=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        finally{
            ConnectionManager.close(null,ps,con);
            return result;
        }
    }

    @Override
    public boolean delete(int id) {
        System.out.println("删除票");
        boolean result = false;
        if(id<1)
            return result;
        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement ps = null;
        try{
            String sql = "delete from ticket where sched_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            ps.executeUpdate();
            ConnectionManager.close(null,ps,con);
            result=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            ConnectionManager.close(null,ps,con);
            return result;
        }
    }

    @Override
    public boolean update(Ticket ticket) {
        boolean result = false;
        if (ticket ==null){
            return result;
        }
        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement ps = null;
        try{
            String sql = "update ticket set ticket_id=?,seat_id=?,sche_id=?,ticket_price=?,ticket_status=?,ticket_locked_time=? where ticket_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1,ticket.getTicket_id());
            ps.setInt(2,ticket.getSeat_id());
            ps.setInt(3,ticket.getSched_id());
            ps.setBigDecimal(4,ticket.getTicket_price());
            ps.setInt(5,ticket.getTicket_status());
            ps.setDate(6,new Date(ticket.getTicket_locked_time().getTime()));

            ps.executeUpdate();
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            ConnectionManager.close(null,ps,con);
            return result;
        }

    }

    @Override
    public ArrayList<Ticket> findTicketAll(int offset, int nums) {
        ArrayList<Ticket> list = new ArrayList<Ticket>();
        Ticket info = null;

        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = con.prepareStatement("select * from ticket limit ?,?");
            ps.setInt(1,offset);
            ps.setInt(2,nums);
            rs = ps.executeQuery();
            while(rs.next()){
                info = new Ticket();

                info.setTicket_id(rs.getInt("ticket_id"));
                info.setSeat_id(rs.getInt("seat_id"));
                info.setSched_id(rs.getInt("sched_id"));
                info.setTicket_price(rs.getBigDecimal("ticket_price"));
                info.setTicket_status(rs.getInt("ticket_status"));
                info.setTicket_locked_time(rs.getTimestamp("ticket_locked_time"));

                list.add(info);
            }

        }catch (Exception e){
                e.printStackTrace();
        }finally{
            ConnectionManager.close(rs,ps,con);
            return list;
        }
    }

    @Override
    public Ticket findTicketById(int id) {
        Ticket ticket = null;
         Connection con = ConnectionManager.getInstance().getConnection();
         PreparedStatement ps = null;
         ResultSet rs = null;

         try{
             ps = con.prepareStatement("select * from ticket where ticket_id=?");
             ps.setInt(1,id);
             rs = ps.executeQuery();
             while(rs.next()){
                 ticket = new Ticket();

                 ticket.setTicket_id(rs.getInt("ticket_id"));
                 ticket.setSeat_id(rs.getInt("seat_id"));
                 ticket.setSched_id(rs.getInt("sched_id"));
                 ticket.setTicket_price(rs.getBigDecimal("ticket_price"));
                 ticket.setTicket_status(rs.getInt("ticket_status"));
                 ticket.setTicket_locked_time(rs.getTimestamp("ticket_locked_time"));
             }
         }catch (Exception e){
             e.printStackTrace();
         }finally {
             ConnectionManager.close(rs,ps,con);
             return ticket;
         }
    }
    @Override
    public boolean updateTicket(Ticket ticket) {
        boolean result = false;
        if (ticket ==null){
            return result;
        }
        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement ps = null;
        try{
            String sql = "update ticket set ticket_status=? , ticket_locked_time = ? where sched_id = ? and seat_id= ? ";
            ps = con.prepareStatement(sql);
            ps.setInt(1,ticket.getTicket_status());
            ps.setTimestamp(2,ticket.getTicket_locked_time());
            ps.setInt(3,ticket.getSched_id());
            ps.setInt(4,ticket.getSeat_id());

            System.out.println(ticket.getTicket_status()+","+ticket.getTicket_locked_time()+","+ticket.getSched_id()+","+ticket.getSeat_id());

            ps.executeUpdate();
            //System.out.println("aaaaaaaaaaaaaaaaa");
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            ConnectionManager.close(null,ps,con);
            return result;
        }
    }

    @Override
    public boolean updateTicket_lock(Ticket t) {
        boolean result = false;
        if (t ==null){
            return result;
        }
        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement ps = null;
        try{
            String sql = "update ticket set ticket_status=? where sched_id = ? and seat_id= ? ";
            ps = con.prepareStatement(sql);
            ps.setInt(1,t.getTicket_status());
            ps.setInt(2,t.getSched_id());
            ps.setInt(3,t.getSeat_id());
            //System.out.println(sql);
            ps.executeUpdate();
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            ConnectionManager.close(null,ps,con);
            return result;
        }
    }

    @Override
    public ArrayList<Ticket> findTicketByname(String name, int offset, int nums) {
        return null;
    }

    @Override
    public ArrayList<Ticket> findTicketIDBySeatId(int seat_id,int sched_id) {
        ArrayList<Ticket> list = new ArrayList<>();
        Ticket info = null;
        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = con.prepareStatement("select * from ticket where seat_id = ? and sched_id = ?");
            ps.setInt(1,seat_id);
            ps.setInt(2,sched_id);
            rs = ps.executeQuery();
            while(rs.next()){
                info = new Ticket();

                info.setTicket_id(rs.getInt("ticket_id"));
                System.out.println(info.getTicket_id());
                list.add(info);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally{
            ConnectionManager.close(rs,ps,con);
            return list;
        }
    }
}
