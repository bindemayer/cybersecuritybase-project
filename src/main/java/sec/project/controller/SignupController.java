package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String address) {
        signupRepository.save(new Signup(name, address));
        return "done";
    }
    
    @RequestMapping(value = "/remove/{name}", method = RequestMethod.POST)
    public String remove(@PathVariable String name) {
        if(name != null)
        {
           Signup remove = signupRepository.findByName(name);
           signupRepository.delete(remove);
           
        }
        return "redirect:/manage";
    }
    
    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public String manageForm(Model model) {
        model.addAttribute("signups", signupRepository.findAll());
        return "signuplist";
    }
    
    @RequestMapping(value = "/delete/{name}", method = RequestMethod.GET)
    public String deleteConfirmation(Model model, @PathVariable String name) {
        if (name != null)
        {
            model.addAttribute("signup", signupRepository.findByName(name));
            return "deleteConfirm";
        }
        else
        {
            return"redirect:/form";
        }
    }

}
