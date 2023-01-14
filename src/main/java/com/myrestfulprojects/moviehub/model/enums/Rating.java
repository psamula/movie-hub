package com.myrestfulprojects.moviehub.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;



@Getter

@JsonPropertyOrder
public enum Rating {
    @JsonProperty("1")
    @ApiModelProperty(value = "1", position = 1)
    ONE(1),
    @ApiModelProperty(value = "2", position = 2)
    @JsonProperty("2")
    TWO(2),
    @ApiModelProperty(value = "3", position = 3)
    THREE(3),
    @ApiModelProperty(value = "4", position = 4)
    FOUR(4),
    @ApiModelProperty(value = "5", position = 5)
    FIVE(5),
    @ApiModelProperty(value = "6", position = 6)
    SIX(6),
    @ApiModelProperty(value = "7", position = 7)
    SEVEN(7),
    @ApiModelProperty(value = "8", position = 8)
    EIGHT(8),
    @ApiModelProperty(value = "9", position = 9)
    NINE(9),
    @ApiModelProperty(value = "10", position = 10)
    TEN(10);
    private final int value;

    Rating(int value) {
        this.value = value;
    }
}
