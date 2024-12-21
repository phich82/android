package com.java.sample.dto;


import java.io.Serializable;

public class YouTubePageInfo implements Serializable {

    private Integer totalResults;
    private Integer resultsPerPage;

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public void setResultsPerPage(Integer resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public Integer getResultsPerPage() {
        return resultsPerPage;
    }
}
