package Projekt.PCCarovnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PolnenjeBazeService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void napolniKomponente() {
        String storedProcedureCall = "CALL napolniKomponente()";
        jdbcTemplate.execute(storedProcedureCall);
    }

}