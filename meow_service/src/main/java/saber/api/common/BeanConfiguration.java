package saber.api.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import saber.api.tasks.TasksManager;

@Configuration
@PropertySource("application.properties")
public class BeanConfiguration
{
    @Autowired
    private Environment env;

    @Bean
    public TasksManager tasksManager()
    {
        return new TasksManager(env);
    }

}
