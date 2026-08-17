package in.tech_camp.furima.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import in.tech_camp.furima.dto.UserDto;
import in.tech_camp.furima.form.RegisterForm;
import in.tech_camp.furima.service.UserService;

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
        if (form.getPassword() != null && !form.getPassword().equals(form.getPasswordConfirmation())) {
            bindingResult.rejectValue("passwordConfirmation", "error.passwordConfirmation", "パスワードと一致しません");
        }

        if (bindingResult.hasErrors()) {
            // パスワードと確認用パスワードはクリア
            List<String> errorMessages = bindingResult.getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());
    model.addAttribute("errorMessages", errorMessages);
    
    form.setPassword("");
    form.setPasswordConfirmation("");
    return "users/sign_up";
        }

        // FormをDTOに変換してServiceに渡す
        UserDto dto = form.toDto();
        userService.register(dto);
        return "redirect:/";
    }

    @GetMapping("/sign_in")
public String showSignInForm() {
    return "users/sign_in";
}
}
