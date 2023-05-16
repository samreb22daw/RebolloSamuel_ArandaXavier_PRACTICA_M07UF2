package edu.fje2.daw2.spring1.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class Usuari {
    @Id
    private String username;

    private String email;

    private ArrayList<String> ciutats;

    public Usuari() {
    }

    public Usuari(String username, String email) {
        this.username = username;
        this.email = email;
        this.ciutats = new ArrayList<String>();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<String> getCiutats() {
        return ciutats;
    }

    public void destroyCiutats() {
        this.ciutats = new ArrayList<String>();
    }
    public void setCiutats(ArrayList<String> ciutats) {
        this.ciutats = ciutats;
    }

    public String toString() {
        	return "Usuari: " + username + " amb email: " + email;
    }


}
