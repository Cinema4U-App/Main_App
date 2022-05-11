package ro.sd.a2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.config.RabbitSender;
import ro.sd.a2.constants.ExistingUserException;
import ro.sd.a2.constants.NonexistentUserException;
import ro.sd.a2.constants.NotMatchingPasswordsException;
import ro.sd.a2.dto.*;
import ro.sd.a2.service.CinemaService;
import ro.sd.a2.service.MovieService;
import ro.sd.a2.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private UserDto loggedUserDto = null;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RabbitSender rabbitSender;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private CinemaService cinemaService;

    private String uuid = "9d09d31e-e9e2-44c0-84be-5b3de26167c75dab64e7-c789-4418-9afa-777fcecf01f8";

    @GetMapping("/bookTickets")
    public ModelAndView buyTickets(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("seats");

        rabbitSender.send(loggedUserDto);

        return mav;
    }

    //METODA GET PENTRU LOGIN PAGE:
    @GetMapping("/login")
    public ModelAndView loginView(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    //METODA POST PENTRU LOGGING IN USER:
    @PostMapping("/profile")
    public ModelAndView signIn(LoginDto userDto){
        ModelAndView mav = new ModelAndView();
        try{
            UserDto userDtoReturned = userService.loginUser(userDto);
            mav.addObject("loggedUser", userDtoReturned);
            mav.setViewName("profile");
            loggedUserDto = userDtoReturned;
            log.info("Logare cu succes a user-ului "+loggedUserDto.getEmail()+" !");
        }
        catch (NonexistentUserException ex){
            mav.addObject("errorMessage", 1);
            mav.setViewName("login");
            log.info("Logare esuata! Redirect catre aceeasi pagina!");
        }
        return mav;
    }

    //METODA GET PENTRU PAGINA DE REGISTER:
    @GetMapping("/register")
    public ModelAndView getRegisterPage(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("register");
        return mav;
    }

    //METODA POST PENTRU CREARE DE CONT USER:
    @PostMapping("/login")
    public ModelAndView addUser(RegisterDto userDto){
        ModelAndView mav = new ModelAndView();
        try{
            UserDto userDtoReturned = userService.addUser(userDto);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setBearerAuth(uuid);
            System.err.println(httpHeaders.getFirst(HttpHeaders.AUTHORIZATION));
            httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<UserDto> entity = new HttpEntity<>(userDtoReturned, httpHeaders);
            HttpStatus statusCode = restTemplate.exchange("http://localhost:8080/email/sendEmail", HttpMethod.POST, entity, UserDto.class).getStatusCode();
            System.out.println("Status request: "+ statusCode);

            mav.setViewName("login");
            log.info("Inregistrare a user-ului cu succes!");
        }
        catch (NotMatchingPasswordsException ex){
            mav.addObject("errorMessage", 1);
            mav.setViewName("register");
            log.info("Inregistrare esuata! Parolele nu sunt la fel!");
        }
        catch (ExistingUserException exx){
            mav.addObject("errorMessage", 2);
            mav.setViewName("register");
            log.info("Inregistrare esuata! User-ul este deja inregistrat!");
        }
        return mav;
    }

    //METODA GET PENTRU PAGINA PRINCIPALA A USER-ULUI:
    @GetMapping("/profile")
    public ModelAndView showProfile() {
        ModelAndView mav = new ModelAndView();
        if(loggedUserDto != null){
            mav.addObject("loggedUser", loggedUserDto);
            mav.setViewName("/profile");
        }
        else{
            mav.setViewName("redirect:/login");
            log.info("Niciun user logat! Redirect catre pagina de login!");
        }
        return mav;
    }

    //METODA POST PENTRU UPDATE USER PROFILE:
    @PostMapping("/profile/userMainPage")
    public ModelAndView editProfile(UserDto userDto){
        ModelAndView mav = new ModelAndView();
        if(loggedUserDto != null){
            UserDto newUserDto = userService.editProfile(userDto);
            mav.addObject("loggedUser", newUserDto);
            mav.setViewName("profile");
            loggedUserDto = newUserDto;
            log.info("Editarea user-ului s-a realizat cu succes!");
        }
        else{
            mav.setViewName("redirect:/login");
            log.info("Niciun user logat! Redirect catre pagina de login!");
        }
        return mav;
    }

    //METODA GET PENTRU PAGINA HISTORY A USER-ULUI:
    @GetMapping("/history")
    public ModelAndView showHistory() {
        ModelAndView mav = new ModelAndView();
        if(loggedUserDto != null){
            mav.setViewName("history");
        }
        else{
            mav.setViewName("redirect:/login");
            log.info("Niciun user logat! Redirect catre pagina de login!");
        }
        return mav;
    }

    //METODA GET PENTRU PAGINA DE VIZUALIZARE A MOVIE-URILOR PENTRU USER:
    @GetMapping("/movies")
    public ModelAndView showMovies() {
        ModelAndView mav = new ModelAndView();
        if(loggedUserDto != null){
            List<MovieDto> movieDtos = movieService.getAllMovies();
            mav.addObject("movies", movieDtos);
            mav.setViewName("movies");
            log.info("Filmele au fost selectate cu succes!");
        }
        else{
            mav.setViewName("redirect:/login");
            log.info("Niciun user logat! Redirect catre pagina de login!");
        }
        return mav;
    }

    //METODA POST PENTRU SEARCH MOVIES IN PAGINA USER-ULUI:
    @PostMapping("/movies")
    public ModelAndView searchMovies(MovieDto movieDto) {
        ModelAndView mav = new ModelAndView();
        if(loggedUserDto != null){
            List<MovieDto> movieDtos = movieService.filterMovies(movieDto);
            mav.addObject("movies", movieDtos);
            mav.setViewName("movies");
            log.info("Filmele au fost filtrare cu succes!");
        }
        else{
            mav.setViewName("redirect:/login");
        }
        return mav;
    }

    //METODA GET PENTRU VIZUALIZARE PAGINA PROGRAM CINEMA-URI DE CATRE USER:
    @GetMapping("/schedule")
    private ModelAndView goToSchedule(){
        ModelAndView mav = new ModelAndView();
        if(loggedUserDto != null){
            mav.setViewName("schedule");
            List<CinemaDto> cinemaDtos = cinemaService.getAllCinemas();
            mav.addObject("cinemas", cinemaDtos);
            log.info("Cinema-urile au fost selectate cu succes");
        }
        else{
            mav.setViewName("redirect:/login");
            log.info("Niciun user logat! Redirect catre pagina de login!");
        }
        return mav;
    }

    //METODA POST PENTRU SEARCH PROGRAM CINEMA-URI DE CATRE USER:
    @PostMapping("/schedule")
    private ModelAndView getCinemaSchedule(String cinemaName, String scheduleDate){
        ModelAndView mav = new ModelAndView();
        if(loggedUserDto != null){
            System.out.println(scheduleDate);
            List<CinemaDto> cinemaDtos = cinemaService.getAllCinemas();
            mav.addObject("cinemas", cinemaDtos);
            List<SessionDto> sessionDtos = cinemaService.getCinemaSessionsForThisDate(cinemaName, scheduleDate);
            //sessionDtos.stream().forEach(e -> System.err.println(e));
            mav.addObject("sessions", sessionDtos);
            mav.setViewName("schedule");
            log.info("Programul a fost selectat cu succes!");
        }
        else{
            mav.setViewName("redirect:/login");
            log.info("Niciun user logat! Redirect catre pagina de login!");
        }
        return mav;
    }

    //METODA GET DE LOGOUT:
    @GetMapping("/logout")
    public ModelAndView logout(){
        ModelAndView mav = new ModelAndView();
        loggedUserDto = null;
        mav.setViewName("redirect:/login");
        log.info("User-ul a fost delogat!");
        return mav;
    }

    //METODA ABOUT, NU FACE NIMIC MOMENTAN, NE VA DUCE TOT LA HISTORY:
    @GetMapping("/about")
    public ModelAndView seeAbout(){
        ModelAndView mav = new ModelAndView();
        if(loggedUserDto != null){
            mav.setViewName("history");
        }
        else{
            mav.setViewName("redirect:/login");
            log.info("Niciun user logat! Redirect catre pagina de login!");
        }
        return mav;
    }

}