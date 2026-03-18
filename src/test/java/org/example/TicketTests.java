package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TicketTests
{
    private Ticket ticket;

    @BeforeEach
    void setUp()
    {
        // Перед кожним тестом створюємо новий чистий квиток
        ticket = new Ticket(1, 3, 5, 150.0);
    }

    @Test
    void testSell_Success()
    {
        ticket.sell("Максим");
        assertTrue(ticket.isSold(), "Квиток має бути позначений як проданий");
        assertEquals("Максим", ticket.getOwnerName(), "Ім'я власника має збігатися");
    }

    @Test
    void testSell_AlreadySold_ThrowsException()
    {
        ticket.sell("Максим");
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            ticket.sell("Олена");
        });
        assertTrue(exception.getMessage().contains("вже проданий"));
    }

    @Test
    void testRefund_Success()
    {
        ticket.sell("Максим");
        ticket.refund("Максим");
        assertFalse(ticket.isSold(), "Квиток має стати вільним після повернення");
        assertNull(ticket.getOwnerName(), "Ім'я власника має бути очищено");
    }

    @Test
    void testRefund_WrongOwner_ThrowsException()
    {
        ticket.sell("Максим");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ticket.refund("Олена");
        });
        assertTrue(exception.getMessage().contains("Відмова! Цей квиток купував(ла)"));
    }

    @Test
    void testRefund_NotSold_ThrowsException()
    {
        assertThrows(IllegalStateException.class, () -> {
            ticket.refund("Максим");
        });
    }
}