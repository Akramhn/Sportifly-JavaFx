package services;

import entities.Actualite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.MyDB;

public class ActualiteService implements IServiceAct<Actualite> {

    Connection cnx;

    public ActualiteService() {
        cnx = MyDB.getInstance().getCnx();
    }

    @Override
    public void ajouter(Actualite t) throws SQLException {
        String req = "insert into actualite(titre,image,description,categorie,date) values(?,?,?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setString(1, t.getTitre());
        ps.setString(2, t.getImage());
        ps.setString(3, t.getDescription());
        ps.setString(4, t.getCategorie());
        ps.setTimestamp(5, Timestamp.valueOf(t.getDate()));

        ps.executeUpdate();
    }

    @Override
    public void modifier(Actualite t) throws SQLException {
        String req = "update actualite set titre = ?, image = ?, description = ?, categorie = ?, date = ? where id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setString(1, t.getTitre());
        ps.setString(2, t.getImage());
        ps.setString(3, t.getDescription());
        ps.setString(4, t.getCategorie());
        ps.setTimestamp(5, Timestamp.valueOf(t.getDate()));
        ps.setInt(6, t.getId());

        ps.executeUpdate();

    }

    @Override
    public void supprimer(Actualite t) throws SQLException {
        String req = "delete from actualite where id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setInt(1, t.getId());

        ps.executeUpdate();
    }

    @Override
    public List<Actualite> recuperer() throws SQLException {
        List<Actualite> actualites = new ArrayList<>();

        String req = "select * from actualite";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Actualite act = new Actualite();
            act.setId(rs.getInt("id"));
            act.setTitre(rs.getString("titre"));
            act.setImage(rs.getString("image"));
            act.setDescription(rs.getString("description"));
            act.setCategorie(rs.getString("categorie"));
            Timestamp timestamp = rs.getTimestamp("date");
            LocalDateTime localDateTime = timestamp.toLocalDateTime();
            act.setDate(localDateTime);
            actualites.add(act);
        }

        return actualites;
    }

    public ObservableList<Actualite> afficher() throws SQLException {
        ObservableList<Actualite> actualites = FXCollections.observableArrayList();
        String req = "select * from actualite";
        try (Statement statement = cnx.createStatement();
                ResultSet resultSet = statement.executeQuery(req)) {
            while (resultSet.next()) {
                Actualite act = new Actualite();
                act.setId(resultSet.getInt("id"));
                act.setTitre(resultSet.getString("titre"));
                act.setImage(resultSet.getString("image"));
                act.setDescription(resultSet.getString("description"));
                act.setCategorie(resultSet.getString("categorie"));
                Timestamp timestamp = resultSet.getTimestamp("date");
                LocalDateTime localDateTime = timestamp.toLocalDateTime();
                act.setDate(localDateTime);
                actualites.add(act);
            }
        }
        return actualites;
    }

    /* public List<Actualite> recupererAvecCommentaires() throws SQLException {
    List<Actualite> actualites = new ArrayList<>();
    String requete = "SELECT a.id, a.titre, a.image, a.description, a.categorie, c.id, c.contenu, c.date " +
            "FROM actualite a LEFT JOIN commentaire_act c ON a.id = c.id_actualite_id " +
            "ORDER BY a.id";
    try (Statement statement = cnx.createStatement();
         ResultSet resultSet = statement.executeQuery(requete)) {
        int actualiteId = -1;
        Actualite actualite = null;
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            if (id != actualiteId) {
                String titre = resultSet.getString("titre");
                String image = resultSet.getString("image");
                String description = resultSet.getString("description");
                String categorie = resultSet.getString("categorie");
                Timestamp timestamp = resultSet.getTimestamp("date");
                LocalDateTime localDateTime = timestamp.toLocalDateTime();
                actualite = new Actualite(id, titre, image, description, categorie, date);
                actualites.add(actualite);
                actualiteId = id;
            }
            int commentaireId = resultSet.getInt("c.id");
            if (commentaireId != 0) {
                String contenu = resultSet.getString("contenu");
                Timestamp timestamp = resultSet.getTimestamp("date");
                LocalDateTime localDateTime = timestamp.toLocalDateTime();
                Commentaire_Act commentaire = new Commentaire_Act(commentaireId, contenu, date, actualite);
                actualite.getCommentaires().add(commentaire);
            }
        }
    }
    return actualites;
}*/
    public Actualite recupererById(int id) throws SQLException {
        String req = "select count(*) as nbr from actualite where id = ?";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        Actualite act = new Actualite();
        rs.next();
        act.setId(rs.getInt("id"));
        act.setTitre(rs.getString("titre"));
        act.setImage(rs.getString("image"));
        act.setDescription(rs.getString("description"));
        act.setCategorie(rs.getString("categorie"));
        Timestamp timestamp = rs.getTimestamp("date");
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        act.setDate(localDateTime);
        rs.getInt("nbr");

        return act;
    }

    public Actualite recupererById2(int id) throws SQLException {
        String req = "SELECT * FROM actualite WHERE id = ?";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        Actualite act = new Actualite();
        if (rs.next()) {
            act.setId(rs.getInt("id"));
            act.setTitre(rs.getString("titre"));
            act.setImage(rs.getString("image"));
            act.setDescription(rs.getString("description"));
            act.setCategorie(rs.getString("categorie"));
            Timestamp timestamp = rs.getTimestamp("date");
            LocalDateTime localDateTime = timestamp.toLocalDateTime();
            act.setDate(localDateTime);
                 }
        return act;
    }
}
