package com.pcda.mb.travel.bulkBkg.model;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;

@Data
public class JourneyRequestBulkBkgDTO {
	
	private BigInteger loginUserId;
	private JourneyMainBulkBkgDTO journeyMainDTO;
	private List<JourneyDetailsBulkBkgDTO> journeyDetails;

}
