package sss.idao;

import sss.model.Schedule;

import java.util.ArrayList;
public interface ISchedule {
    // 增
    public boolean insert(Schedule schedule);

    // 删(根据id删除）
    public boolean delete(int sched_id);

    // 改
    public boolean update(Schedule schedule);

    // 查所有演出计划(一般用于和界面交互)
    public ArrayList<Schedule> findSchduleAll(int offset, int nums);

    public ArrayList<Schedule> findSchduleById(int play_id);

}
