package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cinema
{
    private String name;
    private List<Session> sessions;
    public Cinema(String name)
    {
        this.name = name;
        this.sessions = new ArrayList<>();
    }
    public void addSession(Session session)
    {
        sessions.add(session);
    }
    public Session getSession(int sessionId)
    {
        return sessions.stream()
                .filter(s -> s.getSessionId() == sessionId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Сеанс не знайдено"));
    }
    public String getStatistics()
    {
        StringBuilder stats = new StringBuilder();
        stats.append("Статистика кінотеатру: ").append(name).append("\n");
        double totalCinemaRevenue = 0;

        for (Session session : sessions)
        {
            stats.append("Сеанс: ").append(session.getMovieName())
                    .append(" | Продано: ").append(session.getSoldTicketsCount())
                    .append(" | Залишилось: ").append(session.getAvailableTicketsCount())
                    .append(" | Виручка: ").append(session.getTotalRevenue()).append("\n");
            totalCinemaRevenue += session.getTotalRevenue();
        }
        stats.append("Загальна виручка кінотеатру: ").append(totalCinemaRevenue);
        return stats.toString();
    }
    public String getName()
    {
        return name;
    }
    public List<Session> getSessions()
    {
        return sessions;
    }
    public void setName(String name) { this.name = name; }
    public void setSessions(List<Session> sessions) { this.sessions = sessions; }

    public void removeSession(int sessionId) {
        boolean removed = sessions.removeIf(s -> s.getSessionId() == sessionId);
        if (!removed) {
            throw new IllegalArgumentException("Сеанс з таким ID не знайдено");
        }
    }

    public void updateSessionName(int sessionId, String newName) {
        Session session = getSession(sessionId);
        session.setMovieName(newName);
    }
    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass()) return false;
        Cinema cinema = (Cinema) o;
        return Objects.equals(name, cinema.name) && Objects.equals(sessions, cinema.sessions);
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(name, sessions);
    }
}
