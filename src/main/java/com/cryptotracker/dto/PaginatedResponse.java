package com.cryptotracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

@Getter
@Setter
@ToString
public class PaginatedResponse<T> {

    private String url;
    private String next;
    private long count;
    private T data;
}
