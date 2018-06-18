package sss.dao;

import sss.ConnectionManager;
import sss.idao.ISchedule;
import sss.model.Schedule;
import sss.model.Studio;
import sss.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ScheduleDAO implements ISchedule {
    @Override
    public int  insert(Schedule schedule) {
        int  result =-1;
        if(schedule ==null){
            return result;
        }
        //获取Connection
        Connection con=ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt=null;
        try{
            String sql="insert into schedule(studio_id,play_id,sched_time,sched_ticket_price) values(?,?,?,?)";
            pstmt = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, schedule.getStudio_id());
            pstmt.setInt(2, schedule.getPlay_id());
            pstmt.setTimestamp(3, schedule.getSched_time());
            pstmt.setBigDecimal(4, schedule.getSched_ticket_price());
            pstmt.executeUpdate();
            ResultSet rs=pstmt.getGeneratedKeys();
            if(rs.next()){
                int id=rs.getInt(1);
                result=id;
                System.out.println("生成记录的key为:"+id);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            //关闭连接
            ConnectionManager.close(null, pstmt, con);
            return result;
        }
    }

    @Override
    public boolean delete(int sched_id) {
       boolean result=false;
       if(sched_id<1){
           return result;
       }
        //获取Connection
        Connection con=ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt=null;
        try{
            //删除某条演出计划
            String sql="delete from schedule where sched_id=?";
            pstmt =con.prepareStatement(sql);
            pstmt.setInt(1,sched_id);
            pstmt.executeUpdate();
            ConnectionManager.close(null,pstmt,con);
            result =true;
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            //关闭连接
            ConnectionManager.close(null,pstmt,con);
            return result;
        }
    }

    @Override
    public boolean update(Schedule schedule) {
        boolean result=false;
        if(schedule==null){
            return result;
        }
        //获取Connection
        Connection con=ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt=null;
        try{
            System.out.println("修改中");
            String sql="update schedule set studio_id=?,play_id=?,sched_time=?,sched_ticket_price=? where sched_id=?";
            pstmt =con.prepareStatement(sql);
            //pstmt.setInt(1,schedule.getSched_id());
            pstmt.setInt(1, schedule.getStudio_id());
            pstmt.setInt(2, schedule.getPlay_id());
            pstmt.setTimestamp(3, schedule.getSched_time());
            pstmt.setBigDecimal(4, schedule.getSched_ticket_price());
            pstmt.setInt(5,schedule.getSched_id());
            pstmt.executeUpdate();
            result =true;
        }catch (Exception e){
            e.printStackTrace();

        }finally{
            //关闭连接
            ConnectionManager.close(null,pstmt,con);
            return result;
        }
    }

    @Override
    public ArrayList<Schedule> findScheduleAll(int offset, int nums) {
        ArrayList<Schedule> list= new ArrayList<Schedule>();
        Schedule info=null;
        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            //获取所有演出计划
            pstmt=con.prepareStatement("select * from schedule limit ? , ?");
            pstmt.setInt(1,offset);
            pstmt.setInt(2,nums);
            rs=pstmt.executeQuery();
            while(rs.next()){
                info=new Schedule();
                info.setSched_id(rs.getInt("sched_id"));
                info.setStudio_id(rs.getInt("studio_id"));
                info.setPlay_id(rs.getInt("play_id"));
                info.setSched_time(rs.getTimestamp("sched_time"));
                info.setSched_ticket_price(rs.getBigDecimal("sched_ticket_price"));
                list.add(info);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            //关闭连接
            ConnectionManager.close(null,pstmt,con);
            return list;
        }
    }

    @Override
    public ArrayList<Schedule> findScheduleById(int play_id) {
        ArrayList<Schedule> list = new ArrayList<Schedule>();
        Schedule info = null;

        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            // 获取相应地演出计划
            pstmt = con.prepareStatement("select * from schedule where play_id=?");
            pstmt.setInt(1, play_id);
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                info = new Schedule();
                info.setSched_id(rs.getInt("sched_id"));
                info.setStudio_id(rs.getInt("studio_id"));
                info.setPlay_id(play_id);
                info.setSched_time(rs.getTimestamp("sched_time"));
                info.setSched_ticket_price(rs.getBigDecimal("sched_ticket_price"));
                // 加入列表
                list.add(info);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectionManager.close(rs, pstmt, con);
            return list;
        }
    }
    @Override
    public ArrayList<Schedule> findStudioByiid(int play_id,int offset,int nums) {
        if(play_id <1)
            return null;

        ArrayList<Schedule> list = new ArrayList<Schedule>();
        Schedule info = null;

        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        try
        {
            String sql = "select * from schedule where play_id =? limit ? , ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, play_id );// 拼接模糊查询串
            pstmt.setInt(2,offset);
            pstmt.setInt(3,nums);
            System.out.print(pstmt);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
            {
                // 如果有值的话再实例化
                info = new Schedule();
                info.setSched_id(rs.getInt("sched_id"));
                info.setStudio_id(rs.getInt("studio_id"));
                info.setPlay_id(rs.getInt("play_id"));
                info.setSched_time(rs.getTimestamp("sched_time"));
                info.setSched_ticket_price(rs.getBigDecimal("sched_ticket_price"));
                list.add(info);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 关闭连接
            ConnectionManager.close(null, pstmt, con);
            return list;
        }
    }
}
