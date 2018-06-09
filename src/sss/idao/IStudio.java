package sss.idao;

import sss.model.Studio;

import java.util.ArrayList;

/**
 * Created by zxw on 17-12-18.
 */
public interface IStudio {
    // 增
    public boolean insert(Studio studio);

    // 删
    public boolean delete(int studio_id);

    // 改
    public boolean update(Studio studio);

    // 查所有演出厅(一般用于和界面交互
    public ArrayList<Studio> findStudioAll(int offset, int nums);


    // 根据用户id查(一般用于数据内部关联操作)
    public ArrayList<Studio> findStudioByname(String studio_name,int offset,int nums);

    public Studio findStudioById(int id);
}
