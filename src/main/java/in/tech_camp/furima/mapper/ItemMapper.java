package in.tech_camp.furima.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import in.tech_camp.furima.entity.ItemEntity;

@Mapper
public interface ItemMapper {



  // 商品編集
  @Select("SELECT * FROM items WHERE id = #{id}")
  ItemEntity findById(Long id);

  @Update("""
    UPDATE items SET
            name = #{name},
            description = #{description},
            category = #{category},
            condition = #{condition},
            delivery_fee = #{deliveryFee},
            prefecture = #{prefecture},
            until_delivery = #{untilDelivery},
            price = #{price},
            img = #{img}
            WHERE id = #{id}
        """)
  void update(ItemEntity item);
}
