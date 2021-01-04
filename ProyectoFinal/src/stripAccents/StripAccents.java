/*
 * 39494048N
 * Pablo Vidal Fernández
 * pablovidalfdez@usal.es
 * Programación III, Grado en Estadística
 */
package stripAccents;

import java.text.Normalizer;

/**
 *
 * @author pablo
 */
public class StripAccents {
    
    public static String stripAccents(String s) {
    
    s = Normalizer.normalize(s, Normalizer.Form.NFD);
    s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    return s;
    }
    
}
