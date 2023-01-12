package com.myrestfulprojects.moviehub.model;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
@Component

public class PaginationProvider {
    public <T> List<T> getElementsByPage(List<T> elements, int page, int pageSize) {
        int startIndex = (page) * pageSize;
        int endIndex = startIndex + pageSize;
        if (startIndex < elements.size()) {
            if (endIndex > elements.size()) {
                endIndex = elements.size();
            }
            return elements.subList(startIndex, endIndex);
        }
        return Collections.emptyList();
    }
}
