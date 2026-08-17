package in.tech_camp.furima.service;

import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.tech_camp.furima.dto.UserDto;
import in.tech_camp.furima.entity.UserEntity;
import in.tech_camp.furima.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(UserDto dto) {
        UserEntity user = new UserEntity();
        user.setNickname(dto.getNickname());
        user.setEmail(dto.getEmail());

        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encodedPassword);
        
        user.setLastName(dto.getLastName());
        user.setFirstName(dto.getFirstName());
        user.setLastNameKana(dto.getLastNameKana());
        user.setFirstNameKana(dto.getFirstNameKana());
        LocalDate birthDate = dto.getBirthDate();
        user.setBirthDate(birthDate);

        userRepository.insert(user);
    }
}