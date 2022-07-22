package com.dcb.redissearch.service;

import com.dcb.redissearch.document.domain.Post;
import com.dcb.redissearch.document.domain.Post$;
import com.dcb.redissearch.document.repository.PostRepository;
import com.redis.om.spring.search.stream.EntityStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private EntityStream entityStream;

    public List<Post> search(String param) {
        return postRepository.findByContent(param);
    }

    public List<Post> filter(String content, Set<String> tags) {

        return entityStream.of(Post.class)
                .filter(Post$.CONTENT.eq(content))
                .filter(Post$.TAGS.eq(tags))
                .collect(Collectors.toList());
    }
}
