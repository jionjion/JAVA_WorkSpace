package top.jionjion.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jion
 *  数据包装类
 */
@Getter
@Setter
public class DataDto {

    /** 数据,使用List包装 */
    private List<Object> record = new ArrayList<>();

    /** 数据总数 */
    private Integer totalCount;

    public DataDto(Object data){
        if(data instanceof java.util.List){
            (this.record).addAll((List) data);
        }else{
            (this.record).add(data);
        }
        this.totalCount = this.record.size();
    }
}
