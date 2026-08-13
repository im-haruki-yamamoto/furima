package in.tech_camp.furima.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.furima.entity.Item;

// ダミークラス
@Mapper
public interface ItemMapper {
    @Select("SELECT * FROM items WHERE id = #{id}")
    Item findById(Long id);
}