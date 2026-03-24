package com.pcda.mb.adduser.changepersonalno.model;


import java.math.BigInteger;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostOldPersonalNo {

private String  dob;
private String  enrollmentDate;
private BigInteger loginUserId=BigInteger.ZERO;
}
