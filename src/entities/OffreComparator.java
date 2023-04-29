/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Comparator;

/**
 *
 * @author wadah
 */
public class OffreComparator implements Comparator<Offre> {

    @Override
   public int compare(Offre o1, Offre o2) {
    float prix1 = o1.getPrix();
    float prix2 = o2.getPrix();
    if (prix1 < prix2) {
        return -1;
    } else if (prix1 > prix2) {
        return 1;
    } else {
        return 0;
    }
}


}
