package in.tech_camp.furima.custom_user;

import in.tech_camp.furima.entity.UserEntity;
import in.tech_camp.furima.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetails implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // UserRepositoryを使って、入力されたメールアドレスからユーザーを検索
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            throw new UsernameNotFoundException("ユーザーが見つかりません");
        }

        // 見つかったユーザー情報をSpring Securityが理解できる形式(UserDetails)に変換して返す
        return User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword()) // 暗号化されたパスワードを渡す
                .roles("USER")
                .build();
    }
}