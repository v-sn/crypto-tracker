package com.cryptotracker.util;

import com.cryptotracker.dto.PaginatedResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public final class PaginatedEvent<T extends Serializable> extends ApplicationEvent {
    private UriComponentsBuilder url;
    private PaginatedResponse<T> response;
    private LocalDate date;
    private int offset;
    private int limit;

    public PaginatedEvent(final Class<T> clazz, UriComponentsBuilder url,
                          final PaginatedResponse<T> response, final  LocalDate date, final int offset,
                          final int limit ) {
        super(clazz);
        this.url = url;
        this.response = response;
        this.offset = offset;
        this.limit = limit;
    }



    /**
     * The object on which the Event initially occurred.
     *
     * @return The object on which the Event initially occurred.
     */
    @SuppressWarnings("unchecked")
    public final Class<T> getClazz() {
        return (Class<T>) getSource();
    }

}