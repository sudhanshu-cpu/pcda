package com.pcda.common.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class GrievanceCategoryBean {
	private String grievanceCategoryId;
    private String modulename;
    private Integer categoryType;
    private List<GrievanceCategoryBean> childmodule=new ArrayList<>();
}
