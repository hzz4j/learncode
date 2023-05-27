package org.hzz;

import org.hzz.project.Client;
import org.hzz.project.ClientRepository;
import org.hzz.project.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void testAddClient() throws InterruptedException {
        Client client = new Client();
        client.setName("DDD-learning");
        client.addProject(new Project(1, "Java"));
        client.addProject(new Project(2, "GoLang"));
        clientRepository.save(client);
        clientRepository.findAll().forEach(System.out::println);
        // Client(id=1, name=DDD-learning, projects=[Project(id=1, name=Java), Project(id=2, name=GoLang)])
    }
}
