package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Session
{
    private int sessionId;
    private String movieName;
    private List<Ticket> tickets;

    public Session(int sessionId, String movieName)
    {
        this.sessionId = sessionId;
        this.movieName = movieName;
        this.tickets = new ArrayList<>();
    }

    public void addTicket(Ticket ticket)
    {
        tickets.add(ticket);
    }

    public double buyTicket(int row, int seatNumber, String buyerName)
    {
        for (Ticket ticket : tickets)
        {
            if (ticket.getRow() == row && ticket.getSeatNumber() == seatNumber)
            {
                ticket.sell(buyerName);
                return ticket.getPrice();
            }
        }
        throw new IllegalArgumentException("Місце не знайдено.");
    }

    public double refundTicket(int row, int seatNumber, String requesterName)
    {
        for (Ticket ticket : tickets)
        {
            if (ticket.getRow() == row && ticket.getSeatNumber() == seatNumber)
            {
                ticket.refund(requesterName); // Передаємо ім'я для перевірки
                return ticket.getPrice();
            }
        }
        throw new IllegalArgumentException("Місце не знайдено.");
    }
    public double getTotalRevenue()
    {
        return tickets.stream().filter(Ticket::isSold).mapToDouble(Ticket::getPrice).sum();
    }
    public long getAvailableTicketsCount()
    {
        return tickets.stream().filter(t -> !t.isSold()).count();
    }
    public long getSoldTicketsCount()
    {
        return tickets.stream().filter(Ticket::isSold).count();
    }
    public int getSessionId()
    {
        return sessionId;
    }
    public String getMovieName()
    {
        return movieName;
    }
    public List<Ticket> getTickets()
    {
        return tickets;
    }
    public void setSessionId(int sessionId) { this.sessionId = sessionId; }
    public void setMovieName(String movieName) { this.movieName = movieName; }
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }

    public void removeTicket(int ticketId) {
        tickets.removeIf(t -> t.getId() == ticketId);
    }

    public void updateTicketPrice(int ticketId, double newPrice)
    {
        for (Ticket ticket : tickets)
        {
            if (ticket.getId() == ticketId)
            {
                ticket.setPrice(newPrice);
                return;
            }
        }
        throw new IllegalArgumentException("Квиток з таким ID не знайдено");
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return sessionId == session.sessionId && Objects.equals(movieName, session.movieName) && Objects.equals(tickets, session.tickets);
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(sessionId, movieName, tickets);
    }
}
