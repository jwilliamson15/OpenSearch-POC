package com.verychicpoc.dto;

import java.util.Map;

public class OpenSearchResponse {
    public int took;
    public boolean timed_out;
    public Map<String,String> _shards;
    public SearchHitsOverview hits;
}
