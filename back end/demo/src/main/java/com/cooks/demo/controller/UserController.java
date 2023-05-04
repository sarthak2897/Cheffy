package com.cooks.demo.controller;
import com.cooks.demo.security.MyUserDetails;
import com.cooks.demo.service.UserService;
import com.cooks.demo.util.DTOS.ChangePasswordDTO;
import com.cooks.demo.util.DTOS.LoginDTO;
import com.cooks.demo.util.DTOS.ResponseDTOs.LoginResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://127.0.0.1:3000/")
//@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * verifies user credentials and returns valid token
     * @param loginDTO
     * @return
     */

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO){
        try {
            log.info("login initiated with"+loginDTO.getUsername());
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            //get customer/chef id from user id
            LoginResponseDTO loginResponseDTO = userService.getChefOrCustomerByUser(userDetails);
            System.out.println(loginResponseDTO);
            return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
        }
        catch(Exception exception){
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Helps user to change password after user is logged in
     * @param changePasswordDTO
     * @return
     */

    @PostMapping("/change-password")
    public ResponseEntity changePassword(@RequestBody ChangePasswordDTO changePasswordDTO){
        log.info("change password end point called");
        try {
            log.info("password changed initiated");
            return new ResponseEntity<>(userService.changePassword(changePasswordDTO),HttpStatus.OK);
        } catch (Exception exception){
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * helps user to recover password if user has forgot the password
     * @param email
     * @return
     */

    @GetMapping("/forgot-password")
    public ResponseEntity forgotPassword(@RequestParam String email){
        try {
            log.info("forgot-password requested for" +email);
            return new ResponseEntity<>(forgotPassword(email),HttpStatus.OK);

        }catch (Exception exception){
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
