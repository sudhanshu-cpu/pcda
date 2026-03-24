package com.pcda.adg.reports.bookingreport.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExcelDataList {

	List<GetBookingRepoModel> modelList;
}
