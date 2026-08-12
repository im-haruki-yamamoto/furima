package in.tech_camp.furima.repository;

import org.apache.ibatis.annotations.Delete;

public interface ItemRepository {
// 商品削除
  @Delete("DELETE FROM items WHERE id = #{id}")
        void deleteById(Long id);

}


