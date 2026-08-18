package in.tech_camp.furima.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import in.tech_camp.furima.entity.Address;

@Mapper
public interface AddressMapper {

    @Insert("INSERT INTO address (postal_code, prefecture_id, city, address, building, phone_number, order_id) " +
            "VALUES (#{postalCode}, #{prefectureId}, #{city}, #{address}, #{building}, #{phoneNumber}, #{orderId})")
    void insert(Address address);
}