package top.jionjion.bean;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 *  @author Jion
 *      商品类目表
 */
@Entity //标注映射类,驼峰命名法映射匈牙利命名法
@Data   //提供所有属性get/set方法
@DynamicUpdate //动态更新,使用数据库字段自带的默认属性
public class ProductCategory {

    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    /** 类名名称 */
    @Column
    private String categoryName;

    /** 类目编号 */
    @Column
    private Integer categoryType;

    /** 创建时间 */
    @Column
    private Date createTime;

    /** 更新时间 */
    @Column
    private Date updateTime;
}
