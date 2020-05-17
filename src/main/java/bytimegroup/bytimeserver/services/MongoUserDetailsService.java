package bytimegroup.bytimeserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import bytimegroup.bytimeserver.models.Users;
import bytimegroup.bytimeserver.repositories.UsersRepository;

import java.util.Arrays;
import java.util.List;

/**
 * implements UserDetailsService section denotes that 
 * this class will be creating a service for finding and authenticating users
 * Next, the @Component annotation indicates that this class can be injected into another file
 * https://www.journaldev.com/2394/java-dependency-injection-design-pattern-example-tutorial
 * https://www.journaldev.com/2410/spring-dependency-injection
 */
@Component 
public class MongoUserDetailsService implements UserDetailsService {
  @Autowired
  private UsersRepository repository;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users user = repository.findByUsername(username);
    if(user == null) {
      throw new UsernameNotFoundException("User not found");
    }
    List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));
    return new User(user.getUsername(), user.getPassword(), authorities);
  }
}