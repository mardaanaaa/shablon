package kz.spring.parfume.controllers;

import kz.spring.parfume.entities.Product;
import kz.spring.parfume.entities.User;
import kz.spring.parfume.services.BrandService;
import kz.spring.parfume.services.ProductService;
import kz.spring.parfume.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
    private final ProductService productService;
    private final BrandService brandService;

    @GetMapping("/")
    public String index(Model model){
        List<Product> allProducts = productService.findAll();
        List<Product> limitedProducts = allProducts.size() > 3 ? allProducts.subList(0, 3) : allProducts;
        model.addAttribute("products", limitedProducts);
        model.addAttribute("brands", brandService.findAll());
        return "index";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }

    @GetMapping("/contacts")
    public String contact(){
        return "contacts";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/sign-in")
    public String signIn(){
        return "auth/login";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/sign-up")
    public String signUp(){
        return "auth/register";
    }

    @PostMapping("/to-sign-up")
    public String toSignUp(@RequestParam(name = "user_firstname") String firstname,
                           @RequestParam(name = "user_lastname") String lastname,
                           @RequestParam(name = "user_username") String username,
                           @RequestParam(name = "user_email") String email,
                           @RequestParam(name = "user_password") String password){
        try {
            User user = new User();
            user.setFirstName(firstname);
            user.setLastName(lastname);
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setBanned(false);

            userService.addUser(user);

            return "redirect:/sign-in?success";
        } catch (Exception e) {
            return "redirect:/sign-up?error";
        }
    }
}
