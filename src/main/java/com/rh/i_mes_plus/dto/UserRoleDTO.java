package com.rh.i_mes_plus.dto;

import com.rh.i_mes_plus.model.ums.UmsUser;
import lombok.Data;

import java.util.List;

@Data
public class UserRoleDTO extends UmsUser {
   private List<Long> roleIds;
}
