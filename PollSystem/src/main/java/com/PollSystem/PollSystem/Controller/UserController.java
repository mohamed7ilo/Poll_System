package com.PollSystem.PollSystem.Controller;

import com.PollSystem.PollSystem.Model.UserModel;
import com.PollSystem.PollSystem.Service.AppService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private AppService appService;

    @PostMapping("/register")
    public Response registerUser(@RequestParam(name = "firstName") String firstName,
            @RequestParam(name = "lastName") String lastName,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "age") int age,
            @RequestParam(name = "address") String address) {

        Response res;
        try {

            res = new Response(appService.registerUser(firstName, lastName, email, age, address), null);
        } catch (Exception e) {
            res = new Response(null, e.getMessage());
        }
        return res;
    }

    @DeleteMapping("/{userId}")
    public Response removeUser(@PathVariable(name = "userId") long userId) {
        Response res;
        try {
            appService.removeUser(userId);
            res = new Response(200, null);
        } catch (Exception e) {
            res = new Response(400, "Failed to remove user");
        }
        return res;
    }

    @GetMapping("/{userId}")
    public Response getUserById(@PathVariable(name = "userId") long userId) {
        Response res;
        try {
            UserModel user = appService.getUserById(userId);
            res = new Response(user, null);
        } catch (Exception e) {
            res = new Response(404, "User not found");
        }
        return res;
    }

    @PutMapping("/{userId}")
    public Response updateUser(@PathVariable(name = "userId") long userId,
            @RequestParam(name = "firstName",required = false) String firstName,
            @RequestParam(name = "lastName",required = false) String lastName,
            @RequestParam(name = "email",required = false) String email,
            @RequestParam(name = "age",required = false) Integer age,
            @RequestParam(name = "address",required = false) String address) {
        Response res;
        try {
            UserModel updatedUser = appService.updateUser(userId, firstName, lastName, email, age==null?0:age.intValue(), address);
            res = new Response(updatedUser, null);
        } catch (Exception e) {
            res = new Response(400, "Failed to update user");
        }
        return res;
    }

    @GetMapping("/{userId}/polls/count")
    public Response getUserNumberOfPolls(@PathVariable(name = "userId") long userId) {
        Response res;
        try {
            int pollCount = appService.getUserNumberOfPolls(userId);
            res = new Response(pollCount, null);
        } catch (Exception e) {
            res = new Response(404, "User not found");
        }
        return res;
    }
}
