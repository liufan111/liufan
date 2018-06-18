package sss.idao;

import sss.model.Schedule;
import sss.model.Studio;

import java.util.ArrayList;
public interface ISchedule {
    // 增
    public int insert(Schedule schedule);

    // 删(根据id删除）
    public boolean delete(int sched_id);

    // 改
    public boolean update(Schedule schedule);

    // 查所有演出计划(一般用于和界面交互)
    public ArrayList<Schedule> findScheduleAll(int offset, int nums);

    public ArrayList<Schedule> findScheduleById(int play_id);
    // 根据剧目id查(一般用于数据内部关联操作)
    public ArrayList<Schedule> findStudioByiid(int play_id, int offset, int nums);
}
