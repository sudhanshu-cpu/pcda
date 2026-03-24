package com.pcda.common.model;

import com.pcda.util.AllowedJourneyType;
import com.pcda.util.FromToType;
import com.pcda.util.JourneyDestinationType;

import lombok.Data;

@Data
public class TRJourney {

	private Integer sequanceNo;

	private AllowedJourneyType jrnyType;

	private FromToType fromToType;

	private JourneyDestinationType jrnyStation;

}
