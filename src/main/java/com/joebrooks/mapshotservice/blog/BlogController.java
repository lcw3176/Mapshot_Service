package com.joebrooks.mapshotservice.blog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blog")
public class BlogController {

    @GetMapping
    public String showBlog(){

        return "blog/index";
    }

    @GetMapping("/{postName}/")
    public String showContent(@PathVariable("postName") String postName){

        return "blog/" + postName + "/index";
    }

}


