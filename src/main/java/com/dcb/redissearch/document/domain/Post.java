package com.dcb.redissearch.document.domain;

import lombok.*;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private String postId;

    @NonNull
    //@Searchable(sortable = true)
    private String title;

    @NonNull
    private String content;

    private Set<String> tags = new HashSet<String>();


    @NonNull
    private Integer mostViewed;
}
