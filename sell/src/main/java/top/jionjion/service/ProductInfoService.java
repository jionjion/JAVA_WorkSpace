package top.jionjion.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import top.jionjion.bean.ProductInfo;
import top.jionjion.dto.CartDTO;

import java.util.List;

/**
 *  @author Jion
 *      商品服务
 */
public interface ProductInfoService {

    ProductInfo findOne(String productId);

    /** 查询在货架中的商品 */
    List<ProductInfo> findUpAll();

    /** 分页查询全部商品 */
    Page<ProductInfo> findAll(Pageable pageable);

    /** 更新或者保存 */
    ProductInfo save(ProductInfo productInfo);

    /** 加库存 */
    void increaseStock(List<CartDTO> cartDTOList);

    /** 减库存 */
    void decreaseStock(List<CartDTO> cartDTOList);
}
