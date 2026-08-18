package in.tech_camp.furima.controller;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import in.tech_camp.furima.custom_user.CustomUserDetail;
import in.tech_camp.furima.entity.Item;
import in.tech_camp.furima.enums.PrefectureType;
import in.tech_camp.furima.form.OrderForm;
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
                        @AuthenticationPrincipal CustomUserDetail customUserDetail,
                        Model model,
                        @ModelAttribute("orderForm") OrderForm orderForm) {

        // 未ログインの場合はログイン画面へ
        if (customUserDetail == null) {
            return "redirect:/users/sign_in";
        }

        Item item = itemMapper.findById(itemId);
        if (item == null) {
            return "redirect:/";
        }

        Long currentUserId = customUserDetail.getId();
        Long itemSellerId = item.getUserId();
        boolean isSold = orderService.isSold(itemId);

        // 自身が出品した商品 または 売却済みの商品 -> トップページへリダイレクト
        if (isSold || (itemSellerId != null && itemSellerId.equals(currentUserId))) {
            return "redirect:/";
        }

        model.addAttribute("userId", currentUserId);
        model.addAttribute("item", item);
        model.addAttribute("prefectures", PrefectureType.values());
        model.addAttribute("payjpPublicKey", payjpPublicKey);

        return "orders/index";
    }

    @PostMapping
    public String create(@PathVariable("itemId") Long itemId,
                         @AuthenticationPrincipal CustomUserDetail customUserDetail,
                         @Validated @ModelAttribute("orderForm") OrderForm orderForm,
                         BindingResult bindingResult,
                         Model model) {

        if (customUserDetail == null) {
            return "redirect:/users/sign_in";
        }

        Item item = itemMapper.findById(itemId);
        if (item == null) {
            return "redirect:/";
        }

        Long currentUserId = customUserDetail.getId();
        Long itemSellerId = item.getUserId();
        boolean isSold = orderService.isSold(itemId);

        if (isSold || (itemSellerId != null && itemSellerId.equals(currentUserId))) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            List<String> fieldOrder = Arrays.stream(OrderForm.class.getDeclaredFields())
                    .map(Field::getName)
                    .toList();

            List<FieldError> sortedErrors = bindingResult.getFieldErrors().stream()
                    .sorted(Comparator.comparingInt(error -> fieldOrder.indexOf(error.getField())))
                    .toList();

            model.addAttribute("errors", sortedErrors);
            model.addAttribute("item", item);
            model.addAttribute("prefectures", PrefectureType.values());
            model.addAttribute("payjpPublicKey", payjpPublicKey);
            return "orders/index";
        }

        try {
            orderService.createOrder(orderForm, itemId, currentUserId, item.getPrice());
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("item", item);
            model.addAttribute("prefectures", PrefectureType.values());
            model.addAttribute("payjpPublicKey", payjpPublicKey);
            model.addAttribute("paymentError", "決済処理に失敗しました。カード情報をご確認ください。");
            return "orders/index";
        }

        return "redirect:/";
    }
}