package com.medhead.medhead.ResponseObjects;

import java.util.List;

import com.medhead.medhead.entities.AppUser;

public class GetInvalidAccountsResponse extends BaseResponse {
   
    private List<AppUser> invalidUsers;

    public List<AppUser> getInvalidUsers() {
        return invalidUsers;
    }

    public void setInvalidUsers(List<AppUser> invalidUsers) {
        this.invalidUsers = invalidUsers;
    }    

  

   

}
