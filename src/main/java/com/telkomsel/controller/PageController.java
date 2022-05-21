package com.telkomsel.controller;

import com.telkomsel.logger.Debug;
import com.telkomsel.utils.AuthUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.WebRequest;

import java.security.Principal;

@Controller
public class PageController {
  @GetMapping(path = {"/do/login", "/"})
  public String LoginPage(Principal principal, WebRequest wr, Model ui) {
    Debug.info(wr.toString());
    return "login";
  }

  @GetMapping(path = "/do/logout")
  public String LogoutPage(WebRequest wr){
    Debug.info(wr.toString());
    return "redirect:/";
  }

  @GetMapping(path = "/403")
  public String deniedPage(WebRequest wr){
    Debug.info(wr.toString());
    return "redirect:/403";
  }

  @GetMapping(path = "/404")
  public String notFoundedPage(WebRequest wr){
    Debug.info(wr.toString());
    return "redirect:/404";
  }

  @GetMapping(path = {"/{path}"})
  public String pageIndex(@PathVariable(name = "path") String path, Principal principal, WebRequest wr, Model ui) {
    System.out.println("path : /"+path);
    User auth = (User) ((Authentication) principal).getPrincipal();
    System.out.println("user : /"+auth.getUsername());
    System.out.println("pass : /"+auth.getPassword());
    return auth.getUsername();
//    String role = AuthUtils.getRole(auth);
//    String pages = "";
//    switch (path) {
//      case "dashboard" :
//        pages = "dashboard";
//        break;
//      case "faktur" :
//        pages = "faktur";
//        break;
//      case "pengembalian" :
//        pages = "pengembalian";
//        break;
//      case "pembayaran" :
//        pages = "pembayaran";
//        break;
//      case "mitra" :
//        pages = "mitra";
//        break;
//      case "mitra-new" :
//        pages = "mitra-add";
//        break;
//      case "pengaturan" :
//        pages = "pengaturan";
//        break;
//      default : return "redirect:/404";
//    }
//    ui.addAttribute("role", role);
//    ui.addAttribute("page", pages);
//    Debug.info(wr.toString());
//    return "index";
  }
}
