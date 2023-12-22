package org.hzz.pathfinder.config;

import org.hzz.pathfinder.api.GraphTraversalService;
import org.hzz.pathfinder.internal.GraphDAO;
import org.hzz.pathfinder.internal.GraphDAOStub;
import org.hzz.pathfinder.internal.GraphTraversalServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PathfinderApplicationContext {

    private GraphDAO graphDAO() {
        return new GraphDAOStub();
    }

    @Bean
    public GraphTraversalService graphTraversalService() {
        return new GraphTraversalServiceImpl(graphDAO());
    }
}
