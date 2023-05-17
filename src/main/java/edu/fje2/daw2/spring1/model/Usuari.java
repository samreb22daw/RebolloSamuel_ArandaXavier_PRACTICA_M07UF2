package edu.fje2.daw2.spring1.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

/***
 * Clase Usuari que utilitzarem per guardar dades a MongoDB
 */
public class Usuari {
    /***
     * Atributs de la clase
     */
    @Id
    private String username;

    private String email;

    private ArrayList<String> ciutats;

    /***
     * Constructor per defecte
     */
    public Usuari() {
    }

    /***
     *
     * @param username -> Al constructor guardarem per paràmetres l'atribut username
     * @param email -> Al constructor guardarem per paràmetres l'atribut email
     */
    public Usuari(String username, String email) {
        this.username = username;
        this.email = email;
        this.ciutats = new ArrayList<String>();
    }

    /***
     * Mètode getter
     *
     * @return -> retorna el username
     */
    public String getUsername() {
        return username;
    }

    /***
     * Mètode getter
     *
     * @return -> retorna l'email
     */
    public String getEmail() {
        return email;
    }

    /***
     * Mètode getter
     *
     * @return -> retorna el l'ArrayList de ciutats
     */
    public ArrayList<String> getCiutats() {
        return ciutats;
    }

    /***
     * Mètode setter per asignar valors a l'atribut ciutats
     * @param -> ciutats
     */
    public void setCiutats(ArrayList<String> ciutats) {
        this.ciutats = ciutats;
    }

    /***
     * Mètode toString
     *
     * @return -> Retorna els atributs de la clase en forma de cadena personalitzada
     */
    public String toString() {
        	return "Usuari: " + username + " amb email: " + email;
    }
}
