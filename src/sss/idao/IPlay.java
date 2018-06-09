package sss.idao;

import sss.model.Play;

import java.util.ArrayList;

public interface IPlay {
    //增
    public int  insert(Play play);

    //删
    public boolean delete(int play_id);

    //改
    public boolean update(Play play);

    //查询所有剧目信息<一般用于界面交互>
    public ArrayList<Play> findPlayAll(int offset, int nums);

    //根据剧目id查询<一般用于数据内部关联操作>
    public ArrayList<Play> findPlayByname(String play_name,int offset,int nums);

    public Play findPlayById(int id);
}

