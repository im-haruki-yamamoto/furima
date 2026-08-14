package in.tech_camp.furima.controller;

import java.util.Objects;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import in.tech_camp.furima.custom_user.CustomUserDetail;
import in.tech_camp.furima.dto.ItemResponseDto;
import in.tech_camp.furima.service.ItemService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items/{itemId}")
    public String showItem(@PathVariable("itemId") Long itemId,
            @AuthenticationPrincipal CustomUserDetail userDetail,
            Model model) {
        try {
            ItemResponseDto item = itemService.findById(itemId);

            // ログインユーザーが出品者本人かどうかを判定
            boolean isOwner = false;
            if (userDetail != null && item.getUserId() != null) {
                isOwner = Objects.equals(item.getUserId(), userDetail.getId());
            }

            boolean isSold = itemService.isSold(itemId);

            model.addAttribute("item", item);
            model.addAttribute("isOwner", isOwner);
            model.addAttribute("isSold", isSold);

            return "items/show";
        } catch (Exception e) {
            System.out.println("エラー：" + e.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("/items/{itemId}/delete")
    public String deleteItem(@PathVariable("itemId") Long itemId,
            @AuthenticationPrincipal CustomUserDetail userDetail) {

        if (userDetail == null) {
            return "redirect:/users/sign_in";
        }
        try {
            itemService.deleteItem(itemId, userDetail.getId());
            return "redirect:/";
        } catch (Exception e) {
            System.out.println("エラー：" + e.getMessage());
            return "redirect:/";
        }
    }
}