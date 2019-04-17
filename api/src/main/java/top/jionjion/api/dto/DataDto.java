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
    private List<Object> data = new ArrayList<Object>();

    /** 数据总数 */
    private Integer dateCount;

    public DataDto(Object data){
        if(data instanceof java.util.List){
            (this.data).addAll((List) data);
        }else{
            (this.data).add(data);
        }
        this.dateCount = this.data.size();
    }
}
