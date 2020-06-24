package top.jionjion.service;


import top.jionjion.bean.ProductCategory;

import java.util.List;

/**
 *  @author Jion
 *      商品类目
 */
public interface ProductCategoryService {

    /** 主键查询 */
    ProductCategory findOne(Integer categoryId);

    /** 查询全部 */
    List<ProductCategory> findAll();

    /** 通过类型查询 */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    /** 新增和更新 */
    ProductCategory save(ProductCategory productCategory);
}
