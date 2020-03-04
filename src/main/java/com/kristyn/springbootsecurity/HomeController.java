package com.kristyn.springbootsecurity;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

import javax.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("cars", carRepository.findAll());
        return "index";
    }
    @GetMapping("/addcategory")
    public String newCategory(Model model) {

        model.addAttribute("category", new Category());

        return "categoryform";
    }

    @PostMapping("/processcategory")
    public String processCategory(@ModelAttribute Category category) {
        categoryRepository.save(category);
        return "redirect:/addcar";
    }

    @PostMapping("/updatecategory")
    public String updateCompany(@ModelAttribute Category category) {
        categoryRepository.save(category);
        return "redirect:/";
    }


   @GetMapping("/addcar")
    public String newCar(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("car", new Car());
        return "carform";
    }
    @PostMapping("/processcar")
    public String processCar(@ModelAttribute Car car, @RequestParam("file") MultipartFile file){
        if(file.isEmpty()){
            return "redirect:/carform";
        }
        try {
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));
            car.setPhoto(uploadResult.get("url").toString());
            carRepository.save(car);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/carform";
        }
        return "redirect:/";
    }

   /* @PostMapping("/processcar")
    public String processCar(@ModelAttribute Car car) {
        carRepository.save(car);
        return "redirect:/";
    }*/

    @RequestMapping("/search")
    public String search(@RequestParam("search") String search, Model model) {
        model.addAttribute("categorySearch", categoryRepository.findByNameIgnoreCase(search));
        model.addAttribute("carSearch1", carRepository.findCarByMakeIgnoreCase(search));
        model.addAttribute("carSearch2", carRepository.findCarByModelIgnoreCase(search));
        model.addAttribute("carSearch3", carRepository.findCarByYearIgnoreCase(search));
        return "list";
    }

    @RequestMapping("detail/{id}")
    public String showCategory(@PathVariable("id") long id, Model model) {
        model.addAttribute("category", categoryRepository.findById(id).get());
        return "showcategory";
    }

    @RequestMapping("detailc/{id}")
    public String showCar(@PathVariable("id") long id, Model model) {
        model.addAttribute("car", carRepository.findById(id).get());
        model.addAttribute("categories", categoryRepository.findAll());
        return "showcar";
    }

 /*   @RequestMapping("update/{id}")
    public String updateCategory(@PathVariable("id") long id, Model model) {
        model.addAttribute("category", categoryRepository.findById(id).get());
        return "categoryform";
    }*/

    @RequestMapping("updatec/{id}")
    public String updateCar(@PathVariable("id") long id, Model model) {
        model.addAttribute("car", carRepository.findById(id).get());
        model.addAttribute("categories", categoryRepository.findAll());
        return "carform";
    }

    @RequestMapping("delete/{id}")
    public String deleteCategory(@PathVariable("id") long id, Model model) {
        categoryRepository.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping("deletec/{id}")
    public String deleteCar(@PathVariable("id") long id, Model model) {
        carRepository.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping("/listcars")
    public String listCars(Model model) {
        model.addAttribute("cars", carRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "showcars";
    }
    @RequestMapping("/category/{id}")
    public String displayCat(@PathVariable("id") long id, Model model) {
        model.addAttribute("category", categoryRepository.findById(id).get());
        model.addAttribute("cars", carRepository.findAll());
        return "listcategory";
    }

    @GetMapping("/register")
       public String showRegistrationPage(Model model){
           model.addAttribute("user", new User());
           return "registration";
        }

     @PostMapping("/register")
     public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model){
        model.addAttribute("user", user);
        if(result.hasErrors()){
            return "registration";
        }
        else{
            userService.saveUser(user);
            model.addAttribute("message", "User Account Created");
        }

        return "index";
        }

   /* @RequestMapping("/")
    public String index(){
        return "index1";
    }*/
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/secure")
    public String secure(Principal principal, Model model){
        String username = principal.getName();
        model.addAttribute("user", userRepository.findByUsername(username));
        return "secure";
    }
}
