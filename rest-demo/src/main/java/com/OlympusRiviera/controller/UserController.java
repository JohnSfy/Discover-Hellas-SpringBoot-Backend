//package com.OlympusRiviera.controller;
//
//import com.OlympusRiviera.model.User;
//import com.OlympusRiviera.service.UserService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@CrossOrigin(origins = "http://localhost:5173")
//@RestController
//@RequestMapping("/user")
//public class UserController {
//
//    UserService userService;
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//
//    // Read Specific User Details
//    @GetMapping("{userId}")
//    public User getUserDetails(@PathVariable("userId") String userId){
//
//
//        return userService.getUser(userId);
////                new CloudVendor("C1", "Vendor 1", "Address one", "12345");
//    }
//
//
//    // Read all Users Details from DB
//    @GetMapping()
//    public List<User> getAllUserDetails(){
//
//
//        return userService.getAllUsers();
////                new CloudVendor("C1", "Vendor 1", "Address one", "12345");
//    }
//
//    @PostMapping
//    public String createUserDetails(@RequestBody User user){
////        this.cloudVendor = cloudVendor;
//        userService.createUser(user);
//        return "User Created Successfully ";
//
//    }
//
//
//    @PutMapping
//    public String updateUser(@RequestBody User user){
////        this.cloudVendor = cloudVendor;
//        userService.updateUser(user);
//        return "User Updated Successfully";
//    }
//
//    @DeleteMapping("{userId}")
//    public String deleteUserDetails(@PathVariable String userId){
//        userService.deleteUser(userId);
//        return "User Deleted Successfully";
//    }
//
//}
