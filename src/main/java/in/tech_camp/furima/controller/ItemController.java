package in.tech_camp.furima.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.AllArgsConstructor;

import in.tech_camp.furima.service.ItemService;

@Controller
@AllArgsConstructor
public class ItemController {
  private final ItemService itemService;

  @PostMapping("/items/{itemId}/delete")
  public String deleteItem(@PathVariable("itemId") Long itemId,
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    if (userDetails == null) {
      return "redirect:/login";
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
