package kz.iitu.swd.group34.FirstSpringBootIITU.rest;

import kz.iitu.swd.group34.FirstSpringBootIITU.entities.Roles;
import kz.iitu.swd.group34.FirstSpringBootIITU.entities.Users;
import kz.iitu.swd.group34.FirstSpringBootIITU.payload.response.JwtResponse;
import kz.iitu.swd.group34.FirstSpringBootIITU.repositories.RolesRepository;
import kz.iitu.swd.group34.FirstSpringBootIITU.repositories.UserRepository;
import kz.iitu.swd.group34.FirstSpringBootIITU.security.jwt.JwtUtils;
import kz.iitu.swd.group34.FirstSpringBootIITU.services.UserDetailsImpl;
import kz.iitu.swd.group34.FirstSpringBootIITU.services.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api")
public class MainRestController {

    private final UserRepository userRepository;
    private final RolesRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Autowired
    MainRestController(UserRepository userRepository,
                       RolesRepository roleRepository,
                       UserService userService,
                       PasswordEncoder passwordEncoder,
                       JwtUtils jwtUtils,
                       AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping(path = "/registration")
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
//        System.out.println(users.getName());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("STATUS", 200);
        jsonObject.put("ERROR", "");
        jsonObject.put("RESULT", "some");
        return jsonObject.toString();

    }

    @PostMapping(path = "/checkEmail")
    public String checkEmail(@RequestBody String email){
        JSONObject jsonObject = new JSONObject();
//        System.out.println(email);
        Users user = userRepository.findByEmail(email).get();
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

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody Users loginRequest) {
        System.out.println("101");
        System.out.println(loginRequest.toString());
        System.out.println(loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

//        System.out.println("qwer5");
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getName(),
                userDetails.getEmail(),
                roles));
    }

}
