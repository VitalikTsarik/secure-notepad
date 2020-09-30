package com.delivery.dto;

import com.delivery.entity.Package;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageDto {
    private long id;

    @NotBlank
    private String name;

    private long weight;
    private long length;
    private long width;
    private long height;
    private long cost;
    private String initialLocation;
    private String targetLocation;

    public static PackageDto build(Package cargo) {
        return new PackageDto(
                cargo.getId(),
                cargo.getName(),
                cargo.getWeight(),
                cargo.getLength(),
                cargo.getWidth(),
                cargo.getHeight(),
                cargo.getCost(),
                cargo.getInitialLocation().getName(),
                cargo.getTargetLocation().getName()
        );
    }
}
