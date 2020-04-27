package kz.iitu.swd.group34.FirstSpringBootIITU.rest;

import kz.iitu.swd.group34.FirstSpringBootIITU.entities.*;
import kz.iitu.swd.group34.FirstSpringBootIITU.payload.response.JwtResponse;
import kz.iitu.swd.group34.FirstSpringBootIITU.pojo.CommentPojo;
import kz.iitu.swd.group34.FirstSpringBootIITU.pojo.MasterPojo;
import kz.iitu.swd.group34.FirstSpringBootIITU.pojo.RecordPojo;
import kz.iitu.swd.group34.FirstSpringBootIITU.pojo.ServicePojo;
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
@RequestMapping(path = "/record")
public class RecordRestController {

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
    RecordRestController(UserRepository userRepository,
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

    @PostMapping(path = "/addRecord")
    public String addRecord(@RequestBody RecordPojo recordpojo) {


        Record record  = new Record(null,
                serviceRepository.findById(recordpojo.getService_id()).get(),
                userRepository.findById(recordpojo.getClient_id()).get(),
                masterRepository.findById(recordpojo.getMaster_id()).get(),
                recordpojo.getDate());

        recordRepository.save(record);
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("STATUS", 200);
        jsonObject.put("ERROR", "");
        jsonObject.put("RESULT", "new record added");
        return jsonObject.toString();
    }

    @PostMapping(path = "/deleteRecord")
    public String deleteRecord(@RequestBody Long recordId) {

        Record record = recordRepository.findById(recordId).get();
        recordRepository.delete(record);
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("STATUS", 200);
        jsonObject.put("ERROR", "");
        jsonObject.put("RESULT", "record deleted");
        return jsonObject.toString();
    }

    @PostMapping(path = "/getAllRecords")
    public String getAllRecords() {

        List<Record> recordList = recordRepository.findAll();
        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < recordList.size(); i++){
            JSONObject jsonData = new JSONObject();
            jsonData.put("record_id", recordList.get(i).getId());
            jsonData.put("service_name", recordList.get(i).getService().getName());
            jsonData.put("master_name", recordList.get(i).getMaster().getName());
            jsonData.put("client_name", recordList.get(i).getClient().getName());
            jsonData.put("client_phone", recordList.get(i).getClient().getPhone());
            jsonData.put("client_email", recordList.get(i).getClient().getEmail());
            jsonData.put("master_phone", recordList.get(i).getMaster().getPhone());
            jsonArray.put(jsonData);
        }
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("STATUS", 200);
        jsonObject.put("ERROR", "");
        jsonObject.put("RESULT", jsonArray);
        return jsonObject.toString();
    }



}

