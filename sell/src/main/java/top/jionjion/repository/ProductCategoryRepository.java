package top.jionjion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.jionjion.bean.ProductCategory;

import java.util.List;

/**
 *  @author Jion
 *
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {

    /** 通过主键查询 */
    ProductCategory findByCategoryId(Integer categoryId);

    /** 通过类匹配查询结果集 */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

}