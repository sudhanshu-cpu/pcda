package com.pcda.mb.travel.journey.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TrainSearchResponseBean {

	@JsonProperty("trainBtwnStnsList")
	private List<Trainbtwnstnslist> trainbtwnstnslist;

	@JsonProperty("quotaList")
	private List<String> quotalist;
	@JsonProperty("serverId")
	private String serverid;
	@JsonProperty("timeStamp")
	private Date timestamp;
	@JsonProperty("vikalpInSpecialTrainsAccomFlag")
	private String vikalpinspecialtrainsaccomflag;
	@JsonProperty("oneStopJourny")
	private String onestopjourny;
	@JsonProperty("serveyFlag")
	private String serveyflag;
	@JsonProperty("alternateEnquiryFlag")
	private String alternateenquiryflag;

	private String errorMessage;
	private String errorCode;

}
