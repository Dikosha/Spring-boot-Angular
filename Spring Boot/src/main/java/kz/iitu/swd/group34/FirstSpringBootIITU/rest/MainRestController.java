package kz.iitu.swd.group34.FirstSpringBootIITU.rest;

import kz.iitu.swd.group34.FirstSpringBootIITU.entities.Roles;
import kz.iitu.swd.group34.FirstSpringBootIITU.entities.Users;
import kz.iitu.swd.group34.FirstSpringBootIITU.repositories.RolesRepository;
import kz.iitu.swd.group34.FirstSpringBootIITU.repositories.UserRepository;
import kz.iitu.swd.group34.FirstSpringBootIITU.services.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping(path = "/api")
public class MainRestController {

    private final UserRepository userRepository;
    private final RolesRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    MainRestController(UserRepository userRepository,
                       RolesRepository roleRepository,
                       UserService userService,
                       PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping(path = "/registration")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public String registration(@RequestBody Users users){
//        Items item = itemsRepository.findByIdAndDeletedAtNull(id).get();
//        item.setDeletedAt(new Date());
//        itemsRepository.save(item);
//        System.out.println();
        Set<Roles> roles = new HashSet<>();
        Roles userRole = roleRepository.findById(2L).get();
        roles.add(userRole);
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Users user  = new Users(null, users.getEmail(), users.getPassword(), users.getName(), users.getPhone(),roles);
        userRepository.save(user);
        System.out.println(users.getName());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("STATUS", 200);
        jsonObject.put("ERROR", "");
        jsonObject.put("RESULT", "some");
        return jsonObject.toString();

    }


    @PostMapping(path = "/login")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public boolean login(@RequestBody User user) {
        System.out.println("12345");
        Users u = userRepository.findByEmail(user.getUsername());
        if(u != null && passwordEncoder.matches(u.getPassword(), user.getPassword())){
            return true;
        }
        return false;
    }

    @RequestMapping("/user")
    public Principal user(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization")
                .substring("Basic".length()).trim();
        return () ->  new String(Base64.getDecoder()
                .decode(authToken)).split(":")[0];
    }




    @PostMapping(path = "/checkEmail")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public String checkEmail(@RequestBody String email){
        JSONObject jsonObject = new JSONObject();
        System.out.println(email);
        Users user = userRepository.findByEmail(email);
        if(user != null) {
            jsonObject.put("STATUS", HttpStatus.FOUND.value());
            jsonObject.put("ERROR", "Email found");
            jsonObject.put("RESULT", "");
        }
        else{
            jsonObject.put("STATUS", HttpStatus.OK.value());
            jsonObject.put("ERROR", "");
            jsonObject.put("RESULT", "");
        }


        return jsonObject.toString();

    }


//    @PostMapping(path = "/registration")             //    REGISTRATION BY USER
//    @CrossOrigin(origins = "*", allowedHeaders = "*")
//    public ResponseEntity<String> addUser(@RequestBody Users users){
//
//        Users user = userRepository.findByEmail(email);
//
//        if(user == null){
//
//            if(!email.equals(null)) {
//                return new ResponseEntity<>("email is null", HttpStatus.BAD_REQUEST);
//            }
//            if(!fullName.equals(null)) {
//                return new ResponseEntity<>("Full name is null", HttpStatus.BAD_REQUEST);
//            }
//            if(password.length() < 6){
//                return new ResponseEntity<>("Password too short", HttpStatus.BAD_REQUEST);
//            }
//            if(!password.equals(rePassword)){
//                return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);
//            }
//
//            Set<Roles> roles = new HashSet<>();
//            Roles userRole = roleRepository.getOne(2L);
//            roles.add(userRole);
//
//            user = new Users(null, email, password, fullName, roles, true);
//            userService.registerUser(user);
//
//            return new ResponseEntity<>("User Registered", HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
//    }

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
