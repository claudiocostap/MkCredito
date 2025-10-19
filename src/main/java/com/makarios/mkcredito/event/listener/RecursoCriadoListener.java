package com.makarios.mkcredito.event.listener;

import java.net.URI;

import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.makarios.mkcredito.event.RecursoCriadoEvent;

import jakarta.servlet.http.HttpServletResponse;


@Component
public class RecursoCriadoListener {

    @EventListener
    public void handleRecursoCriadoEvent(RecursoCriadoEvent recursoCriadoEvent) {
        HttpServletResponse response = recursoCriadoEvent.getResponse();
        Long id = recursoCriadoEvent.getId();

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(id).toUri();
        response.setStatus(HttpStatus.CREATED.value());
        response.setHeader("Location", uri.toASCIIString());
    }

}