package com.pcda.common.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RailSearchResponse {

	private String errorMessage;
    private int errorCode;
    private List<String> responseList;
}
