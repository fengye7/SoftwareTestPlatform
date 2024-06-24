package com.tongji.microservice.teamsphere.dto.userservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginResponse extends APIResponse implements Serializable {
    private int userid;

    public LoginResponse(APIResponse apiResponse, int userid) {
        super(apiResponse.getCode(), apiResponse.getMessage());
        this.userid = userid;
    }
    public LoginResponse(APIResponse apiResponse){
        super(apiResponse.getCode(), apiResponse.getMessage());
        this.userid=0;
    }
}

