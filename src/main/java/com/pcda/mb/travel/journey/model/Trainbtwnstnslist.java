package com.pcda.mb.travel.journey.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Trainbtwnstnslist {

	@JsonProperty("trainNumber")
	private String trainnumber;
	@JsonProperty("trainName")
	private String trainname;
	@JsonProperty("fromStnCode")
	private String fromstncode;
	@JsonProperty("toStnCode")
	private String tostncode;
	@JsonProperty("arrivalTime")
	private String arrivaltime;
	@JsonProperty("departureTime")
	private String departuretime;
	private String distance;
	private String duration;
	@JsonProperty("runningMon")
	private String runningmon;
	@JsonProperty("runningTue")
	private String runningtue;
	@JsonProperty("runningWed")
	private String runningwed;
	@JsonProperty("runningThu")
	private String runningthu;
	@JsonProperty("runningFri")
	private String runningfri;
	@JsonProperty("runningSat")
	private String runningsat;
	@JsonProperty("runningSun")
	private String runningsun;
	@JsonProperty("avlClasses")
	private List<String> avlclasses;
	@JsonProperty("trainType")
	private List<String> traintype;
	@JsonProperty("atasOpted")
	private String atasopted;
	@JsonProperty("flexiFlag")
	private String flexiflag;
	@JsonProperty("trainOwner")
	private String trainowner;
	@JsonProperty("trainsiteId")
	private String trainsiteid;
	private String runsOn;

}