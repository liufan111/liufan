package sss.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

public class Sale implements Serializable{
    private int sale_ID;
    private int emp_id;
    private Timestamp sale_time;
    private BigDecimal sale_payment;
    private BigDecimal sale_change;
    private int sale_type;
    private int sale_status;

    public int getSale_ID() {
        return sale_ID;
    }

    public void setSale_ID(int sale_ID) {
        this.sale_ID = sale_ID;
    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public Timestamp getSale_time() {
        return sale_time;
    }

    public void setSale_time(Timestamp sale_time) {
        this.sale_time = sale_time;
    }

    public BigDecimal getSale_payment() {
        return sale_payment;
    }

    public void setSale_payment(BigDecimal sale_payment) {
        this.sale_payment = sale_payment;
    }

    public BigDecimal getSale_change() {
        return sale_change;
    }

    public void setSale_change(BigDecimal sale_change) {
        this.sale_change = sale_change;
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

    @Override
    public String toString() {
        return "Sale_server{" +
                "sale_ID=" + sale_ID +
                ",emp_id=" + emp_id +
                ",sale_time='" + sale_time +'\''+
                ",sale_payment=" + sale_payment+
                ",sale_change=" + sale_change +
                ",sale_type=" + sale_type +
                ",sale_status=" + sale_status +
                '}';
    }
}
