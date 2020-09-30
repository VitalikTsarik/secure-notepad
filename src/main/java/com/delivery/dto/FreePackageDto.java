package com.delivery.dto;

import com.delivery.entity.City;
import com.delivery.entity.Package;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FreePackageDto {
    private long id;
    private String name;
    private long weight;
    private long length;
    private long width;
    private long height;
    private long cost;
    private City initialLocation;
    private City targetLocation;
    private UserDto owner;

    public static FreePackageDto build(Package freePackage) {
        return new FreePackageDto(
                freePackage.getId(),
                freePackage.getName(),
                freePackage.getWeight(),
                freePackage.getLength(),
                freePackage.getWidth(),
                freePackage.getHeight(),
                freePackage.getCost(),
                freePackage.getInitialLocation(),
                freePackage.getTargetLocation(),
                UserDto.build(freePackage.getOwner())
        );
    }
}
