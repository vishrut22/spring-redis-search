package com.dcb.redissearch.document.repository;


import com.dcb.redissearch.document.domain.Page;
import com.dcb.redissearch.document.domain.Post;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.CommandArguments;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.search.*;
import redis.clients.jedis.search.aggr.AggregationBuilder;
import redis.clients.jedis.search.aggr.Reducers;
import redis.clients.jedis.timeseries.TSElement;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class PostRepository{

    @Autowired
    private UnifiedJedis client;

    private static final  int PAGE_SIZE = 20;


    public Post save(Post p){
        if(p.getPostId() == null) {
            p.setPostId(UUID.randomUUID().toString());
        }
        Gson gson = new Gson();
        client.jsonSet("post:"+p.getPostId(), gson.toJson(p));
        client.sadd("post", p.getPostId());
        return p;
    }

    public void deleteAll(){
        Set<String> postKeys = client.smembers("post");
        if(!postKeys.isEmpty()) {
            postKeys.stream().map(key -> "post:"+key).forEach(client::jsonDel);
        }
        client.del("post");
    }

    public Page search(String content, Set<String> tag , int pageNumber){
        int totalResult = 0;
        StringBuilder queryBuilder = new StringBuilder();
        if(content != null && !content.isBlank()) {
            queryBuilder.append("@content:"+content);
        }
        if(tag!= null  && !tag.isEmpty()){
            queryBuilder.append("@tags:{"+tag.stream().collect(Collectors.joining(","))+"}");
        }
        String queryCriteria = queryBuilder.toString();
        Query query = null;

        if(queryCriteria.isBlank()){
            query = new Query();
        } else{
            query = new Query(queryBuilder.toString());
         }
        int numberOfPage = (int) Math.ceil(((float) totalResult)/PAGE_SIZE);
        query.limit(PAGE_SIZE*(pageNumber-1), PAGE_SIZE);
        SearchResult searchResult = client.ftSearch("post-idx",query);
        List<Document> documentList = searchResult.getDocuments();
        List<Post> postList = documentList.stream()
               .map(this::convertDocumentToPost)
               .collect(Collectors.toList());
        return Page.builder()
                .currentPage(pageNumber)
                .totalPage(numberOfPage)
                .posts(postList)
                .total(searchResult.getTotalResults()).build();
    }

    public Post convertDocumentToPost(Document document){
        Gson gson = new Gson();
        String jsonDoc = document.getProperties().iterator().next().getValue().toString();
        return gson.fromJson(jsonDoc, Post.class);
    }

    public int getTotalPageCount(){
        return 0;
    }
}
