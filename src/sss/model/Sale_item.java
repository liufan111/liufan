package sss.model;

public class Sale_item {
    private int sale_item_id;
    private int ticket_id;
    private int sale_ID;
    private float sale_item_price;

    public int getSale_item_id() {
        return sale_item_id;
    }

    public void setSale_item_id(int sale_item_id) {
        this.sale_item_id = sale_item_id;
    }

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public int getSale_ID() {
        return sale_ID;
    }

    public void setSale_ID(int sale_ID) {
        this.sale_ID = sale_ID;
    }

    public float getSale_item_price() {
        return sale_item_price;
    }

    public void setSale_item_price(float sale_item_price) {
        this.sale_item_price = sale_item_price;
    }


}
