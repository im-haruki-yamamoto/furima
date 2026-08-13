package in.tech_camp.furima.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import in.tech_camp.furima.dto.ItemEditDto;
import in.tech_camp.furima.service.ItemService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

  private final ItemService itemService;

  @GetMapping("/{id}/edit")
  public String showEditForm(
      @PathVariable Long id,
      @AuthenticationPrincipal CustomUserDetails userDetails, Model model) {

    ItemEditDto itemEditDto = itemService.getItemForEdit(id, userDetails.getId());
    model.addAttribute("itemForm", itemEditDto);
    return "items/edit";
  }

  // 編集実行
  @PostMapping("/{id}/update")
  public String updateItem(
      @PathVariable Long id,
      @ModelAttribute("itemForm") ItemEditDto itemEditDto,
      @RequestParam(value = "image", required = false) MultipartFile image,
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    itemService.updateItem(id, itemEditDto, image, userDetails.getId());
    return "redirect:/items/" + id;
  }
}