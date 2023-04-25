package com.verychicpoc.dto;

import java.util.List;
import java.util.Map;

public class SearchHitsOverview {
    public Map<String, String> total;
    public float max_score;
    public List<SearchHit> hits;
}
