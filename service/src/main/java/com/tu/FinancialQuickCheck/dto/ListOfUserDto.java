package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.db.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListOfUserDto {

    public List<UserDto> users;

//    public ListOfUserDto(){}

    public ListOfUserDto(Iterable<UserEntity> userEntities){

        this.users = new ArrayList<>();

        for (UserEntity tmp : userEntities) {
            this.users.add(new UserDto(UUID.fromString(tmp.id), tmp.email, tmp.username));
        }
    }

}