package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SessionTests
{
    private Session session;

    @BeforeEach
    void setUp()
    {
        session = new Session(1, "Матриця");
        // Додаємо два квитки: один звичайний, один VIP
        session.addTicket(new Ticket(101, 1, 1, 150.0));
        session.addTicket(new Ticket(102, 5, 1, 250.0));
    }

    @Test
    void testBuyTicket_Success_And_Calculations()
    {
        double pricePaid = session.buyTicket(5, 1, "Андрій");

        assertEquals(250.0, pricePaid, "Має повернутись ціна купленого квитка");
        assertEquals(250.0, session.getTotalRevenue(), "Виручка має оновитися");
        assertEquals(1, session.getSoldTicketsCount(), "Кількість проданих має бути 1");
        assertEquals(1, session.getAvailableTicketsCount(), "Кількість доступних має зменшитись");
    }

    @Test
    void testBuyTicket_SeatNotFound_ThrowsException()
    {
        // Намагаємось купити місце, якого немає в залі (ряд 99)
        assertThrows(IllegalArgumentException.class, () -> {
            session.buyTicket(99, 99, "Андрій");
        }, "Має викидатись виняток, якщо місце не знайдено");
    }

    @Test
    void testRefundTicket_Success()
    {
        session.buyTicket(1, 1, "Ігор");
        double refunded = session.refundTicket(1, 1, "Ігор");

        assertEquals(150.0, refunded, "Має повернути вартість квитка");
        assertEquals(0.0, session.getTotalRevenue(), "Виручка має стати 0 після повернення");
    }
}