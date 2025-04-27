package au.com.demo.transactions.service.impl;

import au.com.demo.transactions.persistence.entity.User;
import au.com.demo.transactions.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void loadUserByUsername_success() throws UsernameNotFoundException {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("test_pwd");
        user.setRoles(List.of("role_1", "role_2"));
        when(userRepository.findUserByEmail(anyString())).thenReturn(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername("test@gmail.com");
        assertEquals(userDetails.getUsername(), user.getEmail());
        assertEquals(userDetails.getPassword(), user.getPassword());
        assertEquals(userDetails.getAuthorities().size(), user.getRoles().size());
    }
}
