package in.tech_camp.furima.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.furima.dto.ItemQueryResult;
import in.tech_camp.furima.entity.ItemEntity;

@Mapper
public interface ItemRepository {

    // 商品出品機能
  @Insert("INSERT INTO items (user_id, name, description, category, condition, delivery_fee, prefecture, until_delivery, price, img) VALUES (#{userId}, #{name}, #{description}, #{category}, #{condition}, #{deliveryFee}, #{prefecture}, #{untilDelivery}, #{price}, #{img})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(ItemEntity product);

  // 商品一覧表示機能
  @Select("""
      SELECT i.id,i.img,i.name,i.price,i.delivery_fee,b.item_id FROM items i
      LEFT JOIN buys b
      ON i.id = b.item_id
      ORDER BY i.id DESC
      """)
  List<ItemQueryResult> findAll();

}
