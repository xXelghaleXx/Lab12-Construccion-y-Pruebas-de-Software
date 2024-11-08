package com.tecsup.petclinic.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Visit Transfer Object
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VisitTO {

    private Integer id;

    private Integer petId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date visitDate;

    private String description;
}
