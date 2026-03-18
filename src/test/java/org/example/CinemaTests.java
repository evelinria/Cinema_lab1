package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CinemaTests
{
    private Cinema cinema;
    private Session session;

    @BeforeEach
    void setUp()
    {
        cinema = new Cinema("Планета Кіно");
        session = new Session(1, "Аватар");
        cinema.addSession(session);
    }

    @Test
    void testGetSession_Success()
    {
        Session retrievedSession = cinema.getSession(1);

        assertNotNull(retrievedSession, "Сеанс має бути знайдений");
        assertEquals("Аватар", retrievedSession.getMovieName(), "Назва фільму має збігатися");
    }

    @Test
    void testGetSession_NotFound_ThrowsException()
    {
        // Шукаємо сеанс з ID, якого не існує
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cinema.getSession(999);
        });
        assertEquals("Сеанс не знайдено", exception.getMessage());
    }
}