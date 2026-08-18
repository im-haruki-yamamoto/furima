package in.tech_camp.furima.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Order {
    private Long id;
    private Long userId;
    private Long itemId;
    private LocalDateTime createdAt;
}