package com.dcb.redissearch.service;

import com.dcb.redissearch.model.CategoryStats;
import com.dcb.redissearch.model.Page;
import com.dcb.redissearch.document.domain.Post;
import com.dcb.redissearch.document.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PostServiceImpl {

    @Autowired
    private PostRepository postRepository;
    public List<Post> search(String param) {
        return null;
    }

    public Page filter(String content, Set<String> tags, int page) {
        return postRepository.search(content,tags,page);
    }

    public List<CategoryStats> getCategoryWiseTotalPost() {
        return postRepository.getCategoryWiseTotalPost();
    }
}
