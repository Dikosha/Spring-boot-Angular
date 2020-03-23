package kz.iitu.swd.group34.FirstSpringBootIITU.controllers;

import kz.iitu.swd.group34.FirstSpringBootIITU.entities.Users;
import kz.iitu.swd.group34.FirstSpringBootIITU.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/")
    public String index(ModelMap model, @RequestParam(name = "page", defaultValue = "1") int page){



        if(page<1){
            page = 1;
        }

        Pageable pageable = PageRequest.of(page-1,10);
        return "index";
    }

    @PostMapping(value = "/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String add(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "price") int price
    ){


        return "redirect:/";
    }

    @GetMapping(path = "/details/{id}")
    public String details(ModelMap model, @PathVariable(name = "id") Long id){


        return "details";
    }

    @GetMapping(path = "/login")
    public String loginPage(Model model){

        return "login";

    }

    @GetMapping(path = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profilePage(Model model){

        model.addAttribute("user", getUserData());

        return "profile";
    }

    public Users getUserData(){
        Users userData = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            User secUser = (User)authentication.getPrincipal();
            userData = userRepository.findByEmail(secUser.getUsername());
        }
        return userData;
    }

}
