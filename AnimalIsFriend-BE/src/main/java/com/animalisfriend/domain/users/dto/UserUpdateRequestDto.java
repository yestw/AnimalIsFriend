package com.animalisfriend.domain.users.dto;

import lombok.*;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {

    private String userName;
    private String userHp;
    private String userAddress;
    private String imageUrl;

    @Builder
    public UserUpdateRequestDto(String userName, String userHp, String userAddress, String imageUrl) {
        this.userName = userName;
        this.userHp= userHp;
        this.userAddress = userAddress;
        this.imageUrl = imageUrl;
    }
}
