package services;

import entities.Event;
import util.MyDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventService {

    private static EventService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public EventService() {
        connection = util.MyDB.getInstance().getCnx();
    }

    public static EventService getInstance() {
        if (instance == null) {
            instance = new EventService();
        }
        return instance;
    }

    public List<Event> getAll() {
        List<Event> listEvent = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `event`");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listEvent.add(new Event(
                        resultSet.getInt("id"),
                        resultSet.getString("titre"),
                        resultSet.getString("description"),
                        LocalDate.parse(String.valueOf(resultSet.getDate("date"))),
                        resultSet.getString("img"),
                        resultSet.getString("lieu")

                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) event : " + exception.getMessage());
        }
        return listEvent;
    }


    public boolean add(Event event,int id) {

        String request = "INSERT INTO `event`(`titre`,`id_user_id`, `description`, `date`, `img`, `lieu`) VALUES(?,?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setString(1, event.getTitre());
            preparedStatement.setInt(2, id);
            preparedStatement.setString(3, event.getDescription());
            preparedStatement.setDate(4, Date.valueOf(event.getDate()));
            preparedStatement.setString(5, event.getImage());
            preparedStatement.setString(6, event.getLieu());

            preparedStatement.executeUpdate();
            System.out.println("Event added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) event : " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(Event event) {

        String request = "UPDATE `event` SET `titre` = ?, `description` = ?, `date` = ?, `img` = ?, `lieu` = ? WHERE `id`=" + event.getId();
        try {
           
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setString(1, event.getTitre());
            preparedStatement.setString(2, event.getDescription());
            preparedStatement.setDate(3, Date.valueOf(event.getDate()));
            preparedStatement.setString(4, event.getImage());
            preparedStatement.setString(5, event.getLieu());

            preparedStatement.executeUpdate();
            System.out.println("Event edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) event : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `event` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Event deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) event : " + exception.getMessage());
        }
        return false;
    }
}
