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
@RequestMapping(path = "/master")
public class MasterRestController {

    private final RecordRepository recordRepository;
    private final ServiceRepository serviceRepository;
    private final CommentRepository commentRepository;
    private final MasterRepository masterRepository;

    @Autowired
    MasterRestController(RecordRepository recordRepository,
                       CommentRepository commentRepository,
                       ServiceRepository serviceRepository,
                       MasterRepository masterRepository){
        this.recordRepository = recordRepository;
        this.serviceRepository = serviceRepository;
        this.commentRepository = commentRepository;
        this.masterRepository = masterRepository;
    }

    @PostMapping(path = "/addNew")
    public String addMaster(@RequestBody MasterPojo masterPojo) {

        Set<Service> services = new HashSet<>();
        for(Long id : masterPojo.getServices()){
            services.add(serviceRepository.getOne(id));
        }

        Master master = new Master(null, masterPojo.getName(),
                masterPojo.getPhone(), services);
        masterRepository.save(master);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("STATUS", 200);
        jsonObject.put("ERROR", "");
        jsonObject.put("RESULT", "new master added");
        return jsonObject.toString();

    }

    @PostMapping(path = "/delete")
    public String deleteMaster(@RequestBody Long masterId) {

        Master master = masterRepository.findById(masterId).get();
        masterRepository.delete(master);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("STATUS", 200);
        jsonObject.put("ERROR", "");
        jsonObject.put("RESULT", "master deleted");
        return jsonObject.toString();
    }

    @PostMapping(path = "/editInformation")
    public String editMaster(@RequestBody MasterPojo masterPojo) {

        Set<Service> services = new HashSet<>();
        for(Long id : masterPojo.getServices()){
            services.add(serviceRepository.getOne(id));
        }
        Master master = masterRepository.getOne(masterPojo.getId());
        master.setName(masterPojo.getName());
        master.setPhone(masterPojo.getPhone());
        master.setServices(services);

        masterRepository.save(master);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("STATUS", 200);
        jsonObject.put("ERROR", "");
        jsonObject.put("RESULT", "master information updated");
        return jsonObject.toString();
    }

    @PostMapping(path = "/profile")
    public String profileMaster(@RequestBody Long masterId) {

        Master master = masterRepository.getOne(masterId);
        List<Comment> comments = commentRepository.findAllByMaster(master);

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonData = new JSONObject();
        JSONArray jsonArrayOfComments = new JSONArray();
        JSONArray jsonArrayOfServices = new JSONArray();
        JSONObject jsonMasterInformation = new JSONObject();

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy, HH:mm");
        for(Comment c : comments){
            JSONObject jsonComment = new JSONObject();
            jsonComment.put("comment_id", c.getId());
            jsonComment.put("comment_author", c.getAuthor().getName());
            jsonComment.put("comment_content", c.getContent());
            jsonComment.put("comment_date", format.format(c.getDate()));

            jsonArrayOfComments.put(jsonComment);
        }

        for(Service s : masterRepository.getOne(masterId).getServices()){
            JSONObject jsonService = new JSONObject();
            jsonService.put("service_id", s.getId());
            jsonService.put("service_name", s.getName());

            jsonArrayOfServices.put(jsonService);
        }


        jsonMasterInformation.put("master_name", master.getName());
        jsonMasterInformation.put("master_phone", master.getPhone());

        jsonData.put("master", jsonMasterInformation);
        jsonData.put("master_comments", jsonArrayOfComments);
        jsonData.put("services", jsonArrayOfServices);

        jsonObject.put("STATUS", 200);
        jsonObject.put("ERROR", "");
        jsonObject.put("RESULT", jsonData);


        return jsonObject.toString();
    }

    @PostMapping(path = "/getAll")
    public String getAllMasters() {

        List<Master> masters = masterRepository.findAll();
        JSONArray jsonArray = new JSONArray();

        for(Master m : masters) {
            JSONObject jsonData = new JSONObject();
            jsonData.put("master_id", m.getId());
            jsonData.put("master_name", m.getName());
            jsonData.put("master_phone", m.getPhone());
            jsonArray.put(jsonData);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("STATUS", 200);
        jsonObject.put("ERROR", "");
        jsonObject.put("RESULT", jsonArray);

        System.out.println(jsonObject.toString());
        return jsonObject.toString();

    }

    @PostMapping(path = "/getServices")
    public String getServices(@RequestBody Long id) {
        Master master = masterRepository.findById(id).get();
        JSONArray jsonArray = new JSONArray();
        for(Service s : master.getServices()) {
            JSONObject jsonData = new JSONObject();
            jsonData.put("service_id", s.getId());
            jsonData.put("service_name", s.getName());
            jsonData.put("service_price", s.getPrice());

            jsonArray.put(jsonData);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("STATUS", 200);
        jsonObject.put("ERROR", "");
        jsonObject.put("RESULT", jsonArray);

        return jsonObject.toString();
    }

}

