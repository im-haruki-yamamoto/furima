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

import in.tech_camp.furima.custom_user.CustomUserDetail;
import in.tech_camp.furima.dto.ItemResponseDto;
import in.tech_camp.furima.enums.Category;
import in.tech_camp.furima.enums.Condition;
import in.tech_camp.furima.enums.DeliveryFeeType;
import in.tech_camp.furima.enums.PrefectureType;
import in.tech_camp.furima.enums.UntilDelivery;
import in.tech_camp.furima.exception.ForbiddenException;
import in.tech_camp.furima.exception.ResourceNotFoundException;
import in.tech_camp.furima.form.ItemEditForm;
import in.tech_camp.furima.form.ItemForm;
import in.tech_camp.furima.service.ItemService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ItemController {

  private final ItemService itemService;

  // 商品一覧表示 (トップページ)
  @GetMapping({ "/", "", "/items" })
  public String showAllItem(Model model) {
    model.addAttribute("items", itemService.allItem());
    return "items/index";
  }

  // 商品出品画面表示
  @GetMapping("/items/new")
  public String showCreationForm(Model model) {
    model.addAttribute("itemForm", new ItemForm());
    addEnumAttributesToModel(model);
    return "items/new";
  }

  // 商品出品実行
  @PostMapping("/items")
  public String createItem(
      @ModelAttribute @Validated ItemForm itemForm,
      BindingResult bindingResult,
      Model model,
      @AuthenticationPrincipal CustomUserDetail userDetails) {

    if (userDetails == null) {
      return "redirect:/users/sign_in";
    }

    if (itemForm.getImg() == null || itemForm.getImg().isEmpty()) {
      bindingResult.rejectValue("img", "error.itemForm", "出品画像を選択してください");
    }

    if (bindingResult.hasErrors()) {
      addEnumAttributesToModel(model);
      return "items/new";
    }

    try {
      itemService.saveItem(itemForm, userDetails.getId());
    } catch (IOException e) {
      e.printStackTrace();
      bindingResult.rejectValue("img", "error.itemForm", "画像の保存中にエラーが発生しました");
      addEnumAttributesToModel(model);
      return "items/new";
    }
    return "redirect:/";
  }

  // 商品詳細画面表示
  @GetMapping("/items/{itemId}")
  public String showItem(
      @PathVariable("itemId") Long itemId,
      @AuthenticationPrincipal CustomUserDetail userDetails,
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
  @GetMapping("/items/{itemId}/edit")
  public String showEditForm(
      @PathVariable("itemId") Long itemId,
      @AuthenticationPrincipal CustomUserDetail userDetails,
      Model model) {

    if (userDetails == null) {
      return "redirect:/users/sign_in";
    }

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
  @PostMapping("/items/{itemId}/update")
  public String updateItem(
      @PathVariable("itemId") Long itemId,
      @ModelAttribute("itemForm") @Validated ItemEditForm itemEditForm,
      BindingResult bindingResult,
      Model model,
      @AuthenticationPrincipal CustomUserDetail userDetails) {

    if (userDetails == null) {
      return "redirect:/users/sign_in";
    }

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
  @PostMapping("/items/{itemId}/delete")
  public String deleteItem(
      @PathVariable("itemId") Long itemId,
      @AuthenticationPrincipal CustomUserDetail userDetails) {

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

  // Enum属性のモデル設定（新規登録・編集画面の双方に対応）
  private void addEnumAttributesToModel(Model model) {
    model.addAttribute("categories", Category.values());
    model.addAttribute("conditions", Condition.values());
    model.addAttribute("deliveryFees", DeliveryFeeType.values());
    model.addAttribute("deliveryFeeTypes", DeliveryFeeType.values());
    model.addAttribute("prefectures", PrefectureType.values());
    model.addAttribute("prefectureTypes", PrefectureType.values());
    model.addAttribute("untilDeliveries", UntilDelivery.values());
  }
}