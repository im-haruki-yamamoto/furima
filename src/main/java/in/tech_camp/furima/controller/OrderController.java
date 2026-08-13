package in.tech_camp.furima.controller;

import org.springframework.beans.factory.annotation.Value;
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

import in.tech_camp.furima.custom_user.CustomUserDetail;
import in.tech_camp.furima.dto.OrderForm;
import in.tech_camp.furima.entity.Item;
import in.tech_camp.furima.entity.Prefecture;
import in.tech_camp.furima.mapper.ItemMapper;
import in.tech_camp.furima.service.OrderService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/items/{itemId}/orders")
@RequiredArgsConstructor
public class OrderController {

    private final ItemMapper itemMapper;
    private final OrderService orderService;

    @Value("${payjp.public-key:pk_test_dummy}")
    private String payjpPublicKey;

    @GetMapping
    public String index(@PathVariable("itemId") Long itemId,
                        @AuthenticationPrincipal CustomUserDetail currentUser,
                        Model model,
                        @ModelAttribute("orderForm") OrderForm orderForm) {

        Item item = itemMapper.findById(itemId);
        if (item == null) {
            return "redirect:/";
        }

        boolean isSold = orderService.isSold(itemId);
        Long currentUserId = currentUser.getUser().getId();

        // ガード制御: 自身が出品した商品 OR 売却済みの商品 -> トップページへリダイレクト
        if (item.getUserId().equals(currentUserId) || isSold) {
            return "redirect:/";
        }

        model.addAttribute("item", item);
        model.addAttribute("prefectures", Prefecture.values());
        model.addAttribute("payjpPublicKey", payjpPublicKey);

        return "orders/index";
    }

    @PostMapping
    public String create(@PathVariable("itemId") Long itemId,
                         @AuthenticationPrincipal CustomUserDetail currentUser,
                         @Validated @ModelAttribute("orderForm") OrderForm orderForm,
                         BindingResult bindingResult,
                         Model model) {

        Item item = itemMapper.findById(itemId);
        if (item == null) {
            return "redirect:/";
        }

        boolean isSold = orderService.isSold(itemId);
        Long currentUserId = currentUser.getUser().getId();

        if (item.getUserId().equals(currentUserId) || isSold) {
            return "redirect:/";
        }

        // バリデーションエラー時（フォームの入力項目は自動で保持される）
        if (bindingResult.hasErrors()) {
            model.addAttribute("item", item);
            model.addAttribute("prefectures", Prefecture.values());
            model.addAttribute("payjpPublicKey", payjpPublicKey);
            return "orders/index";
        }

        try {
            orderService.createOrder(orderForm, itemId, currentUserId, item.getPrice());
        } catch (Exception e) {
            model.addAttribute("item", item);
            model.addAttribute("prefectures", Prefecture.values());
            model.addAttribute("payjpPublicKey", payjpPublicKey);
            model.addAttribute("paymentError", "決済処理に失敗しました。カード情報をご確認ください。");
            return "orders/index";
        }

        return "redirect:/";
    }
}