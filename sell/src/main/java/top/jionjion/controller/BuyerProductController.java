package top.jionjion.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jionjion.bean.ProductCategory;
import top.jionjion.bean.ProductInfo;
import top.jionjion.service.ProductCategoryService;
import top.jionjion.service.ProductInfoService;
import top.jionjion.vo.ProductInfoVO;
import top.jionjion.vo.ProductVO;
import top.jionjion.vo.ResultVO;
import utils.ResultVOUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *  @author Jion
 *      买家相关
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    /** 商品 */
    @Autowired
    private ProductInfoService productInfoService;

    /** 类目 */
    @Autowired
    private ProductCategoryService productCategoryService;


    /**
     *  请求返回列表
     */
    @GetMapping("/list")
    public ResultVO list(){

        //1.查询所有上架的商品信息
        List<ProductInfo> productInfoList = productInfoService.findUpAll();

        //2.查询上架商品的所属类目
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        //3.查询类目信息
        List<ProductCategory> productCategoryList =
                productCategoryService.findByCategoryTypeIn(categoryTypeList);
        //4.将商品放置在类目下面,进行拼装
        List<ProductVO> productVOList = new ArrayList<>();
        //遍历商品类目
        for (ProductCategory productCategory : productCategoryList){
            //类目
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());
            //数据
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            //遍历商品详情
            for (ProductInfo productInfo : productInfoList){
                //外层类目循环类目等于内层商品类目,拷贝属性
                if(Objects.equals(productCategory.getCategoryType(),productInfo.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    //将对象拷贝属性
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            //添加商品信息
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        //返回结果
        return ResultVOUtil.success(productVOList);
    }
}
