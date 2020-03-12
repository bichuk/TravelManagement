package com.travel.usermicroservice.seeders;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.travel.usermicroservice.model.User;
import com.travel.usermicroservice.repository.UserRepository;

@Component
public class DatabaseSeeder {

	private UserRepository userRepository;
	protected Logger logger = Logger.getLogger(DatabaseSeeder.class.getName());
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public DatabaseSeeder(UserRepository userRepository, JdbcTemplate jdbcTemplate) {
		this.userRepository = userRepository;
		this.jdbcTemplate = jdbcTemplate;		
	}
	
	@EventListener
    public void seed(ContextRefreshedEvent event) {
        seedUsersTable();        
    }
	
	private void seedUsersTable() {
        String sql = "SELECT user_name, email FROM user U WHERE U.user_name = \"admin\" OR " +
                "U.email = \"admin@gmail.com\" LIMIT 1";
        
        List<User> u = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(u == null || u.size() <= 0) {
             User user = new User();
             user.setAddress("Admin Address");
             user.setAge(45);
             user.setEmail("admin@gmail.com");
             user.setFirstName("admin");
             user.setLastName("admin");
             user.setPassword("password");
             user.setPhone("123456");
             user.setType("admin");
             user.setUserName("admin");
             userRepository.save(user);
             logger.info("Users Seeded");
        } else {
            logger.info("Users Seeding Not Required");
        }
    }

}
