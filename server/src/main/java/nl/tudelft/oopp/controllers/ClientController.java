package nl.tudelft.oopp.controllers;
import java.util.List;

import nl.tudelft.oopp.entities.ClientEntity;
import nl.tudelft.oopp.repositories.ClientRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;

@EnableJpaRepositories("nl.tudelft.oopp.demo.repositories")

@Controller
public class ClientController {

    @Autowired
    private ClientRepository clientrep;

    @GetMapping("identifyMe/{credentials}")
    @ResponseBody
    public boolean authorize(@PathVariable String credentials) {
        String[] cred = credentials.split(":");
        String givenUsername = cred[0];
        String givenPassword = cred[1];

        List<ClientEntity> list = clientrep.findByUserNameAndUserPassword(givenUsername, givenPassword);
        if (list.size() == 0) {
            return false;
        }
        //we can do whatever we want with this user now
        ClientEntity person = list.get(0);
        return true;
    }

}
