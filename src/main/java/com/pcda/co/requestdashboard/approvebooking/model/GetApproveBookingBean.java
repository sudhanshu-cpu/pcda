package com.pcda.co.requestdashboard.approvebooking.model;

import java.util.List;
import java.util.Set;

import lombok.Data;

@Data
public class GetApproveBookingBean {

private List<GetApproveRailBookingBean>  railRequests;
private List<GetApproveAirBookingBean>  airRequests;
private Set<String> personalNoSet;
}
