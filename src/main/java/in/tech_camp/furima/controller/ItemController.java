package in.tech_camp.furima.controller;

import java.io.IOException;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.tech_camp.furima.enums.Category;
import in.tech_camp.furima.enums.Condition;
import in.tech_camp.furima.enums.DeliveryFeeType;
import in.tech_camp.furima.enums.PrefectureType;
import in.tech_camp.furima.enums.UntilDelivery;
import in.tech_camp.furima.form.ItemForm;
import in.tech_camp.furima.security.CustomUserDetails;
import in.tech_camp.furima.service.ItemService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ItemController {

  private final ItemService itemService;

    // 商品一覧表示
  @GetMapping({ "/", "", "items" })
  public String showAllProduct(Model model) {
    model.addAttribute("items", itemService.allItem());
    return "items/index";
  }

    // 商品出品機能
  @GetMapping("/items/new")
  public String showCreationForm(Model model) {
    model.addAttribute("itemForm", new ItemForm());

    addEnumAttributesToModel(model);

    return "items/new";
  }

  @PostMapping("/items")
  public String createItem(@ModelAttribute @Validated ItemForm itemForm,
      BindingResult bindingResult,
      Model model,
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    if (itemForm.getImg() == null || itemForm.getImg().isEmpty()) {
      bindingResult.rejectValue("img", "error.itemForm", "出品画像を選択してください");
    }

    if (bindingResult.hasErrors()) {
      addEnumAttributesToModel(model);
      return "items/new";
    }

    // Long loginUserId = userDetails.getUser().getId();
    Long loginUserId = 1L;

    try {
      itemService.saveItem(itemForm, loginUserId);
    } catch (IOException e) {
      e.printStackTrace();

      bindingResult.rejectValue("img", "error.itemForm", "画像の保存中にエラーが発生しました");
      addEnumAttributesToModel(model);
      return "items/new";
    }
    return "redirect:/";
  }

  private void addEnumAttributesToModel(Model model) {
    model.addAttribute("categories", Category.values());
    model.addAttribute("conditions", Condition.values());
    model.addAttribute("deliveryFees", DeliveryFeeType.values());
    model.addAttribute("prefectures", PrefectureType.values());
    model.addAttribute("untilDeliveries", UntilDelivery.values());
  }

}
