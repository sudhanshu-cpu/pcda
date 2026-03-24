package com.pcda.common.model;

import java.util.List;

import lombok.Data;

@Data
public class GrievanceCategoryModel {

    private String modulename;
    private List<ChildModel> childmodule;
   
}
