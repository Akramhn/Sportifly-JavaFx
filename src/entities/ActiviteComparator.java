/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Comparator;

/**
 *
 * @author hadjn
 */
public class ActiviteComparator implements Comparator<Activiter> {

    @Override
    public int compare(Activiter o1, Activiter o2) {
         return o1.getTitre().compareTo(o2.getTitre());
    }
    
}
