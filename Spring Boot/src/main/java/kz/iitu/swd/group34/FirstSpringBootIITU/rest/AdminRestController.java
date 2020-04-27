package kz.iitu.swd.group34.FirstSpringBootIITU.rest;

import kz.iitu.swd.group34.FirstSpringBootIITU.entities.*;
import kz.iitu.swd.group34.FirstSpringBootIITU.pojo.ServicePojo;
import kz.iitu.swd.group34.FirstSpringBootIITU.repositories.*;
import kz.iitu.swd.group34.FirstSpringBootIITU.security.jwt.JwtUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/admin")
public class AdminRestController {

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
    AdminRestController(UserRepository userRepository,
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

    @PostMapping(path = "/getAllUsers")
    public String getAllUsers() {

        List<Users> usersList = userRepository.findAll();
        JSONArray jsonArray = new JSONArray();

        for(int i = 0; i < usersList.size(); i++){
            JSONObject jsonData = new JSONObject();
            jsonData.put("user_id", usersList.get(i).getId());
            jsonData.put("user_email", usersList.get(i).getEmail());
            jsonData.put("user_name", usersList.get(i).getName());
            jsonData.put("user_phone", usersList.get(i).getPhone());
            jsonData.put("user_isBlocked", usersList.get(i).getIsBlocked());
            jsonArray.put(jsonData);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("STATUS", 200);
        jsonObject.put("ERROR", "");
        jsonObject.put("RESULT", jsonArray);
        return jsonObject.toString();
    }

    @PostMapping(path = "/blockUser")
    public String blockUser(@RequestBody Long id) {

        Users user = userRepository.findById(id).get();
        user.setIsBlocked(true);
        userRepository.save(user);


        JSONObject jsonObject = new JSONObject();

        jsonObject.put("STATUS", 200);
        jsonObject.put("ERROR", "");
        jsonObject.put("RESULT", "blocked");
        return jsonObject.toString();
    }

    @PostMapping(path = "/unblockUser")
    public String unblockUser(@RequestBody Long id) {

        Users user = userRepository.findById(id).get();
        user.setIsBlocked(false);
        userRepository.save(user);


        JSONObject jsonObject = new JSONObject();

        jsonObject.put("STATUS", 200);
        jsonObject.put("ERROR", "");
        jsonObject.put("RESULT", "unblocked");

        return jsonObject.toString();
    }

    @PostMapping(path = "/addService")
    public String addService(@RequestBody ServicePojo servicePojo) {

        Service service = new Service(null, servicePojo.getName(),
                servicePojo.getDescription(), servicePojo.getPrice());
        serviceRepository.save(service);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("STATUS", 200);
        jsonObject.put("ERROR", "");
        jsonObject.put("RESULT", "Added");
        return jsonObject.toString();
    }

    @PostMapping(path = "/editService")
    public String editService(@RequestBody ServicePojo servicePojo) {

        Service service = serviceRepository.findById(servicePojo.getId()).get();
        service.setDescription(servicePojo.getDescription());
        service.setName(servicePojo.getName());
        service.setPrice(servicePojo.getPrice());
        serviceRepository.save(service);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("STATUS", 200);
        jsonObject.put("ERROR", "");
        jsonObject.put("RESULT", "Updated");
        return jsonObject.toString();
    }

    @PostMapping(path = "/deleteService")
    public String deleteService(@RequestBody Long id) {

        Service service = serviceRepository.findById(id).get();
        serviceRepository.delete(service);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("STATUS", 200);
        jsonObject.put("ERROR", "");
        jsonObject.put("RESULT", "Deleted");
        return jsonObject.toString();
    }

    @PostMapping(path = "/getAllServices")
    public String getAllService() {
        List<Service> servicesList = serviceRepository.findAll();
        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < servicesList.size(); i++){
            JSONObject jsonData = new JSONObject();
            jsonData.put("service_id", servicesList.get(i).getId());
            jsonData.put("service_name", servicesList.get(i).getName());
            jsonData.put("service_description", servicesList.get(i).getDescription());
            jsonData.put("service_price", servicesList.get(i).getPrice());

            jsonArray.put(jsonData);
        }
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("STATUS", 200);
        jsonObject.put("ERROR", "");
        jsonObject.put("RESULT", jsonArray);
        return jsonObject.toString();
    }


}
