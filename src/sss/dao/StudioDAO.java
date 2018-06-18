package sss.dao;

import sss.ConnectionManager;
import sss.idao.IStudio;
import sss.model.Studio;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by zxw on 17-12-18.
 */
public class StudioDAO implements IStudio {
    @Override
    public int insert(Studio studio) {
        int result = -1;
        if(studio == null)
            return result;
        // 获取Connection
        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        try {
            String sql = "insert into studio(studio_name, studio_row_count, studio_col_count, studio_introduction, studio_flag) values(?,?,?,?,?)";
            pstmt = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, studio.getStudio_name());
            pstmt.setInt(2, studio.getStudio_row_count());
            pstmt.setInt(3, studio.getStudio_col_count());
            pstmt.setString(4, studio.getStudio_introduction());
            pstmt.setInt(5, studio.getStudio_flag());

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
    public boolean delete(int studio_id) {
        boolean result = false;
        if(studio_id <= 1)
            return result;

        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        try
        {
            // 删除子某个用户
            String sql = "delete from studio where studio_id=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, studio_id);
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
    public boolean update(Studio studio) {
        boolean result = false;
        if(studio == null)
            return result;

        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        try
        {
            String sql = "update studio set studio_name=?,studio_row_count=?,studio_col_count=?,studio_introduction=?,studio_flag=? where studio_id=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, studio.getStudio_name());
            pstmt.setInt(2, studio.getStudio_row_count());
            pstmt.setInt(3, studio.getStudio_col_count());
            pstmt.setString(4, studio.getStudio_introduction());
            pstmt.setInt(5, studio.getStudio_flag());
            pstmt.setInt(6, studio.getStudio_id());
            System.out.println(pstmt.toString());
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
    public Studio findStudioById(int id){
        Studio s = null;

        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            // 获取所有数据
            pstmt = con.prepareStatement("select * from studio where studio_id = ?");
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                s = new Studio();

                s.setStudio_id(rs.getInt("studio_id"));
                s.setStudio_name(rs.getString("studio_name"));
                s.setStudio_row_count(rs.getInt("studio_row_count"));
                s.setStudio_col_count(rs.getInt("studio_col_count"));
                s.setStudio_introduction(rs.getString("studio_introduction"));
                s.setStudio_flag(rs.getInt("studio_flag"));

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectionManager.close(rs, pstmt, con);
            return s;
        }
    }


    @Override
    public ArrayList<Studio> findStudioAll(int offset,int nums) {
        ArrayList<Studio> list = new ArrayList<Studio>();
        Studio info = null;

        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            // 获取所有用户数据
            pstmt = con.prepareStatement("select * from studio limit ? , ?");
            pstmt.setInt(1,offset);
            pstmt.setInt(2,nums);
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                info = new Studio();

                info.setStudio_id(rs.getInt("studio_id"));
                info.setStudio_name(rs.getString("studio_name"));
                info.setStudio_row_count(rs.getInt("studio_row_count"));
                info.setStudio_col_count(rs.getInt("studio_col_count"));
                info.setStudio_introduction(rs.getString("studio_introduction"));
                info.setStudio_flag(rs.getInt("studio_flag"));
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
    public ArrayList<Studio> findStudioByname(String studio_name,int offset,int nums) {
        if(studio_name == null || studio_name.equals(""))
            return null;

        ArrayList<Studio> list = new ArrayList<Studio>();
        Studio info = null;

        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        try
        {
            String sql = "select * from studio where studio_name like ? limit ? , ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "%" + studio_name + "%");// 拼接模糊查询串
            pstmt.setInt(2,offset);
            pstmt.setInt(3,nums);
            System.out.print(pstmt);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
            {
                // 如果有值的话再实例化
                info = new Studio();
                info.setStudio_id(rs.getInt("studio_id"));
                info.setStudio_name(rs.getString("studio_name"));
                info.setStudio_row_count(rs.getInt("studio_row_count"));
                info.setStudio_col_count(rs.getInt("studio_col_count"));
                info.setStudio_introduction(rs.getString("studio_introduction"));
                info.setStudio_flag(rs.getInt("studio_flag"));
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
