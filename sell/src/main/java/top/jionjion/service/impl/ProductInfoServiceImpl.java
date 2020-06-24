package top.jionjion.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import top.jionjion.bean.ProductInfo;
import top.jionjion.dto.CartDTO;
import top.jionjion.enums.ProductStatusEnum;
import top.jionjion.enums.ResultStatusEnum;
import top.jionjion.exception.SellException;
import top.jionjion.repository.ProductInfoRepository;
import top.jionjion.service.ProductInfoService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * @author Jion
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        return repository.findByProductId(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        //正在货架的
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public void increaseStock(List<CartDTO> cartDTOList) {

    }

    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public void decreaseStock(List<CartDTO> cartDTOList) {
        //遍历购物车
        for(CartDTO cartDTO : cartDTOList){
            //查询商品,是否存在
            ProductInfo productInfo = repository.findByProductId(cartDTO.getProductId());
            if(Objects.isNull(productInfo)){
                throw new SellException(ResultStatusEnum.PRODUCT_NOT_EXIST);
            }
            //减库存 结果 = 商品库存 - 购物车商品选择数量
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            //库存不足
            if(result < 0){
                throw new SellException(ResultStatusEnum.PRODUCT_STOCK_ERROR);
            }
            //修改库存并保存
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }
}
