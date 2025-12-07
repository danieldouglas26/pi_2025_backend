package com.lixo.gerenciamento.model.dto.response;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageResponseDTO<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isLast;

    public PageResponseDTO() {
    }

    public PageResponseDTO(List<T> content, int pageNumber, int pageSize, 
                          long totalElements, int totalPages, boolean isLast) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.isLast = isLast;
    }

    public PageResponseDTO(Page<T> page) {
        this.content = page.getContent();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.isLast = page.isLast();
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLast() {
        return isLast;
    }

    public boolean getIsLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public void setIsLast(boolean isLast) {
        this.isLast = isLast;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        PageResponseDTO<?> that = (PageResponseDTO<?>) o;
        
        if (pageNumber != that.pageNumber) return false;
        if (pageSize != that.pageSize) return false;
        if (totalElements != that.totalElements) return false;
        if (totalPages != that.totalPages) return false;
        if (isLast != that.isLast) return false;
        return content != null ? content.equals(that.content) : that.content == null;
    }

    @Override
    public int hashCode() {
        int result = content != null ? content.hashCode() : 0;
        result = 31 * result + pageNumber;
        result = 31 * result + pageSize;
        result = 31 * result + (int) (totalElements ^ (totalElements >>> 32));
        result = 31 * result + totalPages;
        result = 31 * result + (isLast ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PageResponseDTO{" +
                "content=" + (content != null ? content.size() : 0) + " items" +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                ", isLast=" + isLast +
                '}';
    }

    public boolean isEmpty() {
        return content == null || content.isEmpty();
    }

    public boolean hasNext() {
        return pageNumber < totalPages - 1;
    }

    public boolean hasPrevious() {
        return pageNumber > 0;
    }

    public boolean isFirst() {
        return pageNumber == 0;
    }

    public static <T> PageResponseDTO<T> fromPage(Page<T> page) {
        return new PageResponseDTO<>(page);
    }

    public static <T> PageResponseDTO<T> empty() {
        return new PageResponseDTO<>(Collections.emptyList(), 0, 0, 0, 0, true);
    }

    public static <T> PageResponseDTO<T> singlePage(List<T> content) {
        if (content == null) {
            content = Collections.emptyList();
        }
        return new PageResponseDTO<>(
            content, 
            0, 
            content.size(), 
            content.size(), 
            1, 
            true
        );
    }
}