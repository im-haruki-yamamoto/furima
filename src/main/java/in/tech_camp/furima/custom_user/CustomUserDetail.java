package in.tech_camp.furima.custom_user;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import in.tech_camp.furima.entity.User;
import lombok.Getter;

/**
 * Spring Security がログインユーザーの情報を保持するためのクラス。
 * DBの User を包み込んで、Spring Securityの規格（UserDetails）に適合させます。
 */
@Getter
public class CustomUserDetail implements UserDetails {

    private final User user;

    public CustomUserDetail(User user) {
        this.user = user;
    }

    // ユーザーIDの取得
    public Long getId() {
        return user.getId();
    }


    // ニックネームの取得
    public String getNickname() {
        return user.getNickname();
    }

    // 互換性のための名前取得
    public String getName() {
        return user.getNickname();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}