package com.tales.leite.commons.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Example {

    private Long idFilter;
    private String filterString;
    private Integer filterNumber;
    private Double filterDouble;
    private Date filterDate;


    public String mensagem() {
        return "Hello Pala";
    }
}
