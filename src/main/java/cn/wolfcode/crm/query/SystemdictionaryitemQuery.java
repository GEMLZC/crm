package cn.wolfcode.crm.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class SystemdictionaryitemQuery extends QueryObject{
    private int parentId = -1;//父目录id
}
