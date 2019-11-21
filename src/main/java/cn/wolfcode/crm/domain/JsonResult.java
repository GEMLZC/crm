package cn.wolfcode.crm.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JsonResult {
    private String message;
    private boolean success = true;

    /**
     * 默认创建无参构造的对象就是成功了
     */
    public JsonResult(){}

    /**
     * 如果设置的message说明返回失败的JsonResult内容
     * @param message
     */
    public void mark(String message){
        this.message = message;
        this.success = false;
    }


}
