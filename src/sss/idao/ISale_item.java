package sss.idao;

import sss.model.Sale_item;

import java.util.ArrayList;

public interface ISale_item {
    public boolean insert(Sale_item si);

    public ArrayList<Sale_item> findSaleAll();

    public Sale_item findSaleByID(int id);
}
