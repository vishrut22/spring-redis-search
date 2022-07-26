package com.dcb.redissearch.controller;

import com.dcb.redissearch.document.domain.Page;
import com.dcb.redissearch.document.domain.Post;
import com.dcb.redissearch.service.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/")
public class SearchController {
    @Autowired
    private PostServiceImpl postService;

    @GetMapping("/search")
    public List<Post> search(@RequestParam(name="search") String search) {
        return postService.search(search);
    }

    @GetMapping("/filter")
    public Page filter(@RequestParam(name="search" , required = false) String search ,
                       @RequestParam(name="tags" , required = false) Set<String> tags,
                       @RequestParam(name = "page" , defaultValue = "1") int page) {
        return postService.filter(search,tags ,page);
    }

    @GetMapping("/aggregate")
    public Map<String,String> aggregate() {
        return postService.aggregate();
    }

}
