package com.pcda.mb.travel.journey.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Clusterstationlist {

    @JsonProperty("stnNameCode")
    private String stnnamecode;
    @JsonProperty("enRoutePoint")
    private String enroutepoint;
    private String via1;
    private String distance;
    
}