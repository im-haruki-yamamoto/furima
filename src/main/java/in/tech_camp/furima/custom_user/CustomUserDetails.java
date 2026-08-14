package in.tech_camp.furima.custom_user;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import in.tech_camp.furima.entity.UserEntity;
import lombok.Getter;

@Getter
public class CustomUserDetails extends User {

    private final Long id;
    private final String nickname;

    public CustomUserDetails(UserEntity userEntity) {
        // メールアドレス、パスワード、権限を親クラスに渡す
        super(
            userEntity.getEmail(),
            userEntity.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
        // IDとニックネームを保持する
        this.id = userEntity.getId();
        this.nickname = userEntity.getNickname();
    }
}