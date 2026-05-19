package com.flex.tender.model.embedded;

import java.util.UUID;

/**
 * @author Ruslan Timoshchuk
 */
public record PrincipalSummary(
        Integer userId,
        UUID principalUuid) {
}