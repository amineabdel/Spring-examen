package edu.ap.spring.tml.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import edu.ap.spring.redis.RedisService;

@Controller
public class TMLController {

   @Autowired
   private RedisService service;

   @GetMapping("/new")
   public String getCreateForm() {
      return "newRecipe";
   }

   @PostMapping("new")
   public String postRecipe(@RequestParam("name") String name, @RequestParam("ingredients") String ingredients,
         Model m) {

      if (!service.exists("*recipe*")) {
         service.setKey("recipe", name + "|" + ingredients);
         System.out.println("if");

      } else {
         service.getKey("recipe");
         System.out.println("else");
      }

      service.setKey("recipe", name + "|" + ingredients);
      service.getKey("recipe");

      /*
       * List<String> recipeList = new ArrayList<String>(); Set<String> names =
       * service.keys("recipe:*"); for (String n : names) { String rec = "";
       * Map<Object, Object> recipe = service.hgetAll(n); String[] parts =
       * n.split(" "); System.out.println(parts); }
       */

      // ik kan als ik een recept aanmaak, dat het zichtbaar is in de console
      System.out.println(service.getKey("recipe"));
      return "newRecipe";

   }

   @GetMapping("/searchRecipe")
   public String getSearch() {
      return "searchRecipe";
   }

   @GetMapping("/searchRecipe/{name}")
   public String getSearchForm(@PathVariable("name") String name, Model m) {
      m.addAttribute("recipe", service.getKey("recipe"));
      return "redirect:/searchRecipe";
   }

   @PostMapping("/search")
   public String postSearch(@RequestParam("name") String name, Model m) {
      service.setKey("recipe", name);
      return "redirect:/searchRecipe/" + name;
   }

}
