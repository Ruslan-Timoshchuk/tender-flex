package pl.com.tenderflex.payload;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Page<T> {

    private final Integer currentPage;
    private final Integer totalPages;
    private final List<T> content;

}