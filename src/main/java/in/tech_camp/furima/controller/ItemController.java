package in.tech_camp.furima.controller;

import java.util.Objects;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import in.tech_camp.furima.dto.ItemResponseDto;
import in.tech_camp.furima.security.CustomUserDetails;
import in.tech_camp.furima.service.ItemService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items/{itemId}")
public String showItem(@PathVariable("itemId") Long itemId, 
                       @AuthenticationPrincipal CustomUserDetails userDetails, 
                       Model model) {
    try {
        ItemResponseDto item = itemService.findById(itemId);
        
        // ログインユーザーが出品者本人かどうかを判定
        boolean isOwner = false;
        if (userDetails != null && item.getUser() != null) {
            isOwner = Objects.equals(item.getUser().getId(), userDetails.getId());
        }

        model.addAttribute("item", item);
        model.addAttribute("isOwner", isOwner); 
        
        return "items/show";
    } catch (Exception e) {
        System.out.println("エラー：" + e.getMessage());
        return "redirect:/";
    }
}

    @PostMapping("/items/{itemId}/delete")
    public String deleteItem(@PathVariable("itemId") Long itemId,
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null) {
           return "redirect:/users/sign_in";
        }
        try {
            itemService.deleteItem(itemId, userDetails.getId());
            return "redirect:/";
        } catch (Exception e) {
            System.out.println("エラー：" + e.getMessage());
            return "redirect:/";
        }
    }
}