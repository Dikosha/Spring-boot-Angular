package kz.iitu.swd.group34.FirstSpringBootIITU.rest;

import kz.iitu.swd.group34.FirstSpringBootIITU.entities.Record;
import kz.iitu.swd.group34.FirstSpringBootIITU.entities.Roles;
import kz.iitu.swd.group34.FirstSpringBootIITU.entities.Users;
import kz.iitu.swd.group34.FirstSpringBootIITU.payload.response.JwtResponse;
import kz.iitu.swd.group34.FirstSpringBootIITU.pojo.RecordPojo;
import kz.iitu.swd.group34.FirstSpringBootIITU.repositories.*;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api")
public class MainRestController {

    private final UserRepository userRepository;
    private final RecordRepository recordRepository;
    private final ServiceRepository serviceRepository;
    private final MasterRepository masterRepository;
    private final RolesRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Autowired
    MainRestController(UserRepository userRepository,
                       RecordRepository recordRepository,
                       RolesRepository roleRepository,
                       ServiceRepository serviceRepository,
                       MasterRepository masterRepository,
                       UserService userService,
                       PasswordEncoder passwordEncoder,
                       JwtUtils jwtUtils,
                       AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
        this.recordRepository = recordRepository;
        this.serviceRepository = serviceRepository;
        this.masterRepository = masterRepository;
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
        Roles userRole = roleRepository.getOne(2L);
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
        Optional<Users> opt  = userRepository.findByEmail(email);
        if(opt.isPresent()) {
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

    @PostMapping(path = "/addRecord")
    public String addRecord(@RequestBody RecordPojo record) throws ParseException {

        Record record1  = new Record(null, serviceRepository.findById(record.getService_id()).get(),
                userRepository.findById(record.getClient_id()).get(), masterRepository.findById(record.getMaster_id()).get(),
                new SimpleDateFormat("dd/MM/yyyy").parse(record.getDate()));
        recordRepository.save(record1);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("STATUS", 200);
        jsonObject.put("ERROR", "");
        jsonObject.put("RESULT", "some");
        return jsonObject.toString();

    }

}
