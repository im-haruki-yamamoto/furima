package in.tech_camp.furima.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.furima.entity.ItemEntity;

@Mapper
public interface ItemMapper {

  // 商品削除
 @Select("SELECT * FROM items WHERE id = #{itemId}")
  @Results({
    @Result(property = "deliveryFee", column = "delivery_fee"),
    @Result(property = "untilDelivery", column = "until_delivery"),
    @Result(property = "user", column = "user_id",
            one = @One(select = "in.tech_camp.furima.mapper.UserMapper.findById"))
  })
  ItemEntity findById(@Param("itemId") Long itemId);

  @Delete("DELETE FROM items WHERE id = #{itemId}")
  void deleteById(@Param("itemId") Long id);

}
