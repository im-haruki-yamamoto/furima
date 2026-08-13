package in.tech_camp.furima.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.tech_camp.furima.dto.OrderForm;
import in.tech_camp.furima.entity.Address;
import in.tech_camp.furima.entity.Order;
import in.tech_camp.furima.mapper.AddressMapper;
import in.tech_camp.furima.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final AddressMapper addressMapper;
    private final PayjpService payjpService;

    public boolean isSold(Long itemId) {
        return orderMapper.existsByItemId(itemId);
    }

    @Transactional
    public void createOrder(OrderForm orderForm, Long itemId, Long userId, int price) {
        // PAY.JP 決済処理
        payjpService.charge(price, orderForm.getToken());

        // Order 保存
        Order order = new Order();
        order.setUserId(userId);
        order.setItemId(itemId);
        orderMapper.insert(order);

        // Address 保存
        Address address = new Address();
        address.setPostalCode(orderForm.getPostalCode());
        address.setPrefectureId(orderForm.getPrefectureId());
        address.setCity(orderForm.getCity());
        address.setAddresses(orderForm.getAddresses());
        address.setBuilding(orderForm.getBuilding());
        address.setPhoneNumber(orderForm.getPhoneNumber());
        address.setOrderId(order.getId());

        addressMapper.insert(address);
    }
}