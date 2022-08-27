package com.cryptotracker.util;

import com.cryptotracker.dto.PaginatedResponse;
import lombok.NonNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@Component
class PaginatedResultsRetrievedEventDiscoverabilityListener implements ApplicationListener<PaginatedEvent> {

    @Override
    public void onApplicationEvent(final @NonNull PaginatedEvent ev) {
        addLinkHeaderOnPagedResourceRetrieval(ev.getUrl(),  ev.getResponse(), ev.getDate(), ev.getOffset() , ev.getLimit() );
    }

    void addLinkHeaderOnPagedResourceRetrieval(final UriComponentsBuilder uriBuilder, final PaginatedResponse response,
                                               final LocalDate date, final int offset, final int limit) {

        response.setUrl( constructUri(uriBuilder,date, offset, limit));
        if(response.getCount() >  offset+limit){
            response.setNext( constructNextPageUri(uriBuilder,date, offset, limit ));
        }
    }

    String constructNextPageUri(final UriComponentsBuilder uriBuilder, final LocalDate date, final int offset, final int limit) {
        return uriBuilder.replaceQueryParam("date",  date).replaceQueryParam("offset",  offset+limit)
                .replaceQueryParam("limit", limit).build().encode().toUriString();
    }

    String constructUri(final UriComponentsBuilder uriBuilder, final LocalDate date, final int offset, final int limit) {
        return uriBuilder.replaceQueryParam("date",  date).replaceQueryParam("offset",  offset)
                .replaceQueryParam("limit", limit).build().encode().toUriString();
    }


}