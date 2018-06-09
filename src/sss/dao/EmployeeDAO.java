package sss.dao;

import sss.ConnectionManager;
import sss.idao.IEmployee;
import sss.model.Employee;
import sss.model.Studio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by zxw on 17-11-19.
 */
public class EmployeeDAO implements IEmployee{
    /**
     * 存储用户信息
     * @return 成功与否boolean
     */
    @SuppressWarnings("finally")
    public boolean insert(Employee employee)
    {
        boolean result = false;
        if(employee == null)
            return result;

        // 获取Connection
        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        try
        {
            String sql = "insert into employee(emp_no, emp_name, emp_tel_num, emp_addr, emp_email) values(?,?,?,?,?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, employee.getEmp_no());
            pstmt.setString(2, employee.getEmp_name());
            pstmt.setString(3, employee.getEmp_tel_num());
            pstmt.setString(4, employee.getEmp_addr());
            pstmt.setString(5, employee.getEmp_email());

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

    /**
     * 删除用户(通过employeeId)
     * @return 成功与否boolean
     */
    @SuppressWarnings("finally")
    public boolean delete(int employeeId)
    {
        boolean result = false;
        if(employeeId == 0)
            return result;

        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        try
        {
            // 删除子某个用户
            String sql = "delete from employee where emp_id=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, employeeId);
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

    /**
     * 修改用户信息
     * @return 成功与否boolean
     */
    @SuppressWarnings("finally")
    public boolean update(Employee employee)
    {
        boolean result = false;
        if(employee == null)
            return result;

        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        try
        {
            String sql = "update employee set emp_no=?,emp_name=?,emp_tel_num=?,emp_addr=?,emp_email=? where emp_id=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, employee.getEmp_no());
            pstmt.setString(2, employee.getEmp_name());
            pstmt.setString(3, employee.getEmp_tel_num());
            pstmt.setString(4, employee.getEmp_addr());
            pstmt.setString(5, employee.getEmp_email());
            pstmt.setInt(6, employee.getEmp_id());

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
    public ArrayList<Employee> findEmployeeAll(int offset, int nums) {
        ArrayList<Employee> list = new ArrayList<Employee>();
        Employee info = null;

        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            // 获取所有用户数据
            pstmt = con.prepareStatement("select * from employee limit ? , ?");
            pstmt.setInt(1,offset);
            pstmt.setInt(2,nums);
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                info = new Employee();

                info.setEmp_id(rs.getInt("emp_id"));
                info.setEmp_no(rs.getString("emp_no"));
                info.setEmp_name(rs.getString("emp_name"));
                info.setEmp_tel_num(rs.getString("emp_tel_num"));
                info.setEmp_addr(rs.getString("emp_addr"));
                info.setEmp_email(rs.getString("emp_email"));
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
    public ArrayList<Employee> findEmployeeByName(String employeeName, int offset, int nums) {
        if(employeeName == null || employeeName.equals(""))
            return null;

        ArrayList<Employee> list = new ArrayList<Employee>();
        Employee info = null;

        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        try
        {
            String sql = "select * from employee where employee_name like ? limit ? , ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "%" + employeeName + "%");// 拼接模糊查询串
            pstmt.setInt(2,offset);
            pstmt.setInt(3,nums);
            System.out.print(pstmt);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
            {
                // 如果有值的话再实例化
                info = new Employee();
                info.setEmp_id(rs.getInt("emp_id"));
                info.setEmp_no(rs.getString("emp_no"));
                info.setEmp_name(rs.getString("emp_name"));
                info.setEmp_tel_num(rs.getString("emp_tel_num"));
                info.setEmp_addr(rs.getString("emp_addr"));
                info.setEmp_email(rs.getString("emp_email"));
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

    /**
     * 获取所有用户信息(一般用于和界面交互)
     * @return Employee列表
     */
    @SuppressWarnings("finally")
    public ArrayList<Employee> findEmployeeAll()
    {
        ArrayList<Employee> list = new ArrayList<Employee>();
        Employee info = null;

        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            // 获取所有用户数据
            pstmt = con.prepareStatement("select * from employee");
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                info = new Employee();

                info.setEmp_id(rs.getInt("emp_id"));
                info.setEmp_no(rs.getString("emp_no"));
                info.setEmp_name(rs.getString("emp_name"));
                info.setEmp_tel_num(rs.getString("emp_tel_num"));
                info.setEmp_addr(rs.getString("emp_addr"));
                info.setEmp_email(rs.getString("emp_email"));
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

    /**
     * 根据用户名获取用户信息(一般用于和界面交互)
     * @return Employee列表
     */
    @SuppressWarnings("finally")
    public ArrayList<Employee> findEmployeeByName(String employeeName)
    {
        if(employeeName == null || employeeName.equals(""))
            return null;

        ArrayList<Employee> list = new ArrayList<Employee>();
        Employee info = null;

        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            // 获取所有用户数据:模糊查询
            pstmt = con.prepareStatement("select * from employee where emp_name like ?");
            pstmt.setString(1, "%" + employeeName + "%");// 拼接模糊查询串
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                // 如果有值的话再实例化
                info = new Employee();
                info.setEmp_id(rs.getInt("emp_id"));
                info.setEmp_no(rs.getString("emp_no"));
                info.setEmp_name(rs.getString("emp_name"));
                info.setEmp_tel_num(rs.getString("emp_tel_num"));
                info.setEmp_addr(rs.getString("emp_addr"));
                info.setEmp_email(rs.getString("emp_email"));
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

    /**
     * 根据employee_id获取用户信息(一般用于数据内部关联操作)
     * @return 用户
     */
    @SuppressWarnings("finally")
    public Employee findEmployeeById(int employeeId)
    {
        Employee info = null;
        if(employeeId == 0)
            return info;

        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            // 获取所有用户数据
            pstmt = con.prepareStatement("select * from employee where emp_id=?");
            pstmt.setInt(1, employeeId);
            rs = pstmt.executeQuery();
            if(rs.next())
            {
                // 如果有值的话再实例化
                info = new Employee();
                info.setEmp_id(employeeId);
                info.setEmp_no(rs.getString("emp_no"));
                info.setEmp_name(rs.getString("emp_name"));
                info.setEmp_tel_num(rs.getString("emp_tel_num"));
                info.setEmp_addr(rs.getString("emp_addr"));
                info.setEmp_email(rs.getString("emp_email"));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectionManager.close(rs, pstmt, con);
            return info;
        }
    }

    /*根据用户编号找用户*/
    public Employee findEmployeeByNo(String emp_no)
    {
        Employee info = null;
        if(emp_no == null || emp_no.equals(""))
            return info;

        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            // 获取所有用户数据
            pstmt = con.prepareStatement("select * from employee where emp_no=?");
            pstmt.setString(1, emp_no);
            rs = pstmt.executeQuery();
            if(rs.next())
            {
                // 如果有值的话再实例化
                info = new Employee();
                info.setEmp_id(rs.getInt("emp_id"));
                info.setEmp_no(rs.getString("emp_no"));
                info.setEmp_name(rs.getString("emp_name"));
                info.setEmp_tel_num(rs.getString("emp_tel_num"));
                info.setEmp_addr(rs.getString("emp_addr"));
                info.setEmp_email(rs.getString("emp_email"));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectionManager.close(rs, pstmt, con);
            return info;
        }
    }

}
