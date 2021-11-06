package com.feign.handling.feignerrorshandling.error;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class ExceptionControllerAdvice {

    private final ObjectMapper mapper;

    @ExceptionHandler(FeignException.class)
    public final ResponseEntity<String> handleFeignException(FeignException fex) {
        log.error("Exception from downstream service call - ", fex);
        HttpStatus status = Optional.ofNullable(HttpStatus.resolve(fex.status()))
                .orElse(HttpStatus.INTERNAL_SERVER_ERROR);
        String body = Strings.isNullOrEmpty(fex.contentUTF8()) ? fex.getMessage() : fex.contentUTF8();
        return new ResponseEntity<>(
                body,
                getHeadersWithContentType(body),
                status
        );
    }

    private MultiValueMap<String, String> getHeadersWithContentType(String body) {
        HttpHeaders headers = new HttpHeaders();
        String contentType = isValidJSON(body) ? "application/json" : "text/plain";
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
        return headers;
    }

    private boolean isValidJSON(String body) {
        try {
            if (Strings.isNullOrEmpty(body)) return false;
            mapper.readTree(body);
            return true;
        } catch (JacksonException e) {
            return false;
        }
    }
}