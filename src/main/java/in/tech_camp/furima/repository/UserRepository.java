package in.tech_camp.furima.repository;

import in.tech_camp.furima.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRepository {
    // ユーザー情報をDBに保存するメソッド
    @Insert("INSERT INTO users (nickname, email, password, last_name, first_name, last_name_kana, first_name_kana, birth_date) " +
            "VALUES (#{nickname}, #{email}, #{password}, #{lastName}, #{firstName}, #{lastNameKana}, #{firstNameKana}, #{birthDate})")
    void insert(UserEntity user);
    // メールアドレスでユーザー情報を取得するメソッド
    @Select("SELECT * FROM users WHERE email = #{email}")
    UserEntity findByEmail(String email);
}