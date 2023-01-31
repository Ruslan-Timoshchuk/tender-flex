package pl.com.tenderflex.dto;

import java.util.List;

public class Page<T> {

    private final Integer currentPage;
    private final Integer totalPages;
    private final List<T> content;

    public Page(Integer currentPage, Integer totalPages, List<T> content) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.content = content;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public List<T> getContent() {
        return content;
    }
}