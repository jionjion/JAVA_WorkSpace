package top.jionjion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.jionjion.bean.ProductInfo;

import java.util.List;

/**
 *  @author Jion
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String>{

    /** 通过主键查询 */
    ProductInfo findByProductId(String productId);

    /** 查询商家商品 */
    List<ProductInfo> findByProductStatus(Integer productStatus);
}
