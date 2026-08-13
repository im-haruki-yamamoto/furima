package in.tech_camp.furima.service;

import in.tech_camp.furima.entity.UserEntity;
import in.tech_camp.furima.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FurimaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public FurimaUserDetailsService(UserRepository userRepository) {
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
                .roles("USER") // 権限（今回はとりあえずUSERとしておきます）
                .build();
    }
}