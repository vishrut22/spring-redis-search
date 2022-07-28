package com.dcb.redissearch.model;

import com.dcb.redissearch.document.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Page {
    private List<Post> posts;
    private int totalPage;
    private int currentPage;
    private long total;
}
