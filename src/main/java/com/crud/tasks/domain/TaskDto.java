package com.crud.tasks.domain;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
//@Data
public class TaskDto {

    private Long id;
    private String title;
    private String content;

}
