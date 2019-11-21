package cn.wolfcode.crm.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee extends BaseDomain{
    private String name;

    private String password;

    private String email;

    private Integer age;

    private boolean admin;

    private Department dept;

    private List<Role> roles = new ArrayList<>();


}