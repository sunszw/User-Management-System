package com.ssmsun.management.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
public class User implements Serializable {
    private static final long serialVersionUID = -1355048640747066095L;
    private Integer userid;
    private String username;
    private String password;
    private String salt;
    private String email;
    private String phone;
    private String avatar;
    private Integer gender;
    private Double amount;
    private LocalDateTime credate;
    private Integer confirm;
    private Integer vip;


}
