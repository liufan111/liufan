package sss.dao;

import sss.ConnectionManager;
import sss.idao.IPlay;
import sss.model.Play;
import sss.model.Studio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PlayDAO implements IPlay {

        @Override
        public int insert(Play play) {
            int result = -1;
            if(play == null)
                return result;
            // 获取Connection
            Connection con = ConnectionManager.getInstance().getConnection();
            PreparedStatement pstmt = null;
            try {
                String sql = "insert into play(play_type_id, play_lang_id, play_name, play_introduction, play_image, play_length, play_ticket_price, play_status) values(?,?,?,?,?,?,?,?)";
                pstmt = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1,play.getPlay_type_id());
                pstmt.setInt(2,play.getPlay_lang_id());
                pstmt.setString(3,play.getPlay_name());
                pstmt.setString(4,play.getPlay_introduction());
                pstmt.setString(5,play.getPlay_image());
                pstmt.setInt(6,play.getPlay_length());
                pstmt.setBigDecimal(7,play.getPlay_ticket_price());
                pstmt.setInt(8,play.getPlay_status());

                pstmt.executeUpdate();
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    result = id;
                    System.out.println ("生成记录的key为 ：" + id);
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
                return result;
            }
        }

        @Override
        public boolean delete(int play_id) {
            //System.out.println("删除开始");
            boolean result = false;
            if(play_id <= 1)
                return result;

            Connection con = ConnectionManager.getInstance().getConnection();
            PreparedStatement pstmt = null;
            try
            {
                // 删除子某个剧目
                String sql = "delete from play where play_id=?";
                System.out.println("id:"+play_id);
                pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, play_id);
                pstmt.executeUpdate();
                ConnectionManager.close(null, pstmt, con);
                result = true;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                // 关闭连接
                ConnectionManager.close(null, pstmt, con);
                return result;
            }
        }

        @Override
        public boolean update(Play play) {
            boolean result = false;
            if(play == null)
                return result;

            Connection con = ConnectionManager.getInstance().getConnection();
            PreparedStatement pstmt = null;
            try
            {
                String sql = "update play set play_type_id=?, play_lang_id=?, play_name=?, play_introduction=?, play_image=?, play_length=?, play_ticket_price=?, play_status=? where play_id=?";
                pstmt = con.prepareStatement(sql);
                pstmt.setInt(1,play.getPlay_type_id());
                pstmt.setInt(2,play.getPlay_lang_id());
                pstmt.setString(3,play.getPlay_name());
                pstmt.setString(4,play.getPlay_introduction());
                pstmt.setString(5,play.getPlay_image());
                pstmt.setInt(6,play.getPlay_length());
                pstmt.setBigDecimal(7,play.getPlay_ticket_price());
                pstmt.setInt(8,play.getPlay_status());
                pstmt.setInt(9,play.getPlay_id());

                pstmt.executeUpdate();
                result = true;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                // 关闭连接
                ConnectionManager.close(null, pstmt, con);
                return result;
            }
        }

        @Override
        public Play  findPlayById(int id){
            Play p = null;

            Connection con = ConnectionManager.getInstance().getConnection();
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try
            {
                // 获取所有用户数据
                pstmt = con.prepareStatement("select * from play where play_id = ?");
                pstmt.setInt(1,id);
                rs = pstmt.executeQuery();
                while(rs.next())
                {
                    p = new Play();

                    p.setPlay_id(rs.getInt("play_id"));
                    p.setPlay_type_id(rs.getInt("play_type_id"));
                    p.setPlay_lang_id(rs.getInt("play_lang_id"));
                    p.setPlay_name(rs.getString("play_name"));
                    p.setPlay_introduction(rs.getString("play_introduction"));
                    p.setPlay_image(rs.getString("play_image"));
                    p.setPlay_length(rs.getInt("play_length"));
                    p.setPlay_ticket_price(rs.getBigDecimal("play_ticket_price"));
                    p.setPlay_status(rs.getInt("play_status"));

                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                ConnectionManager.close(rs, pstmt, con);
                return p;
            }
        }


    @Override
        public ArrayList<Play> findPlayAll(int offset, int nums) {
            ArrayList<Play> list = new ArrayList<Play>();
            Play info = null;

            Connection con = ConnectionManager.getInstance().getConnection();
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try
            {
                // 获取所有用户数据
                pstmt = con.prepareStatement("select * from play limit ? , ?");
                pstmt.setInt(1,offset);
                pstmt.setInt(2,nums);
                rs = pstmt.executeQuery();
                while(rs.next())
                {
                    info = new Play();

                    info.setPlay_id(rs.getInt("play_id"));
                    info.setPlay_type_id(rs.getInt("play_type_id"));
                    info.setPlay_lang_id(rs.getInt("play_lang_id"));
                    info.setPlay_name(rs.getString("play_name"));
                    info.setPlay_introduction(rs.getString("play_introduction"));
                    info.setPlay_image(rs.getString("play_image"));
                    info.setPlay_length(rs.getInt("play_length"));
                    info.setPlay_ticket_price(rs.getBigDecimal("play_ticket_price"));
                    info.setPlay_status(rs.getInt("play_status"));
                    // 加入列表
                    list.add(info);
                    //System.out.println(list+"查找");
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
        public ArrayList<Play> findPlayByname(String play_name,int offset,int nums) {
            if(play_name == null || play_name.equals(""))
                return null;

            ArrayList<Play> list = new ArrayList<Play>();
            Play info = null;

            Connection con = ConnectionManager.getInstance().getConnection();
            PreparedStatement pstmt = null;
            try
            {
                String sql = "select * from play where play_name like ? limit ? , ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, "%" + play_name + "%");// 拼接模糊查询串
                pstmt.setInt(2,offset);
                pstmt.setInt(3,nums);
                System.out.print(pstmt);
                ResultSet rs = pstmt.executeQuery();

                while(rs.next())
                {
                    // 如果有值的话再实例化
                    info = new Play();

                    info.setPlay_id(rs.getInt("play_id"));
                    info.setPlay_type_id(rs.getInt("play_type_id"));
                    info.setPlay_lang_id(rs.getInt("play_lang_id"));
                    info.setPlay_name(rs.getString("play_name"));
                    info.setPlay_introduction(rs.getString("play_introduction"));
                    info.setPlay_image(rs.getString("play_image"));
                    info.setPlay_length(rs.getInt("play_length"));
                    info.setPlay_ticket_price(rs.getBigDecimal("play_ticket_price"));
                    info.setPlay_status(rs.getInt("play_status"));
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
                // 关闭连接
                ConnectionManager.close(null, pstmt, con);
                return list;
            }
        }
    }
