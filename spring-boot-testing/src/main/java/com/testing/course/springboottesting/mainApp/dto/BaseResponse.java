package com.testing.course.springboottesting.mainApp.dto;

import lombok.*;

import java.util.UUID;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BaseResponse {
    protected UUID uuid;
}
