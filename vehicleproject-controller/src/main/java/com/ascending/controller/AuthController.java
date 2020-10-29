package com.ascending.controller;

import com.ascending.model.User;
import com.ascending.service.JWTService;
import com.ascending.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping (value = "auth")
public class AuthController {
   private Logger logger = LoggerFactory.getLogger(getClass());

   @Autowired
   private UserService userService;

   @Autowired
   private JWTService jwtService;

   private String errorMsg = "The email or password is not correct";
   private String tokenKeyWord = "Authorization";
   private String tokenType = "Barrer";
//   @PostMapping(value = "post", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Map> authenticate(@RequestBody User user){
//       Map<String,String> resultMap = new HashMap<>();
//       user = userService.save(user);
//       resultMap.put("name",user.getName());
//       resultMap.put("Email",user.getEmail());
//       resultMap.put("first name",user.getFirstName());
//       resultMap.put("last name", user.getLastName());
//
//       return ResponseEntity.status(HttpServletResponse.SC_OK).body(resultMap);
//
//   }

   @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Map> authenticate1(@RequestBody User user)  {
      String token;
      Map<String, String> resultMap = new HashMap<>();
      User retrievedUser = null;
      try {
         retrievedUser = userService.getUserByCredential(user.getEmail(), user.getPassword());

         if (retrievedUser == null) {
            resultMap.put("msg", errorMsg);
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(resultMap);

         }
         token = jwtService.generateToken(retrievedUser);
         resultMap.put("token", token);
      }catch (Exception e) {
            String msg = e.getMessage();
            if (msg==null){
               msg = "BAD REQUEST";
            }
            logger.error(msg);
            resultMap.put("msg",msg);
         }
      return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(resultMap);
   }
}
