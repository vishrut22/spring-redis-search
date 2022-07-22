package com.dcb.redissearch.document.domain;

import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;
import com.redis.om.spring.annotations.Searchable;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Document("test")
@Data
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id
    private String id;

    @NonNull
    @Searchable(sortable = true)
    private String content;

    @Indexed
    private Set<String> tags = new HashSet<String>();

    @NonNull
    private String url;

    @NonNull
    @Indexed
    private Integer mostViewed;

    @NonNull
    @Indexed
    private Date dateCreated;

}
