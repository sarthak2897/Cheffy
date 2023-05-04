package com.cooks.demo.service;

import com.cooks.demo.exception.UserNotFoundException;
import com.cooks.demo.model.*;
import com.cooks.demo.repository.ChefRepository;
import com.cooks.demo.repository.OrderRepository;
import com.cooks.demo.util.DTOS.ChefDetailDTO;
import com.cooks.demo.util.DTOS.RegisterChefDTO;
import com.cooks.demo.util.DTOS.ResponseDTOs.ChefResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Ejb3TransactionAnnotationParser;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ChefService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ValidatorService validatorService;

    @Autowired
    UserService userService;

    @Autowired
    ChefRepository chefRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MailService mailService;


    public RegisterChefDTO getChefProfile(Integer chefId) throws Exception{
        Chef chef = getChefById(chefId);
        RegisterChefDTO registerChefDTO = getRegisterChefDTOFromChef(chef);
        return  registerChefDTO;
    }

    public Chef getChefById(Integer customerId) throws Exception{
        Optional<Chef> chef = chefRepository.findById(customerId);
        if(chef.isPresent()){
            return chef.get();
        }else {
            throw new Exception("chef not found");
        }
    }
    private RegisterChefDTO getRegisterChefDTOFromChef(Chef chef) {
        RegisterChefDTO registerChefDTO = new RegisterChefDTO();
        registerChefDTO.setChefDetailDTO(getChefDetailDTOFromChef(chef));
        registerChefDTO.setUserDetailDTO(userService.getUserDetailDTOFromUser(chef.getUser()));
        return  registerChefDTO;
    }

    public ChefDetailDTO getChefDetailDTOFromChef(Chef chef) {
        return ChefDetailDTO.builder().experience(chef.getExperience()).qualification(chef.getQualification()).build();

    }

    /**
     * business logic for getting the tire of the chef based on experience
     * @param experience
     * @return
     */
    public Integer setTierOnExperience(Double experience) {
        if (experience >= 5.0)
            return 1;
        else if (experience >= 3.0 && experience < 5.0)
            return 2;
        else
            return 3;
    }

    /**
     * converts order object to DTO object
     * @param orders
     * @return
     */
    public List<ChefResponseDTO> convertOrderToChefOrdersDTO(List<Order> orders) {
        List<ChefResponseDTO> chefOrders = new ArrayList<>();
        return chefOrders = orders.stream()
                .map(x -> new ChefResponseDTO(
                        x.getId(),
                        x.getCustomer().getUser().getName(),
                        x.getCustomer().getUser().getEmail(),
                        x.getCustomer().getUser().getPhoneNumber(),
                        x.getDateOfSupply(),
                        x.getStatus(),
                        (x.getAmount()/5)*4,
                        x.getCuisine(),
                        x.getMealType(),
                        x.getNoOfPeople(),
                        x.getFoodPreference()
                )).sorted(new Comparator<ChefResponseDTO>() {
                    @Override
                    public int compare(ChefResponseDTO o1, ChefResponseDTO o2) {
                        return o2.getOrderId() - o1.getOrderId();
                    }
                })
                .collect(Collectors.toList());
    }

    /**
     * gets all the objects of chef
     * @param chefId
     * @return
     * @throws Exception
     */
    public List<ChefResponseDTO> getAllOrdersOfChef(Integer chefId) throws Exception {
        try {
            List<Order> orders = orderRepository.getAllOrdersOfChef(chefId);
            return convertOrderToChefOrdersDTO(orders);
        } catch (Exception userNotFoundException) {
            throw new Exception("Chef with Id not Found" + chefId);
        }
    }

    /**
     * gets all the chefs
     * @return
     */
    public List<Chef> getAllChefs() {
        return (List<Chef>) chefRepository.findAll();
    }

    /**
     * gets Chef Object from DTO object
     * @param registerChefDTO
     * @param user
     * @return
     */
    public Chef getChefFromDTO(RegisterChefDTO registerChefDTO, User user) {
        return Chef.builder().
                user(user).
                qualification(registerChefDTO.getChefDetailDTO().getQualification())
                .experience(registerChefDTO.getChefDetailDTO().getExperience())
                .build();
    }

    /**
     * registers chef as part of the application
     * @param registerChefDTO
     * @param user
     * @return
     * @throws Exception
     */
    public String registerChef(RegisterChefDTO registerChefDTO, User user) throws Exception {
        Chef chef = getChefFromDTO(registerChefDTO, user);
        chef.setIsActive(true);
        chef.setTier(setTierOnExperience(chef.getExperience()));
        chefRepository.save(chef);
        return "You have been successfully registered as chef.";
    }

    /**
     * adds Chef User to the data base. rolls back if the transaction is not fully completed
     * @param registerChefDTO
     * @return
     * @throws SQLIntegrityConstraintViolationException
     * @throws UserNotFoundException
     * @throws Exception
     */

    @Transactional(rollbackFor = Exception.class)
    public String registerChefUser(RegisterChefDTO registerChefDTO) throws SQLIntegrityConstraintViolationException, UserNotFoundException, Exception {
        try {
            if (validatorService.validatePasswordAndVerifyPassword(registerChefDTO.getUserDetailDTO().getPassword(), registerChefDTO.getUserDetailDTO().getVerifyPassword())) {
                User user = userService.getUserFromDTO(registerChefDTO.getUserDetailDTO());
                user.setPassword(passwordEncoder.encode(registerChefDTO.getUserDetailDTO().getPassword()));
                userService.registerBasicUserDetails(user);
                String message = "Your registration with cooks as a chef has been successful. We would be delighted to serve by getting exiting customers. Please feel free to reach out to our customer care center for help";
                mailService.sendEmail(user.getEmail(), "Registration Successful", message);
                return registerChef(registerChefDTO, user);
            } else {
                log.error("passwords do not match");
                throw new Exception("Passwords do not match. please try again");
            }
        }
        catch (DataIntegrityViolationException exception){
            log.error(exception.getMessage());
            throw new Exception("User with this email_id or phone_number already exists");
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            throw new Exception("something went wrong. please contact admin");
        }
    }
}
