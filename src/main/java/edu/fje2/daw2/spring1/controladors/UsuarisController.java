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
 * Controlador de clients
 * Verifica el funcionament de curl
 * @author sergi.grau@fje.edu
 * @version 1.0 21.3.19
 * @version 2.0 25.3.21
 */

@Controller
@SessionAttributes("usuaris")
public class UsuarisController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UsuariRepositori repositori;

    @ModelAttribute("usuaris")
    public List<Usuari> inicialitzar() {

        List<Usuari> usuaris = new ArrayList<>();
        for ( Usuari u : repositori.findAll()) {
            usuaris.add(u);
        }
        return usuaris;
    }

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

    @GetMapping ("/veureTemps")
    public String veureTemps(
            @RequestParam() String ciudad,
            Model model) {
        model.addAttribute("ciudad", ciudad);
        return("temps");
    }

    @GetMapping("/actualizarCiudades")
    public String actualizarCiudades(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getPrincipal().toString()
                .substring(authentication.getPrincipal().toString().indexOf("username=") + 9);

        repositori.deleteById(username);
        return("redirect:/home");
    }


}




