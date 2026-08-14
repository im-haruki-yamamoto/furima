package in.tech_camp.furima.service;

import in.tech_camp.furima.FurimaApplication;
import in.tech_camp.furima.dto.UserDto;
import in.tech_camp.furima.entity.UserEntity;
import in.tech_camp.furima.form.RegisterForm;
import in.tech_camp.furima.repository.UserRepository;

import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final FurimaApplication furimaApplication;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // コンストラクタを使ってRepositoryとPasswordEncoderを読み込む
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, FurimaApplication furimaApplication) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.furimaApplication = furimaApplication;
    }

    @Transactional
    public void register(UserDto dto) {
        UserEntity user = new UserEntity();
        user.setNickname(dto.getNickname());
        user.setEmail(dto.getEmail());

        // パスワードをそのまま保存せず、暗号化してセットする
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encodedPassword);
        
        user.setLastName(dto.getLastName());
        user.setFirstName(dto.getFirstName());
        user.setLastNameKana(dto.getLastNameKana());
        user.setFirstNameKana(dto.getFirstNameKana());
        LocalDate birthDate = dto.getBirthDate();
        user.setBirthDate(birthDate);

        // DBに保存
        userRepository.insert(user);
    }
}