package in.tech_camp.furima.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * ユーザー情報を保持するエンティティクラス
 */
@Data
public class User {
  private Long id;
  private String name;
  private String email;
  @JsonIgnore
  private String password;
}
