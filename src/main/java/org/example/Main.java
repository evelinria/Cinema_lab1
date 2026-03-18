package org.example;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Cinema cinema = new Cinema("Absolute");

        //Створюємо сеанси
        Session session1 = new Session(1, "Дюна: Частина друга");
        Session session2 = new Session(2, "Дедпул та Росомаха");
        Session session3 = new Session(3, "Крик 7");
        Session session4 = new Session(4, "Тихе місце 2");
        Session session5 = new Session(5, "Зоотрополіс 2");

        cinema.addSession(session1);
        cinema.addSession(session2);
        cinema.addSession(session3);
        cinema.addSession(session4);
        cinema.addSession(session5);
        //Перший сеанс
        int ticketIdCounter = 1;

        for (Session session : cinema.getSessions())
        {
            for (int row = 1; row <= 5; row++)
            {
                for (int seat = 1; seat <= 5; seat++)
                {
                    // Ряди 1-4 звичайні (150 грн), ряд 5 VIP (250 грн)
                    double price = (row == 5) ? 250.0 : 150.0;
                    session.addTicket(new Ticket(ticketIdCounter++, row, seat, price));
                }
            }
        }

        System.out.println("---Початок продажів---");
        Random random = new Random();
        List<Session> allSessions = cinema.getSessions();

        for (int person = 1; person <= 40; person++)
        {
            Session randomSession = allSessions.get(random.nextInt(allSessions.size()));
            List<Ticket> availableTickets = randomSession.getTickets().stream()
                    .filter(t -> !t.isSold())
                    .collect(Collectors.toList());

            if (!availableTickets.isEmpty())
            {
                Ticket desiredTicket = availableTickets.get(random.nextInt(availableTickets.size()));
                try
                {
                    double price = randomSession.buyTicket(desiredTicket.getRow(), desiredTicket.getSeatNumber(), "Глядач_" + person);

                    System.out.println("Глядач_" + person + " купив квиток: '"
                            + randomSession.getMovieName() + "', Ряд " + desiredTicket.getRow()
                            + ", Місце " + desiredTicket.getSeatNumber() + ". Вартість: " + price + " грн.");
                }
                catch (Exception e)
                {
                    System.out.println("Помилка: " + e.getMessage());
                }
            }
            else
            {
                System.out.println("Глядач_" + person + " хотів піти на '"
                        + randomSession.getMovieName() + "', але всі квитки розкупили!");
            }
        }
        System.out.println("Частину квитків вже розкуплено.\n");

        System.out.println("\n--- Результати роботи ---");
        System.out.println(cinema.getStatistics());

        Scanner scanner = new Scanner(System.in);

        System.out.println("Ласкаво просимо до кінотеатру " + cinema.getName() + "!");
        System.out.print("Введіть ваше ім'я для авторизації: ");
        String currentUser = scanner.nextLine();
        if (currentUser.trim().isEmpty())
        {
            currentUser = "Гість";
        }
        System.out.println("Вітаємо, " + currentUser + "!");

        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n=================================");
            System.out.println("Головне меню (" + currentUser + "):");
            System.out.println("1. Доступні сеанси");
            System.out.println("2. Купити квиток");
            System.out.println("3. Повернути квиток");
            System.out.println("4. Загальна статистика");
            System.out.println("5. Мої квитки");
            System.out.println("0. Вийти");
            System.out.println("=================================");
            System.out.print("Оберіть дію: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Помилка: Введіть число");
                continue;
            }

            switch (choice) {
                case 1:
                    showSessions(cinema);
                    break;

                case 2:
                    System.out.println("\n--- Купівля квитка ---");
                    System.out.print("Введіть ID сеансу (або 0 для відміни): ");
                    int sessionIdToBuy = Integer.parseInt(scanner.nextLine());
                    if (sessionIdToBuy == 0) break;

                    try { showSeatMap(cinema, sessionIdToBuy); } catch (Exception e) { break; }

                    System.out.print("\nВведіть номер ряду 1-5 (або 0 для відміни): ");
                    int rowToBuy = Integer.parseInt(scanner.nextLine());
                    if (rowToBuy == 0) break;

                    System.out.print("Введіть номер місця 1-5 (або 0 для відміни): ");
                    int seatToBuy = Integer.parseInt(scanner.nextLine());
                    if (seatToBuy == 0) break;

                    try {
                        Session session = cinema.getSession(sessionIdToBuy);
                        // Автоматично використовуємо currentUser!
                        double price = session.buyTicket(rowToBuy, seatToBuy, currentUser);
                        System.out.println("Успішно! Ви (" + currentUser + ") купили квиток на '" + session.getMovieName() + "'. До сплати: " + price + " грн.");
                    } catch (Exception e) {
                        System.out.println("Помилка покупки: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("\n--- Повернення квитка ---");
                    java.util.List<Session> userSessions = new java.util.ArrayList<>();
                    java.util.List<Ticket> userTickets = new java.util.ArrayList<>();
                    int ticketIndex = 1;

                    for (Session session : cinema.getSessions()) {
                        for (Ticket ticket : session.getTickets()) {
                            if (ticket.isSold() && currentUser.equalsIgnoreCase(ticket.getOwnerName())) {
                                System.out.println(ticketIndex + ". Фільм: '" + session.getMovieName() +
                                        "' | Ряд: " + ticket.getRow() +
                                        " | Місце: " + ticket.getSeatNumber() +
                                        " | Ціна: " + ticket.getPrice() + " грн");
                                userSessions.add(session);
                                userTickets.add(ticket);
                                ticketIndex++;
                            }
                        }
                    }

                    // 2. Якщо квитків немає - виходимо
                    if (userTickets.isEmpty()) {
                        System.out.println("У вас немає куплених квитків для повернення.");
                        break;
                    }

                    // 3. Просимо вибрати квиток зі списку
                    System.out.print("\nВведіть номер квитка, який хочете повернути (або 0 для відміни): ");
                    int refundChoice = Integer.parseInt(scanner.nextLine());

                    if (refundChoice == 0) {
                        System.out.println("Дію скасовано.");
                        break;
                    }

                    if (refundChoice < 1 || refundChoice > userTickets.size()) {
                        System.out.println("Помилка: Невірний номер квитка.");
                        break;
                    }

                    // 4. Робимо повернення вибраного квитка
                    Session chosenSession = userSessions.get(refundChoice - 1);
                    Ticket chosenTicket = userTickets.get(refundChoice - 1);

                    try
                    {
                        double refundedAmount = chosenSession.refundTicket(chosenTicket.getRow(), chosenTicket.getSeatNumber(), currentUser);
                        System.out.println("Ваш квиток на '" + chosenSession.getMovieName() + "' успішно скасовано. Вам відшкодовано: " + refundedAmount + " грн.");
                    }
                    catch (Exception e)
                    {
                        System.out.println("Помилка: " + e.getMessage());
                    }

                    break;
                case 4:
                    System.out.println("\n" + cinema.getStatistics());
                    break;
                case 5:
                    showMyTickets(cinema, currentUser);
                    break;
                case 0:
                    isRunning = false;
                    System.out.println("Дякуємо, що обрали наш кінотеатр! До побачення, " + currentUser + ".");
                    break;
                default:
                    System.out.println("Невідома команда. Спробуйте ще раз.");
            }
        }
        scanner.close();
    }

    private static void showSessions(Cinema cinema)
    {
        System.out.println("\n--- Доступні сеанси ---");
        for (Session session : cinema.getSessions())
        {
            System.out.println("ID: " + session.getSessionId() + " | Фільм: " + session.getMovieName());
        }
    }
    private static void showSeatMap(Cinema cinema, int sessionId)
    {
        try
        {
            Session session = cinema.getSession(sessionId);
            System.out.println("\n--- Зал для сеансу: " + session.getMovieName() + " ---");
            System.out.println("[О] - вільно, [Х] - зайнято\n");

            System.out.print("             ");
            for (int seat = 1; seat <= 5; seat++)
            {
                System.out.print(" " + seat + "  ");
            }
            System.out.println();

            List<Ticket> tickets = session.getTickets();
            int currentTicketIndex = 0;

            for (int row = 1; row <= 5; row++)
            {
                System.out.print("Ряд " + row + (row == 5 ? " (VIP): " : " (Std): "));
                for (int seat = 1; seat <= 5; seat++)
                {
                    Ticket t = tickets.get(currentTicketIndex++);
                    if (t.isSold())
                    {
                        System.out.print("[Х] ");
                    }
                    else
                    {
                        System.out.print("[О] ");
                    }
                }
                System.out.println();
            }
        }
        catch (Exception e)
        {
            System.out.println("Помилка: " + e.getMessage());
        }
    }
    private static void showMyTickets(Cinema cinema, String userName) {
        System.out.println("\n Ваші квитки, " + userName + " ---");
        boolean hasTickets = false;

        for (Session session : cinema.getSessions()) {
            for (Ticket ticket : session.getTickets()) {
                // Перевіряємо, чи квиток проданий і чи збігається ім'я
                if (ticket.isSold() && userName.equalsIgnoreCase(ticket.getOwnerName()))
                {
                    System.out.println("🎬 Фільм: '" + session.getMovieName() +
                            "' | Ряд: " + ticket.getRow() +
                            " | Місце: " + ticket.getSeatNumber() +
                            " | Ціна: " + ticket.getPrice() + " грн");
                    hasTickets = true;
                }
            }
        }
        if (!hasTickets)
        {
            System.out.println("У вас поки немає куплених квитків.");
        }
    }
}