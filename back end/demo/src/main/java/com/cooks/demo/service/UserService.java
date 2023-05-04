package com.cooks.demo.service;

import com.cooks.demo.model.Address;
import com.cooks.demo.model.Chef;
import com.cooks.demo.model.Customer;
import com.cooks.demo.model.User;
import com.cooks.demo.repository.ChefRepository;
import com.cooks.demo.repository.CustomerRepository;
import com.cooks.demo.repository.UserRepository;
import com.cooks.demo.security.MyUserDetails;
import com.cooks.demo.util.DTOS.AddressDTO;
import com.cooks.demo.util.DTOS.ChangePasswordDTO;
import com.cooks.demo.util.DTOS.ResponseDTOs.LoginResponseDTO;
import com.cooks.demo.util.DTOS.UserDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ValidatorService validatorService;
    @Autowired
    MailService mailService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ChefRepository chefRepository;



    public UserDetailDTO getUserDetailDTOFromUser(User user){
        UserDetailDTO userDetailDTO = UserDetailDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber()).build();
        return  userDetailDTO;
    }

    public String forgotPassword(String emailId) throws Exception{
        Optional<User> user = userRepository.findByEmail(emailId);
        if (user.isPresent()){
            User presentUser = user.get();
            presentUser.setPassword(passwordEncoder.encode("newPassword"));
            userRepository.save(presentUser);
//            mailService.sendEmail(presentUser.getEmail(),"Password Reset","This is your new password. please change the same in the next login for your own security reasons");
            return "your password has been sent to your registered email id";
        }else {
            throw new Exception("Sorry we do not have record of this user");
        }
    }

    /**
     * helps user to change the password
     * @param changePasswordDTO
     * @return
     * @throws Exception
     */
    public String changePassword(ChangePasswordDTO changePasswordDTO) throws Exception{
        if(validatorService.validatePasswordAndVerifyPassword(changePasswordDTO.getPassword(), changePasswordDTO.getVerifyPassword())){
            Optional<User> user = userRepository.findByEmail(changePasswordDTO.getEmail());
            if (user.isPresent()) {
                User presentUser = user.get();
                presentUser.setPassword(passwordEncoder.encode(changePasswordDTO.getPassword()));
                userRepository.save(presentUser);
                return "password updated Successfully";
            } else {
                throw new Exception("User with this email not found");
            }
        } else {
            throw new Exception("Passwords do not match. please try again");
        }
    }

    /**
     * saves the basic details to the database
     * @param user
     * @return
     */
    public User registerBasicUserDetails(User user) {
        return userRepository.save(user);
    }

    /**
     * gets user object for DTO
     * @param userDetailDTO
     * @return
     */
    public User getUserFromDTO(UserDetailDTO userDetailDTO) {
        return User.builder()
                .name(userDetailDTO.getName())
                .email(userDetailDTO.getEmail())
                .gender(userDetailDTO.getGender())
                .phoneNumber(userDetailDTO.getPhoneNumber())
                .userType(userDetailDTO.getUserType())
                .password(userDetailDTO.getPassword()).build();
    }

    public LoginResponseDTO getChefOrCustomerByUser(MyUserDetails myUserDetails){
        LoginResponseDTO loginResponseDTO = LoginResponseDTO.builder().email(myUserDetails.getUsername()).username(myUserDetails.getDisplayName()).build();
        if(myUserDetails.getUserRole().equals("ROLE_CUSTOMER")){
            //Customer Respository
            Customer customer = customerRepository.getCustomerByUserId(myUserDetails.getUserId());
            System.out.println("Customer is "+customer);
            loginResponseDTO.setAccountId(customer.getId());
            loginResponseDTO.setRole(myUserDetails.getUserRole());
        }
        else{
                Chef chef = chefRepository.getChefByUserId(myUserDetails.getUserId());
                loginResponseDTO.setAccountId(chef.getId());
                loginResponseDTO.setRole(myUserDetails.getUserRole());
        }
        return loginResponseDTO;
    }

}

