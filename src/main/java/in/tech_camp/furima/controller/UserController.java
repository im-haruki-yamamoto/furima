package in.tech_camp.furima.controller;

import in.tech_camp.furima.form.RegisterForm;
import in.tech_camp.furima.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign_up")
    public String showSignUpForm(Model model) {
        model.addAttribute("userForm", new RegisterForm());
        return "users/sign_up";
    }

    @PostMapping("/sign_up")
    public String registerUser(@Validated @ModelAttribute("userForm") RegisterForm form,
                               BindingResult bindingResult,
                               Model model) {
        // パスワード一致チェック
        if (!form.getPassword().equals(form.getPasswordConfirmation())) {
            bindingResult.rejectValue("passwordConfirmation", "error.passwordConfirmation", "パスワードと一致しません");
        }

        if (bindingResult.hasErrors()) {
            // パスワードと確認用パスワードはクリア
            form.setPassword("");
            form.setPasswordConfirmation("");
            return "users/sign_up";
        }

        // 登録処理 (UserService経由で暗号化して保存)
        userService.register(form);
        return "redirect:/";
    }

    @GetMapping("/sign_in")
public String showSignInForm() {
    return "users/sign_in";
}
}
