package com.dcb.redissearch.document.repository;

import com.dcb.redissearch.document.domain.Post;
import com.redis.om.spring.repository.RedisDocumentRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface PostRepository extends RedisDocumentRepository<Post, String> {

    List<Post> findByContent(String content);

}
