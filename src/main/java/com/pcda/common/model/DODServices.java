package com.pcda.common.model;

import java.util.Date;

import com.pcda.util.PermissionType;
import com.pcda.util.YesOrNo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DODServices {

	private String serviceId;

	private Date creationTime;

	private String status;

	private Date lastModTime;

	private Long cratedBy;

	private Long lastModBy;

	private Long approvedBy;

	private String approvalState;

	private String serviceName;

	private String serviceDescription;

	private String remark;

	private YesOrNo armedForces;

	private PermissionType permissionType;

}
