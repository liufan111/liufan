package sss.dao;

import sss.ConnectionManager;
import sss.idao.ISale;
import sss.model.Sale;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

public class SaleDAO implements ISale {

    @Override
    public boolean insert(Sale sale) {
        boolean result = false;
        if (sale == null) {
            return result;
        }
        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            String sql = "insert into sale(emp_id,sale_time,sale_payment,sale_change,sale_type,sale_status) values(?,?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            //ps.setInt(1, sale.getSale_ID());
            ps.setInt(1, sale.getEmp_id());
            ps.setTimestamp(2, sale.getSale_time());
            ps.setBigDecimal(3, sale.getSale_payment());
            ps.setBigDecimal(4, sale.getSale_change());
            ps.setInt(5, sale.getSale_type());
            ps.setInt(6, sale.getSale_status());

            //System.out.println(sale.getSale_ID()+"*"+sale.getEmp_id()+"*"+sale.getSale_time()+"*"+sale.getSale_payment()+"*"+sale.getSale_change()+"*"+sale.getSale_type()+"*"+sale.getSale_status());
            System.out.println(ps.toString());
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
    public boolean delete(int sale_ID) {
        boolean result = false;
        if(sale_ID<=1)
            return result;
        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement ps = null;
        try{
            String sql = "delete from sale where sale_ID = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1,sale_ID);
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
    public boolean update(Sale sale) {
        boolean result = false;
        if (sale ==null){
            return result;
        }
        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement ps = null;
        try{
            String sql = "update sale set sale_ID=?,emp_id=?,sale_time=?,sale_payment=?,sale_change=?,sale_type=?,sale_status=? where sale_ID = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, sale.getSale_ID());
            ps.setInt(2, sale.getEmp_id());
            ps.setDate(3, new java.sql.Date(sale.getSale_time().getTime()));
            ps.setBigDecimal(4, sale.getSale_payment());
            ps.setBigDecimal(5, sale.getSale_change());
            ps.setInt(6, sale.getSale_type());
            ps.setInt(7, sale.getSale_status());
            ps.setInt(8,sale.getSale_ID());

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
    public ArrayList<Sale> findSaleAll(int offset, int nums) {
        ArrayList<Sale> list = new ArrayList<Sale>();
        Sale info = null;

        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = con.prepareStatement("select * from sale limit ?,?");
            ps.setInt(1,offset);
            ps.setInt(2,nums);
            rs = ps.executeQuery();
            while(rs.next()){
                info = new Sale();

                info.setSale_ID(rs.getInt("sale_ID"));
                info.setEmp_id(rs.getInt("emp_id"));
                info.setSale_time(rs.getTimestamp("sale_time"));
                info.setSale_payment(rs.getBigDecimal("sale_payment"));
                info.setSale_change(rs.getBigDecimal("sale_change"));
                info.setSale_type(rs.getInt("sale_type"));
                info.setSale_status(rs.getInt("sale_status"));

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
    public Sale findSaleById(String id) {
        Sale sale = null;
        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            ps = con.prepareStatement("select * from sale where emp_id=?");
            ps.setString(1,id);
            rs = ps.executeQuery();
            while(rs.next()){

                sale = new Sale();

                sale.setSale_ID(rs.getInt("sale_ID"));
                sale.setEmp_id(rs.getInt("emp_id"));
                sale.setSale_time(rs.getTimestamp("sale_time"));
                sale.setSale_payment(rs.getBigDecimal("sale_payment"));
                sale.setSale_change(rs.getBigDecimal("sale_change"));
                sale.setSale_type(rs.getInt("sale_type"));
                sale.setSale_status(rs.getInt("sale_status"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ConnectionManager.close(rs,ps,con);
            return sale;
        }
    }

    @Override
    public ArrayList<Sale> findSaleId(int emp_id, Timestamp sale_time, BigDecimal sale_payment, BigDecimal sale_change, int sale_type, int sale_status) {
        ArrayList<Sale> list = new ArrayList<Sale>();
        Sale info = null;
        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            //获取座位信息
            pstmt = con.prepareStatement("select * from sale where emp_id = ? and sale_type=? and sale_status = ? ");
            pstmt.setInt(1,emp_id);
//            pstmt.setBigDecimal(2,sale_payment);
//            pstmt.setBigDecimal(3,sale_change);
            pstmt.setInt(2,sale_type);
            pstmt.setInt(3,sale_status);
            // System.out.println(row,col);
            rs = pstmt.executeQuery();
            //System.out.println("what："+rs.next());
            //System.out.println("数据："+emp_id+","+sale_time+","+sale_payment+","+sale_change+","+sale_type+","+sale_status);

            while (rs.next()) {
                info = new Sale();
                //System.out.println("找到了：+AAAAAAAAAAAAAAAAAAAaa");
                info.setSale_ID(rs.getInt("sale_ID"));


                System.out.println(info.getSale_ID());
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

    public ArrayList<Sale> findSaleById(String sale_ID,int offset,int nums){
        ArrayList<Sale> list = new ArrayList<Sale>();
        Sale info = null;
        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = con.prepareStatement("select * from sale where emp_id = ? limit ?,?");
            ps.setString(1,sale_ID);
            ps.setInt(2,offset);
            ps.setInt(3,nums);
            rs = ps.executeQuery();
            while(rs.next()){
                info = new Sale();

                info.setSale_ID(rs.getInt("sale_ID"));
                info.setEmp_id(rs.getInt("emp_id"));
                info.setSale_time(rs.getTimestamp("sale_time"));
                info.setSale_payment(rs.getBigDecimal("sale_payment"));
                info.setSale_change(rs.getBigDecimal("sale_change"));
                info.setSale_type(rs.getInt("sale_type"));
                info.setSale_status(rs.getInt("sale_status"));

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