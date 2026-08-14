package in.tech_camp.furima.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.furima.entity.UserEntity;

@Mapper
public interface UserRepository {

    // メールアドレスからユーザー情報を取得
    @Select("SELECT * FROM users WHERE email = #{email}")
    UserEntity findByEmail(String email);
}