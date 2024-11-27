package pl.com.tenderflex.cpv.payload;

import pl.com.tenderflex.model.Cpv;

public record CpvResponse(Integer id, String code, String description) {

    public CpvResponse(Cpv cpv) {
        this(cpv.getId(), cpv.getCode(), cpv.getDescription());
    }

}