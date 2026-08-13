package in.tech_camp.furima.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import in.tech_camp.furima.dto.ItemEditDto;
import in.tech_camp.furima.exception.ForbiddenException;
import in.tech_camp.furima.exception.ResourceNotFoundException;
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

    try {
      ItemEditDto itemEditDto = itemService.getItemForEdit(id, userDetails.getId());
      model.addAttribute("itemForm", itemEditDto);
      return "items/edit";
    } catch (ResourceNotFoundException | ForbiddenException e) {
      return "redirect:/";
    }
  }

  // 編集実行
  @PostMapping("/{id}/update")
  public String updateItem(
      @PathVariable Long id,
      @ModelAttribute("itemForm") ItemEditDto itemEditDto,
      BindingResult bindingResult,
      @RequestParam(value = "image", required = false) MultipartFile image,
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    if (bindingResult.hasErrors()) {
      return "items/edit";
    }

    try {
      itemService.updateItem(id, itemEditDto, image, userDetails.getId());
      return "redirect:/items/" + id;
    } catch (ResourceNotFoundException | ForbiddenException e) {
      return "redirect:/";
    }
  }
}