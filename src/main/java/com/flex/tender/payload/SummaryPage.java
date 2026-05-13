package com.flex.tender.payload;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SummaryPage<T> {

    private final Integer page;
    private final Integer pages;
    private final List<T> content;

}