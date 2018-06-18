package sss.model;

import java.sql.Time;

public class xiaoshou {
    private int sale_item_id; //id
    private float sale_item_price; //价格
    private int emp_id;  //销售员
    private int play_id;  //影片；
    private Time sale_time;  //销售时间
    private int sale_type;  //1卖 -1 退
    private int sale_status;  //0待付款 1已付款

    public int getSale_item_id() {
        return sale_item_id;
    }

    public void setSale_item_id(int sale_item_id) {
        this.sale_item_id = sale_item_id;
    }

    public float getSale_item_price() {
        return sale_item_price;
    }

    public void setSale_item_price(float sale_item_price) {
        this.sale_item_price = sale_item_price;
    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public int getPlay_id() {
        return play_id;
    }

    public void setPlay_id(int play_id) {
        this.play_id = play_id;
    }

    public Time getSale_time() {
        return sale_time;
    }

    public void setSale_time(Time sale_time) {
        this.sale_time = sale_time;
    }

    public int getSale_type() {
        return sale_type;
    }

    public void setSale_type(int sale_type) {
        this.sale_type = sale_type;
    }

    public int getSale_status() {
        return sale_status;
    }

    public void setSale_status(int sale_status) {
        this.sale_status = sale_status;
    }
}
