package sss.idao;

import sss.model.Sale;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

public interface ISale {

    public boolean insert(Sale sale);

    public boolean delete(int sale_ID);

    public boolean update(Sale sale);

    public ArrayList<Sale> findSaleAll(int offset, int nums);

    public ArrayList<Sale> findSaleById(String id, int offset, int nums);

    public Sale findSaleById(String id);

    public ArrayList<Sale> findSaleId(int emp_id, Timestamp sale_time, BigDecimal sale_payment, BigDecimal sale_change, int sale_type, int sale_status);
}
