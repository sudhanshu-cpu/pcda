package com.pcda.mb.travel.journey.model;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;

@Data
public class JourneyRequestDTO {

	private BigInteger loginUserId;
	private JourneyMainDTO journeyMainDTO;
	private List<JourneyDetailsDTO> journeyDetails;

}
