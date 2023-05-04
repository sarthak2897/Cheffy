package com.cooks.demo.controller;
import com.cooks.demo.service.ChefService;
import com.cooks.demo.util.DTOS.RegisterChefDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@RestController
@RequestMapping("/api/chef")
@CrossOrigin(origins = "http://127.0.0.1:3000")


public class ChefController {

    @Autowired
    ChefService chefService;

    /**
     *
     * @return all chefs registered with application
     */
    @GetMapping("/get-all")
    public ResponseEntity getAllChefs(){
        log.info("Requesting all chefs");
        return new ResponseEntity<>(chefService.getAllChefs(),HttpStatus.OK);
    }

    @GetMapping("/get-profile")
    public ResponseEntity getChefProfile(@RequestParam Integer chefId) throws Exception{
        try {
            log.info("Requested for chef profile with ched id" + chefId);
            return new ResponseEntity(chefService.getChefProfile(chefId), HttpStatus.OK);
        }catch (Exception exception){
            log.error(exception.getMessage());
            return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * @param chefId system specific unique id for chef
     * @return all orders of the chef
     */
    @GetMapping("/get-all-bookings")
    public ResponseEntity getAllBookingsOfChef(@RequestParam  Integer chefId){
        try {
            log.info("Requesting all bookings of chef with id" +chefId);
            return new ResponseEntity<>(chefService.getAllOrdersOfChef(chefId), HttpStatus.OK);
        } catch (Exception exception){
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * @param registerChefDTO with basic details like email, phone number and experience
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity registerChefUser(@RequestBody RegisterChefDTO registerChefDTO){
        log.info("Register Chef initiated for " + registerChefDTO.getUserDetailDTO().getEmail());
        try {
            String message = chefService.registerChefUser(registerChefDTO);
            log.info("User registered successfully");
            return new ResponseEntity<>(message, HttpStatus.OK);

        }catch (SQLIntegrityConstraintViolationException exception){
            log.error(exception.getMessage());
            return  new ResponseEntity<>("user with this email_id or phone number already exists",HttpStatus.BAD_REQUEST);
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
