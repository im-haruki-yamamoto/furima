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

    // DBから取得したユーザーエンティティ
    private final User user;

    public CustomUserDetail(User user) {
        this.user = user;
    }

    // ユーザーIDの取得
    public Long getId() {
        return user.getId();
    }

    // ユーザー名の取得
    public String getName() {
        return user.getName();
    }

    // ユーザーの権限（ロール）設定。今回はシンプルにするため空リストを返します
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    /** パスワードの取得（Spring Securityが入力されたパスワードと照合するために使用） */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * ログイン識別子（ユーザー名）の取得
     * 今回は email をログインIDとして使うため、user.getEmail() を返します
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /* 以下、アカウントの状態設定（今回はすべて有効「true」として扱います） */

    @Override
    public boolean isAccountNonExpired() {
        return true; // アカウントの期限切れがないか
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // アカウントがロックされていないか
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // パスワードの期限切れがないか
    }

    @Override
    public boolean isEnabled() {
        return true; // アカウントが有効か
    }
}