package sss.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import sss.idao.ISale_item;
import sss.model.Employee;
import sss.model.Sale_item;
import sss.ConnectionManager;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Sale_itemDAO implements ISale_item {
    @Override
    public boolean insert(Sale_item si){
        boolean result = false;
        if (si == null) {
            return result;
        }
        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            String sql = "insert into sale_item(ticket_id,sale_ID,sale_item_price) values(?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1,si.getTicket_id());
            ps.setInt(2, si.getSale_ID());
            ps.setFloat(3, si.getSale_item_price());

            ps.executeUpdate();
            //System.out.println("bbbbbbbbbbbbbbbbbbbbbbb");
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(null, ps, con);
            return result;
        }
    }

    @Override
    public ArrayList<Sale_item> findSaleAll(){
        ArrayList<Sale_item> list = new ArrayList<Sale_item>();
        Sale_item info = null;

        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            // 获取所有用户数据
            pstmt = con.prepareStatement("select * from sale_item");
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                info = new Sale_item();

                info.setSale_ID(rs.getInt("sale_item_id"));
                info.setTicket_id(rs.getInt("ticket_id"));
                info.setSale_ID(rs.getInt("sale_ID"));
                info.setSale_item_price(rs.getFloat("sale_item_price"));
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
    public Sale_item findSaleByID(int id){
        return null;
    }
}
