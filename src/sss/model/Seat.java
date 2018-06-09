package sss.model;

/**
 * Created by zxw on 17-12-20.
 */
public class Seat {
    private int seat_id;
    private int studio_id;
    private int seat_row;
    private int seat_column;
    private int seat_status;

public int getSeat_id(){
    return seat_id;
}
public int getStudio_id(){
    return studio_id;
}
public int getSeat_row(){
    return seat_row;
}
public int getSeat_column(){
    return seat_column;
}
public int getSeat_status(){
    return seat_status;
}

public void setSeat_id(int seat_id){
    this.seat_id = seat_id;
}
public void setStudio_id(int studio_id){
    this.studio_id = studio_id;
}
public void setSeat_row(int seat_row){
    this.seat_id = seat_id;
}
public void setSeat_column(int seat_column){
    this.seat_column = seat_column;
}
public void setSeat_status(int seat_status){
    this.seat_status = seat_status;
}

    @Override
    public String toString() {
        return "Seat_server{"+
                "seat_id=" + seat_id +
                ", studio_id='" + studio_id +
                ", seat_row=" + seat_row +
                ", seat_column=" + seat_column +
                ", seat_status=" + seat_status +
                '}';
    }
}
