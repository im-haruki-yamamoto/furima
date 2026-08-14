package in.tech_camp.furima.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.furima.entity.ItemEntity;

@Mapper
public interface ItemRepository {

  // 商品1件を取得
  @Select("""
          SELECT
              i.id AS id,
              i.name AS name,
              i.description AS description,
              i.category AS category,
              i.condition AS condition,
              i.delivery_fee AS deliveryFee,
              i.prefecture AS prefecture,
              i.until_delivery AS untilDelivery,
              i.price AS price,
              i.img AS img,
              u.id AS "user.id",
              u.nickname AS "user.nickname"
          FROM items i
          JOIN users u ON i.user_id = u.id
          WHERE i.id = #{id}
      """)
  ItemEntity findById(Long id);

  // 商品削除
  @Delete("DELETE FROM items WHERE id = #{id}")
  void deleteById(Long id);

}
