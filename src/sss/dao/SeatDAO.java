package sss.dao;

import sss.ConnectionManager;
import sss.idao.ISeat;
import sss.model.Seat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by zxw on 18-6-4.
 */
public class SeatDAO implements ISeat {
    @Override
    public boolean insert(Seat seat) {
        boolean result = false;
        if (seat == null) {

            return result;
        }
        System.out.println(seat.getSeat_column());
        System.out.println(seat.getSeat_row());
        System.out.println(seat.getStudio_id());
        System.out.println(seat.getSeat_status());
        //获取Connection
        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        try {
            for (int i = 1; i <= seat.getSeat_row(); i++) {
                for (int j = 1; j <= seat.getSeat_column(); j++) {
                    String sql = "insert into seat(studio_id,seat_row,seat_column,seat_status) values (?,?,?,?)";
                    pstmt = con.prepareStatement(sql);
                    pstmt.setInt(1, seat.getStudio_id());
                    pstmt.setInt(2, i);
                    pstmt.setInt(3, j);
                    pstmt.setInt(4, seat.getSeat_status());
                    System.out.println("第 " + i + " 行 " + "第 " + j + " 列 插入成功");
                    pstmt.executeUpdate();
                    result = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭连接
            ConnectionManager.close(null, pstmt, con);
            return result;
        }
    }


    @Override
    public ArrayList<Seat> findSeatStateByStudioId(int id) {
        ArrayList<Seat> list = new ArrayList<Seat>();
        Seat info = null;

        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            //获取座位信息
            pstmt = con.prepareStatement("select * from seat where studio_id = ?");
            pstmt.setInt(1, id);
            System.out.println(id);
            rs = pstmt.executeQuery();
            //System.out.println(rs.next());

            while (rs.next()) {
                info = new Seat();
                info.setStudio_id(rs.getInt("studio_id"));
                info.setSeat_id(rs.getInt("seat_id"));
                info.setSeat_row(rs.getInt("seat_row"));
                info.setSeat_column(rs.getInt("seat_column"));
                info.setSeat_status(rs.getInt("seat_status"));
                System.out.println("列：" + rs.getInt("seat_column"));
                list.add(info);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            ConnectionManager.close(rs, pstmt, con);
            return list;
        }
    }

    @Override
    public ArrayList<Seat> findSeatidByStudioId(int studio_id,int row, int col) {
        ArrayList<Seat> list = new ArrayList<Seat>();
        Seat info = null;
        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            //获取座位信息
            pstmt = con.prepareStatement("select seat_id from seat where studio_id = ? and seat_row= ? and seat_column=?");
            pstmt.setInt(1,studio_id);
            pstmt.setInt(2,row);
            pstmt.setInt(3,col);
           // System.out.println(row,col);
            rs = pstmt.executeQuery();
            //System.out.println(rs.next());

            while (rs.next()) {
                info = new Seat();
                //System.out.println("AAAAAAAAAAAAAAAAAAAaa");
                info.setSeat_id(rs.getInt("seat_id"));
                //System.out.println(info.getSeat_id());
                list.add(info);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            ConnectionManager.close(rs, pstmt, con);
            return list;
        }
    }

    @Override
    public boolean update(Seat seat){
        boolean result = false;
        if (seat == null)
            return result;

        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        try {
            String sql = "update seat set seat_status=? where studio_id=? and seat_row=? and seat_column=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,seat.getSeat_status());
            pstmt.setInt(2,seat.getStudio_id());
            pstmt.setInt(3,seat.getSeat_row());
            pstmt.setInt(4,seat.getSeat_column());

            System.out.println(pstmt.toString());
            pstmt.executeUpdate();

            result = true;

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            ConnectionManager.close(null,pstmt,con);
            System.out.println(result);
            return result;
        }

    }

    @Override
    public boolean delete(int studio_id){
        System.out.println("删除的座位的studio_id是：");
        boolean result = false;
        if (studio_id<=1){
            return result;
        }
        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        try {
            //删除所有
            String sql = "delete from seat where studio_id=?";

            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,studio_id);
            pstmt.executeUpdate();
            ConnectionManager.close(null,pstmt,con);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            ConnectionManager.close(null,pstmt,con);
            return result;
        }
    }
}

