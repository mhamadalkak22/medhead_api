package com.medhead.medhead.ResponseObjects;

import com.medhead.medhead.entities.AppUser;

public class GetUserByUsernameResponse extends BaseResponse{

    private AppUser user;

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
    
}
