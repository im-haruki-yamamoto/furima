package in.tech_camp.furima.controller;

import java.io.IOException;
import java.util.Objects;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import in.tech_camp.furima.dto.ItemResponseDto;
import in.tech_camp.furima.enums.Category;
import in.tech_camp.furima.enums.Condition;
import in.tech_camp.furima.enums.DeliveryFeeType;
import in.tech_camp.furima.enums.PrefectureType;
import in.tech_camp.furima.enums.UntilDelivery;
import in.tech_camp.furima.exception.ForbiddenException;
import in.tech_camp.furima.exception.ResourceNotFoundException;
import in.tech_camp.furima.form.ItemEditForm;
import in.tech_camp.furima.security.CustomUserDetails;
import in.tech_camp.furima.service.ItemService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

  private final ItemService itemService;

  // 商品詳細画面表示
  @GetMapping("/{itemId}")
  public String showItem(
      @PathVariable("itemId") Long itemId,
      @AuthenticationPrincipal CustomUserDetails userDetails,
      Model model) {
    try {
      ItemResponseDto item = itemService.findById(itemId);

      boolean isOwner = false;
      if (userDetails != null && item.getUserId() != null) {
        isOwner = Objects.equals(item.getUserId(), userDetails.getId());
      }

      model.addAttribute("item", item);
      model.addAttribute("isOwner", isOwner);

      return "items/show";
    } catch (Exception e) {
      System.out.println("エラー：" + e.getMessage());
      return "redirect:/";
    }
  }

  // 商品編集画面表示
  @GetMapping("/{itemId}/edit")
  public String showEditForm(
      @PathVariable("itemId") Long itemId,
      @AuthenticationPrincipal CustomUserDetails userDetails,
      Model model) {

    try {
      ItemEditForm itemEditForm = itemService.getItemForEdit(itemId, userDetails.getId());
      model.addAttribute("itemForm", itemEditForm);

      addEnumAttributesToModel(model);

      return "items/edit";
    } catch (ResourceNotFoundException | ForbiddenException e) {
      return "redirect:/";
    }
  }

  // 商品編集実行
  @PostMapping("/{itemId}/update")
  public String updateItem(
      @PathVariable("itemId") Long itemId,
      @ModelAttribute("itemForm") @Validated ItemEditForm itemEditForm,
      BindingResult bindingResult,
      Model model,
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    if (bindingResult.hasErrors()) {
      addEnumAttributesToModel(model);
      return "items/edit";
    }

    try {
      itemService.updateItem(itemId, itemEditForm, userDetails.getId());
      return "redirect:/items/" + itemId;
    } catch (IOException e) {
      e.printStackTrace();
      bindingResult.rejectValue("img", "error.itemForm", "画像の保存中にエラーが発生しました");
      addEnumAttributesToModel(model);
      return "items/edit";
    } catch (ResourceNotFoundException | ForbiddenException e) {
      return "redirect:/";
    }
  }

  // 商品削除実行
  @PostMapping("/{itemId}/delete")
  public String deleteItem(
      @PathVariable("itemId") Long itemId,
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

  private void addEnumAttributesToModel(Model model) {
    model.addAttribute("categories", Category.values());
    model.addAttribute("conditions", Condition.values());
    model.addAttribute("deliveryFees", DeliveryFeeType.values());
    model.addAttribute("prefectures", PrefectureType.values());
    model.addAttribute("untilDeliveries", UntilDelivery.values());
  }
}