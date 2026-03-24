package com.pcda.mb.requestdashboard.offlinecancellationdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetOfflineCanDataModel {
private String personalNo;
private List<OfflineRequestBean> offBeanList;	
}
