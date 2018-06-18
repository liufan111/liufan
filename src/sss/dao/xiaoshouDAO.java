package sss.dao;
import sss.ConnectionManager;
import sss.idao.Ixiaoshou;
import sss.model.xiaoshou;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class xiaoshouDAO implements Ixiaoshou {
    @Override
    public ArrayList<xiaoshou> findxiaoshouAll(int movie, int people, String start, String end){
        ArrayList<xiaoshou> list= new ArrayList<xiaoshou>();
        xiaoshou info=null;
        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        HashMap<String,Integer> hp = new HashMap<String, Integer>();
        try{
            int f = 0;
            //获取所有演出计划
            String sql = "select * from ((ticket t \n" +
                    "INNER join schedule s on s.sched_id = t.sched_id) \n" +
                    "INNER join sale_item si on si.ticket_id = t.ticket_id) \n" +
                    "INNER join sale on sale.sale_ID = si.sale_ID ";
            if(movie != -1){
                if(f == 0){
                    sql += "where ";
                }else{
                    sql += "and ";
                }
                sql += "play_id = " + movie + " ";
                f++;
                hp.put("play_id",f);
            }
            if(people != -1){
                if(f == 0){
                    sql += "where ";
                }else{
                    sql += "and ";
                }
                sql += "emp_id = " + people + " ";
                f++;
                hp.put("emp_id",f);
            }

            if(start != null){
                if(f == 0){
                    sql += "where ";
                }else{
                    sql += "and ";
                }
                sql += "sale_time > '" + start + "' ";
                f++;
                hp.put("start",f);
            }

            if(end != null){
                if(f == 0){
                    sql += "where ";
                }else{
                    sql += "and ";
                }
                sql += "sale_time < '" + end + "'";
                f++;
                hp.put("end",f);
            }
            System.out.println(sql);
            pstmt=con.prepareStatement(sql);
            rs=pstmt.executeQuery();
            while(rs.next()){
                info=new xiaoshou();
                info.setSale_item_id(rs.getInt("sale_item_id"));
                info.setSale_item_price(rs.getFloat("sale_item_price"));
                info.setEmp_id(rs.getInt("emp_id"));
                info.setPlay_id(rs.getInt("play_id"));
                info.setSale_time(rs.getTime("sale_time"));
                info.setSale_type(rs.getInt("sale_type"));
                info.setSale_status(rs.getInt("sale_status"));
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
}
