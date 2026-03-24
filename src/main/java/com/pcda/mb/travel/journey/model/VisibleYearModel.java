package com.pcda.mb.travel.journey.model;

import lombok.Data;

@Data
public class VisibleYearModel {

	private String yearCurrent="";
	private String yearNext="";
	private String previousYear="";
	private String previousBlockYear="";
	private String currentBlockYear="";
	private String nextBlockYear="";
	private String currentSubBlockYear="";
	private String previousSubBlockYear="";
	private String nextSubBlockYear="";
	private String bothBlockAreSame="";
	private String schemeType="";
}
