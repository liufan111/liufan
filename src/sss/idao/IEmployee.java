package sss.idao;

import java.util.ArrayList;
import sss.model.Employee;

/**
 * Created by zxw on 17-11-19.
 */
public interface IEmployee {
    //增
    public boolean insert(Employee employee);
    // 删
    public boolean delete(int employeeId);

    // 改
    public boolean update(Employee employee);

    // 查所有用户(一般用于和界面交互)
    public ArrayList<Employee> findEmployeeAll(int offset,int nums);

    // 根据用户名查(一般用于和界面交互)
    public ArrayList<Employee> findEmployeeByName(String employeeName,int offset,int nums);

    // 根据用户id查(一般用于数据内部关联操作)
    public Employee findEmployeeById(int employeeId);

    public Employee findEmployeeByNo(String emp_no);
}
