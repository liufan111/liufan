package sss.idao;

import sss.model.Sale;
import sss.model.Ticket;

import java.util.ArrayList;

public interface ITicket {
    public boolean insert(Ticket ticket);

    public boolean delete(int ticket_id);

    public boolean update(Ticket ticket);

    public ArrayList<Ticket> findTicketAll(int offset, int nums);

    public Ticket findTicketById(int id);

    public boolean updateTicket(Ticket t);

    public boolean updateTicket_lock(Ticket t);

    public ArrayList<Ticket> findTicketByname(String name, int offset, int nums);

    public ArrayList<Ticket> findTicketIDBySeatId(int id,int sched_id);
}
