package edu.fje2.daw2.spring1.controladors;

import edu.fje2.daw2.spring1.model.Usuari;
import edu.fje2.daw2.spring1.repositoris.UsuariRepositori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador de usuaris
 * @desc Ens permet comunicarnos entre les vistes i el model
 * @author Xavier Aranda / Samuel Rebollo
 * @version 1.0
 * @modul M07UF2
 */
@Controller
@SessionAttributes("usuaris")
public class UsuarisController {

    @Autowired
    private UserDetailsService userDetailsService;

    /***
     * Definim una instància de UsuariRepositori per poder fer la persistència de dades a MongoDB
     */
    @Autowired
    private UsuariRepositori repositori;

    /***
     * Definim un model de dades anomenat usuaris
     *
     * @return -> Retorna el model de dades usuaris
     */
    @ModelAttribute("usuaris")
    public List<Usuari> inicialitzar() {

        List<Usuari> usuaris = new ArrayList<>();
        for ( Usuari u : repositori.findAll()) {
            usuaris.add(u);
        }
        return usuaris;
    }

    /***
     * Aquest mètode farà la seva feina en el moment d'accedir mitjançant el mètode GET a la ruta /home
     * El mètode comproba si l'usuari ja té assignades les seves 3 ciutats favorites a MongoDB. En cas de no tenirles, mostra
     la vista "SELECCIO" on l'usuari haurà d'escollir le seves 3 ciutats favorites. En cas de tenirles guardades a MongoDB, mostra la vista
     "HOME" amb les 3 ciutats favorites que ha escollit l'usuari
     *
     * @param model -> Utilitzarem el model per passar un atribut per utilitzar a les vistes mitjançant Thymeleaf
     * @return -> En funció de la sentència If - Else, retornem una vista o una altre
     */
    @GetMapping("/home")
    String mostrarInici(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("principal : " + authentication.getPrincipal());

        String username = authentication.getPrincipal().toString()
                .substring(authentication.getPrincipal().toString().indexOf("username=") + 9);

        String email = authentication.getPrincipal().toString()
                .substring(authentication.getPrincipal().toString().indexOf("email=") + 6);

        System.out.println("username = " + username);
        System.out.println("email = " + email);

        if(repositori.findById(username).isPresent() && repositori.findById(username).get().getCiutats().size() > 0){
            System.out.println("Usuari trobat");
            ArrayList<String> ciutats = new ArrayList<String>();
            repositori.findById(username).get().getCiutats().forEach(ciutat ->ciutats.add(ciutat));
            System.out.println("ciutatsHOME = " +ciutats);
            model.addAttribute("ciutats", ciutats);
            return ("home");
        } else {
            System.out.println("Usuari no trobat");
            Usuari u = new Usuari(username, email);
            repositori.save(u);
            return("seleccio");
        }
    }

    /**
     * Métode per Afegir Ciutats
     * @param ciutat1 -> Agafem la primera ciutat que ha escollit l'usuari com a favorita
     * @param ciutat2 -> Agafem la segona ciutat que ha escollit l'usuari com a favorita
     * @param ciutat3 -> Agafem la tercera ciutat que ha escollit l'usuari com a favorita
     * Aquests paràmetres guarden les ciutats que l'usuari envia i les guarda al repositori.
     * @return -> Ens retorna el mètode home
    */
    @RequestMapping(value = "/afegirCiutats", method = RequestMethod.POST)
    public String afegirCiutats(
            @RequestParam() String ciutat1,
            @RequestParam() String ciutat2,
            @RequestParam() String ciutat3,
            Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getPrincipal().toString()
                .substring(authentication.getPrincipal().toString().indexOf("username=") + 9);

        System.out.println("username = " + username);
        System.out.println("ciutat1 = " + ciutat1);
        System.out.println("ciutat2 = " + ciutat2);
        System.out.println("ciutat3 = " + ciutat3);

        ArrayList<String> ciutats = new ArrayList<String>();
        ciutats.add(ciutat1);
        ciutats.add(ciutat2);
        ciutats.add(ciutat3);
        System.out.println("ciutats = " + ciutats);

        repositori.findById(username).ifPresent(u -> {
            u.setCiutats(ciutats);
            repositori.save(u);
        });

        return("redirect:/home");
    }

    /**
     * Ens permet veure el temps de determinada ciutat
     * @param ciudad -> Agafem la ciutat que vol
     * @param model -> Utilitzarem el model per passar un atribut per utilitzar a les vistes mitjançant Thymeleaf
     * @return Ens retorna vista del temps
     */
    @PostMapping("/veureTemps")
    public String veureTemps(
            @RequestParam() String ciudad,
            Model model) {
        model.addAttribute("ciudad", ciudad);
        return("temps");
    }

    /**
     * Metode per a actualitzar les ciutats de l'usuari
     * @return Ens retorna el metode home
     */
    @GetMapping("/actualizarCiudades")
    public String actualizarCiudades(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getPrincipal().toString()
                .substring(authentication.getPrincipal().toString().indexOf("username=") + 9);

        repositori.deleteById(username);
        return("redirect:/home");
    }

    /***
     * Mètode per esborrar les dades d'un usuari guardades a MongoDB, esborra el registre de l'usuari
     *
     * @return -> Ens redirecciona a l'arrel del projecte
     */
    @GetMapping("/borrarUsuario")
    public String borrarUsuario(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getPrincipal().toString()
                .substring(authentication.getPrincipal().toString().indexOf("username=") + 9);

        repositori.deleteById(username);
        return("redirect:/");
    }
}




