/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
import util.MyDB;


/**
 *
 * @author lenovo
 */
public class STAT {
    private Connection con;
    private Statement ste;

    public STAT() {
        con = MyDB.getInstance().getCnx();

    }
 public ObservableList<PieChart.Data> Stats() {
    String requete = "SELECT etat, COUNT(*) FROM user GROUP BY etat";
    try {
        Statement st2 = MyDB.getInstance().getCnx().createStatement();

        ResultSet rs = st2.executeQuery(requete);
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        while (rs.next()) {
            String etat = rs.getString(1);
            int count = rs.getInt(2);
            String etatLabel;
            if (etat.equals("Actif")) {
                etatLabel = "Actif";
            } else if (etat.equals("bloqué")) {
                etatLabel = "bloqué";
            } else if (etat.equals("debloqué")) {
                etatLabel = "debloqué";
            } else {
                etatLabel = etat; // fallback to original value if unknown state
            }
            PieChart.Data data = new PieChart.Data(etatLabel + " (" + "Nb utilisateurs=" + count + ")", count);
            pieChartData.add(data);
        }

        return pieChartData;

    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }

    return null;
}
 
 
 
 
 
 
 
}



  
