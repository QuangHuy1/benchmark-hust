package vn.edu.benchmarkhust.model.request.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonSearchRequest {
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int DEFAULT_PAGE_INDEX = 1;
    private int pageIndex;
    private int pageSize;
    private String sortBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fromTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime toTime;

    public int getPageIndex() {
        return pageIndex == 0 ? DEFAULT_PAGE_INDEX : pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize == 0 ? DEFAULT_PAGE_SIZE : pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortByNature() {
        return sortBy;
    }

    public String getSortBy() {
        return StringUtils.isEmpty(sortBy) ? "createdDate:DESC" : sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public LocalDateTime getFromTime() {
        return fromTime;
    }

    public void setFromTime(LocalDateTime fromTime) {
        this.fromTime = fromTime;
    }

    public LocalDateTime getToTime() {
        return toTime;
    }

    public void setToTime(LocalDateTime toTime) {
        this.toTime = toTime;
    }

    @Override
    public String toString() {
        return "CommonFilterRequest{" +
                "pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", sortBy='" + sortBy + '\'' +
                ", fromTime=" + fromTime +
                ", toTime=" + toTime +
                '}';
    }
}