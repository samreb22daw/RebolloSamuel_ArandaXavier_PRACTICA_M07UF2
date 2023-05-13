package edu.fje2.daw2.spring1.repositoris;

import edu.fje2.daw2.spring1.model.Usuari;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface UsuariRepositori extends MongoRepository<Usuari, String> {

    Usuari findByUsername(String username);
    List<Usuari> findByEmail(String email);
    List<Usuari> findByCiutats(String ciutat);
}
