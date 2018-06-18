package sss.idao;

import sss.model.Sale;
import sss.model.Seat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by zxw on 17-12-20.
 */
public interface ISeat {
    //增
    public boolean insert(Seat seat);

    //删
    public boolean delete(int studio_id);

    //改
    public boolean update(Seat seat);

    //查  更具影厅id查找座位信息
    public ArrayList<Seat> findSeatStateByStudioId(int id);

    public ArrayList<Seat> findSeatidByStudioId(int studio_id,int row,int col);
//    public boolean update(Seat seat);

    //查找所有座位
//    public ArrayList<Seat> findSeatAll();
    //根据影厅id查找
//    public Seat findSeatByStudioId(String studio_id);
}
