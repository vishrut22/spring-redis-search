package com.dcb.redissearch.controller;

import com.dcb.redissearch.document.domain.Post;
import com.dcb.redissearch.service.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
    public List<Post> filter(@RequestParam(name="search") String search , @RequestParam(name="tags") Set<String> tags) {
        return postService.filter(search,tags);
    }

}
