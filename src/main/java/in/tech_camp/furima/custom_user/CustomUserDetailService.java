package in.tech_camp.furima.custom_user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.tech_camp.furima.entity.User;
import in.tech_camp.furima.mapper.UserMapper;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserMapper userRepository;

    public CustomUserDetailService(UserMapper userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("ユーザーが見つかりません");
        }

        // 自作の CustomUserDetail に User を包んで返します
        return new CustomUserDetail(user);
    }
}