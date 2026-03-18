package org.example;

import java.util.Objects;

public class Ticket {
    private int id;
    private int row;
    private int seatNumber;
    private double price;
    private boolean isSold;
    private String ownerName;

    public Ticket(int id, int row, int seatNumber, double price) {
        this.id = id;
        this.row = row;
        this.seatNumber = seatNumber;
        this.price = price;
        this.isSold = false;
        this.ownerName = null;
    }

    public void sell(String buyerName)
    {
        if (isSold)
        {
            throw new IllegalStateException("Квиток (Ряд " + row + ", Місце " + seatNumber + ") вже проданий.");
        }
        this.isSold = true;
        this.ownerName = buyerName;
    }

    public void refund(String requesterName)
    {
        if (!isSold)
        {
            throw new IllegalStateException("Квиток не проданий, його не можна повернути.");
        }
        if (!this.ownerName.equalsIgnoreCase(requesterName))
        {
            throw new IllegalArgumentException("Відмова! Цей квиток купував(ла) " + this.ownerName + ", а ви ввели ім'я: " + requesterName);
        }

        this.isSold = false;
        this.ownerName = null;
    }

    public int getId()
    {
        return id;
    }
    public int getRow()
    {
        return row;
    }
    public int getSeatNumber()
    {
        return seatNumber;
    }
    public double getPrice()
    {
        return price;
    }
    public boolean isSold()
    {
        return isSold;
    }
    public String getOwnerName()
    {
        return ownerName;
    }
    public void setId(int id) { this.id = id; }
    public void setRow(int row) { this.row = row; }
    public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id && row == ticket.row && seatNumber == ticket.seatNumber && Double.compare(price, ticket.price) == 0 && isSold == ticket.isSold && Objects.equals(ownerName, ticket.ownerName);
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(id, row, seatNumber, price, isSold, ownerName);
    }
}
