package com.dcb.redissearch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CategoryStats {
    private String tag;
    private long totalPosts;
    private String averageViews;
}
