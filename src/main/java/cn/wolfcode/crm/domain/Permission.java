package cn.wolfcode.crm.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Permission extends BaseDomain{

    private String name;

    private String expression;




}