package kz.iitu.swd.group34.FirstSpringBootIITU.rest;

import kz.iitu.swd.group34.FirstSpringBootIITU.entities.*;
import kz.iitu.swd.group34.FirstSpringBootIITU.payload.response.JwtResponse;
import kz.iitu.swd.group34.FirstSpringBootIITU.pojo.*;
import kz.iitu.swd.group34.FirstSpringBootIITU.repositories.*;
import kz.iitu.swd.group34.FirstSpringBootIITU.security.jwt.JwtUtils;
import kz.iitu.swd.group34.FirstSpringBootIITU.services.UserDetailsImpl;
import kz.iitu.swd.group34.FirstSpringBootIITU.services.UserService;
import org.json.JSONArray;
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
@RequestMapping(path = "/user")
public class UserRestController {

    private final UserRepository userRepository;
    private final RecordRepository recordRepository;
    private final ServiceRepository serviceRepository;
    private final CommentRepository commentRepository;
    private final MasterRepository masterRepository;
    private final RolesRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Autowired
    UserRestController(UserRepository userRepository,
                       RecordRepository recordRepository,
                       RolesRepository roleRepository,
                       CommentRepository commentRepository,
                       ServiceRepository serviceRepository,
                       MasterRepository masterRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtils jwtUtils,
                       AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
        this.recordRepository = recordRepository;
        this.serviceRepository = serviceRepository;
        this.commentRepository = commentRepository;
        this.masterRepository = masterRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping(path = "/profile")
    public String profileUser(@RequestBody Long userId) {

        Users user = userRepository.getOne(userId);
        List<Record> records = recordRepository.findAllByClient(user);

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonData = new JSONObject();
        JSONArray jsonArrayOfRecords = new JSONArray();
        JSONObject jsonUserInformation = new JSONObject();

        for(Record r : records){
            JSONObject jsonRecord = new JSONObject();
            jsonRecord.put("record_id", r.getId());
            jsonRecord.put("record_master_name", r.getMaster().getName());
            jsonRecord.put("record_service", r.getService().getName());
            jsonRecord.put("record_date", r.getDate());

            jsonArrayOfRecords.put(jsonRecord);
        }

        jsonUserInformation.put("user_name", user.getName());
        jsonUserInformation.put("user_email", user.getEmail());
        jsonUserInformation.put("user_phone", user.getPhone());

        jsonData.put("user", jsonUserInformation);
        jsonData.put("records", jsonArrayOfRecords);

        jsonObject.put("STATUS", 200);
        jsonObject.put("ERROR", "");
        jsonObject.put("RESULT", jsonData);

        return jsonObject.toString();
    }

    @PostMapping(path = "/editInformation")
    public String editUser(@RequestBody UserInfoPojo userPojo) {

        Users user = userRepository.getOne(userPojo.getId());
        user.setName(userPojo.getName());
        user.setEmail(userPojo.getEmail());
        user.setPhone(userPojo.getPhone());

        userRepository.save(user);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("STATUS", 200);
        jsonObject.put("ERROR", "");
        jsonObject.put("RESULT", "user information updated");
        return jsonObject.toString();
    }

    @PostMapping(path = "/updatePassword")
    public String updatePassword(@RequestBody UserPasswordPojo userPasswordPojo) {

        Users user = userRepository.getOne(userPasswordPojo.getId());
        JSONObject jsonObject = new JSONObject();

        if(passwordEncoder.matches(userPasswordPojo.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(userPasswordPojo.getNewPassword()));
            userRepository.save(user);
            jsonObject.put("STATUS", 200);
            jsonObject.put("ERROR", "");
            jsonObject.put("RESULT", "password updated");
        }
        else {
            jsonObject.put("STATUS", 200);
            jsonObject.put("ERROR", "old password is incorrect");
            jsonObject.put("RESULT", "passwords NOT updated");
        }

        return jsonObject.toString();
    }
}
