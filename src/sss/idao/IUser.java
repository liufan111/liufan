package sss.idao;


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
    public ArrayList<User> findUserAll(int offset, int nums);

    public User findUserById(String emp_no);

    User findUserByNo(String name);


//    public Employee findEmployeeByNo(String emp_no);

}