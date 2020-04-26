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
@RequestMapping(path = "/api")
public class CommentRestController {

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
    CommentRestController(UserRepository userRepository,
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

    @PostMapping(path = "/addCommentOnMaster")
    public String addCommentOnMaster(@RequestBody CommentPojo commentPojo ) {

        Comment comment = new Comment(null, masterRepository.findById(commentPojo.getMaster_id()).get(),
                commentPojo.getContent(), userRepository.findById(commentPojo.getAuthor_id()).get(),
                commentPojo.getDate());
        commentRepository.save(comment);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("STATUS", 200);
        jsonObject.put("ERROR", "");

        jsonObject.put("RESULT", "new comment added");
        return jsonObject.toString();

    }
}
