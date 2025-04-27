package au.com.demo.transactions.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDto {

    private final String email;
    private final String password;
    private final List<String> roles;
}
