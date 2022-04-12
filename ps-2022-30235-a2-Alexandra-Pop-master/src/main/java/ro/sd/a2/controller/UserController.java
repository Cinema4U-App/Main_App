package ro.sd.a2.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ro.sd.a2.constants.ExistingUserException;
import ro.sd.a2.constants.NonexistentUserException;
import ro.sd.a2.constants.NotMatchingPasswordsException;
import ro.sd.a2.dto.*;
import ro.sd.a2.entity.User;
import ro.sd.a2.service.MovieService;
import ro.sd.a2.service.UserService;

import java.util.List;


@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private UserDto loggedUserDto = null;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @GetMapping("/login")
    public ModelAndView loginView(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    @PostMapping("/profile")
    public ModelAndView signIn(LoginDto userDto){
        ModelAndView mav = new ModelAndView();
        try{
            UserDto userDtoReturned = userService.loginUser(userDto);
            mav.addObject("loggedUser", userDtoReturned);
            mav.setViewName("profile");
            loggedUserDto = userDtoReturned;
        }
        catch (NonexistentUserException ex){
            mav.addObject("errorMessage", 1);
            mav.setViewName("redirect:/login");
        }
        return mav;
    }

    @GetMapping("/register")
    public ModelAndView getRegisterPage(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("register");
        return mav;
    }

    @PostMapping("/login")
    public ModelAndView addUser(RegisterDto userDto){
        ModelAndView mav = new ModelAndView();
        try{
            userService.addUser(userDto);
            mav.setViewName("login");
        }
        catch (NotMatchingPasswordsException ex){
            mav.setViewName("redirect:/register");
            mav.addObject("errorMessage", 1);
        }
        catch (ExistingUserException exx){
            mav.setViewName("redirect:/register");
            mav.addObject("errorMessage", 2);
        }
        return mav;
    }

    @GetMapping("/profile")
    public ModelAndView showProfile() {
        ModelAndView mav = new ModelAndView();
        if(loggedUserDto != null){
            mav.addObject("loggedUser", loggedUserDto);
            mav.setViewName("/profile");
        }
        else{
            mav.setViewName("redirect:/login");
        }
        return mav;
    }

    @PostMapping("/profile/userMainPage")
    public ModelAndView editProfile(UserDto userDto){
        ModelAndView mav = new ModelAndView();
        if(loggedUserDto != null){
            UserDto newUserDto = userService.editProfile(userDto);
            mav.addObject("loggedUser", newUserDto);
            mav.setViewName("profile");
            loggedUserDto = newUserDto;
        }
        else{
            mav.setViewName("redirect:/login");
        }
        return mav;
    }

    @GetMapping("/history")
    public ModelAndView showHistory() {
        ModelAndView mav = new ModelAndView();
        if(loggedUserDto != null){
            mav.setViewName("history");
        }
        else{
            mav.setViewName("redirect:/login");
        }
        return mav;
    }

    @GetMapping("/movies")
    public ModelAndView showMovies() {
        ModelAndView mav = new ModelAndView();
        if(loggedUserDto != null){
            List<MovieDto> movieDtos = movieService.getAllMovies();
            mav.addObject("movies", movieDtos);
            mav.setViewName("movies");
        }
        else{
            mav.setViewName("redirect:/login");
        }
        return mav;
    }

    @PostMapping("/movies")
    public ModelAndView searchMovies(MovieDto movieDto) {
        ModelAndView mav = new ModelAndView();
        if(loggedUserDto != null){
            List<MovieDto> movieDtos = movieService.filterMovies(movieDto);
            mav.addObject("movies", movieDtos);
            mav.setViewName("movies");
        }
        else{
            mav.setViewName("redirect:/login");
        }
        return mav;
    }

    @GetMapping("/schedule")
    private ModelAndView goToSchedule(){
        ModelAndView mav = new ModelAndView();
        if(loggedUserDto != null){
            mav.setViewName("schedule");
        }
        else{
            mav.setViewName("redirect:/login");
        }
        return mav;
    }

//    @PostMapping("/schedule")
//    private ModelAndView getCinemaSchedule(CinemaDto cinemaDto){
//
//    }

    @GetMapping("/logout")
    public ModelAndView logout(){
        ModelAndView mav = new ModelAndView();
        loggedUserDto = null;
        mav.setViewName("redirect:/login");
        return mav;
    }

}
