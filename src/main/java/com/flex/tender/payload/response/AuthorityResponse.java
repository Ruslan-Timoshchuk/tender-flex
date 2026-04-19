package com.flex.tender.payload.response;

/**
 * @author Ruslan Timoshchuk
 */
public record AuthorityResponse(
        Integer id, 
        String name, 
        String label) {
}