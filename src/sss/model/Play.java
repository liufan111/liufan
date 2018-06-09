package sss.model;

import java.math.BigDecimal;


public class Play {
    private int play_id;
    private int play_type_id;
    private int play_lang_id;
    private String play_name;
    private String play_introduction;
    private String play_image;
    private int play_length;
    private BigDecimal play_ticket_price;
    private int play_status;

    public int getPlay_id() {
        return play_id;
    }

    public void setPlay_id(int play_id) {
        this.play_id = play_id;
    }

    public int getPlay_type_id() {
        return play_type_id;
    }

    public void setPlay_type_id(int play_type_id) {
        this.play_type_id = play_type_id;
    }

    public int getPlay_lang_id() {
        return play_lang_id;
    }

    public void setPlay_lang_id(int play_lang_id) {
        this.play_lang_id = play_lang_id;
    }

    public String getPlay_name() {
        return play_name;
    }

    public void setPlay_name(String play_name) {
        this.play_name = play_name;
    }

    public String getPlay_introduction() {
        return play_introduction;
    }

    public void setPlay_introduction(String play_introduction) {
        this.play_introduction = play_introduction;
    }

    public String getPlay_image() {
        return play_image;
    }

    public void setPlay_image(String play_image) {
        this.play_image = play_image;
    }

    public int getPlay_length() {
        return play_length;
    }

    public void setPlay_length(int play_length) {
        this.play_length = play_length;
    }

    public BigDecimal getPlay_ticket_price() {
        return play_ticket_price;
    }

    public void setPlay_ticket_price(BigDecimal play_ticket_price) {
        this.play_ticket_price = play_ticket_price;
    }

    public int getPlay_status() {
        return play_status;
    }

    public void setPlay_status(int play_status) {
        this.play_status = play_status;
    }

    @Override
    public String toString() {
        return "Play{" +
                "play_id=" + play_id +
                ", play_type_id=" + play_type_id +
                ", play_lang_id=" + play_lang_id +
                ", play_name='" + play_name + '\'' +
                ", play_introduction='" + play_introduction + '\'' +
                ", play_image='" + play_image + '\'' +
                ", play_length=" + play_length +
                ", play_ticket_price=" + play_ticket_price +
                ", play_status=" + play_status +
                '}';
    }
}

