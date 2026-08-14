package in.tech_camp.furima.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.tech_camp.furima.custom_user.CustomUserDetails;
import in.tech_camp.furima.entity.UserEntity;
import in.tech_camp.furima.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FurimaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            throw new UsernameNotFoundException("該当するユーザーが存在しません: " + email);
        }

        // CustomUserDetails に UserEntity を渡して Spring Security にログイン情報をセット
        return new CustomUserDetails(userEntity);
    }
}