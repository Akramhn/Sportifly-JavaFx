package services;

import entities.Participant;
import entities.User;
import util.MyDB;
import util.RelationObject;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ParticipantService {

    private static ParticipantService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public ParticipantService() {
        connection = util.MyDB.getInstance().getCnx();
    }

    public static ParticipantService getInstance() {
        if (instance == null) {
            instance = new ParticipantService();
        }
        return instance;
    }

    public List<Participant> getAll() {
        
        List<Participant> listParticipant = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM `participant` AS x " +
                    "RIGHT JOIN `user` AS y ON x.id_user_id = y.id " +
                    "RIGHT JOIN `event` AS z ON x.event_id = z.id " +
                    "WHERE x.id_user_id = y.id " +
                    "AND x.event_id = z.id");
            
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listParticipant.add(new Participant(
                        resultSet.getInt("id"),
                        LocalDate.parse(String.valueOf(resultSet.getDate("date_participation"))),
                        new User(resultSet.getInt("id_user_id"), resultSet.getString("y.lastname")),
                        new RelationObject(resultSet.getInt("event_id"), resultSet.getString("z.titre"))

                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) participant : " + exception.getMessage());
        }
        return listParticipant;
    }

    public List<User> getAllUsers() {
        List<User> listUsers = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `user`");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listUsers.add(new User(resultSet.getInt("id"), resultSet.getString("lastname")));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) users : " + exception.getMessage());
        }
        return listUsers;
    }

    public List<RelationObject> getAllEvents() {
        List<RelationObject> listEvents = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `event`");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listEvents.add(new RelationObject(resultSet.getInt("id"), resultSet.getString("titre")));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) events : " + exception.getMessage());
        }
        return listEvents;
    }


    public boolean add(Participant participant) {

        String request = "INSERT INTO `participant`(`date_participation`, `id_user_id`, `event_id`) VALUES(?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setDate(1, Date.valueOf(participant.getDateParticipation()));
            preparedStatement.setInt(2, participant.getUser().getId());
            preparedStatement.setInt(3, participant.getEvent().getId());

            preparedStatement.executeUpdate();
            System.out.println("Participant added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) participant : " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(Participant participant) {

        String request = "UPDATE `participant` SET `date_participation` = ?, `id_user_id` = ?, `event_id` = ? WHERE `id`=" + participant.getId();
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setDate(1, Date.valueOf(participant.getDateParticipation()));
            preparedStatement.setInt(2, participant.getUser().getId());
            preparedStatement.setInt(3, participant.getEvent().getId());

            preparedStatement.executeUpdate();
            System.out.println("Participant edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) participant : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `participant` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Participant deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) participant : " + exception.getMessage());
        }
        return false;
    }
}
