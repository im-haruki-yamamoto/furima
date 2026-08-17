package in.tech_camp.furima.service;

import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.tech_camp.furima.dto.UserDto;
import in.tech_camp.furima.entity.User;
import in.tech_camp.furima.mapper.UserMapper;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(UserDto dto) {
        User user = new User();
        user.setNickname(dto.getNickname());
        user.setEmail(dto.getEmail());

        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encodedPassword);
        
        user.setLastName(dto.getLastName());
        user.setFirstName(dto.getFirstName());
        user.setLastNameKana(dto.getLastNameKana());
        user.setFirstNameKana(dto.getFirstNameKana());
        LocalDate birthDate = dto.getBirthDate();
        user.setBirthday(birthDate);

        userMapper.insert(user);
    }
}