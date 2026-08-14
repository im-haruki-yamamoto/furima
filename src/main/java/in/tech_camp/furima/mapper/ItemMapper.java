package in.tech_camp.furima.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.furima.entity.ItemEntity;

@Mapper
public interface ItemMapper {

  // 商品削除
  @Select("""
          SELECT * FROM items WHERE id = #{id}
      """)
  ItemEntity findById(Long id);

  @Delete("DELETE FROM items WHERE id = #{id}")
  void deleteById(Long id);

}
