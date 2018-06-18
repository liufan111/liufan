package sss.idao;


import sss.model.EmpUser;
import sss.model.Employee;
import sss.model.User;

import java.util.ArrayList;

/**
 * Created by zxw on 17-11-19.
 */
public interface IUser {
    // 增
    public boolean insert(User user);

    // 删
    public boolean delete(String emp_no);

    // 改
    public boolean update(User user);

    // 查所有用户(一般用于和界面交互)
    public ArrayList<EmpUser> findUserAll(int offset, int nums);

    //模糊查找
    public ArrayList<EmpUser> findUserByName(String userName,int offset,int nums);

    public User findUserById(String emp_no);

    public User findUserByNo(String name);

    //查找不在登录表里面的员工
    public ArrayList<EmpUser> findEmpnotinUser();

    public ArrayList<User> findUserShoupiao();


//    public Employee findEmployeeByNo(String emp_no);

}