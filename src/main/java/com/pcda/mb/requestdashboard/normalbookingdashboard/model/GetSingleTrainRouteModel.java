package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetSingleTrainRouteModel {



    @JsonProperty("stationCode")
    private String stationcode;
    @JsonProperty("stationName")
    private String stationname;
    @JsonProperty("arrivalTime")
    private String arrivaltime;
    @JsonProperty("departureTime")
    private String departuretime;
    @JsonProperty("routeNumber")
    private String routenumber;
    @JsonProperty("haltTime")
    private String halttime;
    private String distance;
    @JsonProperty("dayCount")
    private String daycount;
    @JsonProperty("stnSerialNumber")
    private String stnserialnumber;
    @JsonProperty("boardingDisabled")
    private String boardingdisabled;
    


}
