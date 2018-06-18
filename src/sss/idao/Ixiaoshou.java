package sss.idao;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import sss.model.xiaoshou;
public interface Ixiaoshou {
    public ArrayList<xiaoshou> findxiaoshouAll(int movie, int people, String start,String end);
}
