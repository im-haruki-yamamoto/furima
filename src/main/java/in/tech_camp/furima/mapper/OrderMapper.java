package in.tech_camp.furima.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.furima.entity.Order;

@Mapper
public interface OrderMapper {

    @Insert("INSERT INTO orders (user_id, item_id, created_at) VALUES (#{userId}, #{itemId}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Order order);

    @Select("SELECT COUNT(*) > 0 FROM orders WHERE item_id = #{itemId}")
    boolean existsByItemId(Long itemId);
}