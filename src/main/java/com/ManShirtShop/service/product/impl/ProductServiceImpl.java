package com.ManShirtShop.service.product.impl;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.InvalidClassException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.checkerframework.checker.units.qual.C;
import org.checkerframework.checker.units.qual.s;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.ManShirtShop.common.mapperUtil.ObjectMapperUtils;
import com.ManShirtShop.common.mapperUtil.ResponseFormat;
import com.ManShirtShop.dto.ProductDetail_Dto.ProductDetailRequest;
import com.ManShirtShop.dto.product.ProductFilterRequest;
import com.ManShirtShop.dto.ProductDetail_Dto.ProductDetailResponse;
import com.ManShirtShop.dto.product.ProductReponse;
import com.ManShirtShop.dto.product.ProductRequest;
import com.ManShirtShop.dto.product_Detail_Image_Dto.ProductAllRequest;
import com.ManShirtShop.dto.product_Image_dto.ProductImageRequest;
import com.ManShirtShop.dto.product_Image_dto.Status;
import com.ManShirtShop.entities.Category;
import com.ManShirtShop.entities.Collar;
import com.ManShirtShop.entities.Color;
import com.ManShirtShop.entities.Design;
import com.ManShirtShop.entities.Discount;
import com.ManShirtShop.entities.Form;
import com.ManShirtShop.entities.Material;
import com.ManShirtShop.entities.Product;
import com.ManShirtShop.entities.ProductDetail;
import com.ManShirtShop.entities.ProductImage;
import com.ManShirtShop.entities.Size;
import com.ManShirtShop.entities.Sleeve;
import com.ManShirtShop.repository.CategoryRepository;
import com.ManShirtShop.repository.CollarRepository;
import com.ManShirtShop.repository.ColorRepository;
import com.ManShirtShop.repository.DesignRepository;
import com.ManShirtShop.repository.DiscountRepository;
import com.ManShirtShop.repository.FormRepository;
import com.ManShirtShop.repository.MaterialRepository;
import com.ManShirtShop.repository.ProductDetailRepository;
import com.ManShirtShop.repository.ProductImageRepository;
import com.ManShirtShop.repository.ProductRepository;
import com.ManShirtShop.repository.SizeRepository;
import com.ManShirtShop.repository.SleeveRepository;
import com.ManShirtShop.service.product.ProductService;
import com.ManShirtShop.service.productDetail.ProductDetailService;
import com.ManShirtShop.service.product_Image.ProductImageService;
import com.ManShirtShop.service.uploadImage.ImageUploadService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.core.joran.conditional.IfAction;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.MaskFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    ProductDetailRepository productDetailRepository;

    @Autowired
    ColorRepository colorRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductImageRepository productImageRepository;

    @Autowired
    DesignRepository designRepository;

    @Autowired
    FormRepository formRepository;

    @Autowired
    MaterialRepository materialRepository;

    @Autowired
    SleeveRepository sleeveRepository;

    @Autowired
    CollarRepository collarRepository;

    @Autowired
    DiscountRepository discountRepository;

    @Autowired
    ProductDetailService productDetailService;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    ResponseFormat responseFormat;

    @Autowired
    ImageUploadService imageUploadService;

    @Autowired
    SizeRepository sizeRepository;

    public Boolean checkIdProduct(Integer id, Boolean checkDB) {
        if (id <= 0 || id == null) {
            return true;
        }
        if (!checkDB) { // check id db
            return true;
        }
        return false;
    }

    @Override
    public List<ProductReponse> getAll() {
        // Pageable pageable = PageRequest.of(page - 1,size );
        List<Product> getAll = productRepository.getAllByStatus();
        List<ProductReponse> lst = ObjectMapperUtils.mapAll(getAll, ProductReponse.class);
        for (ProductReponse x : lst) {
            Integer total = 0;
            for (com.ManShirtShop.dto.product.ProductDetailResponse y : x.getProductDetail()) {
                total = y.getQuantity() + total;
            }
            x.setTotal(total);
        }
        return lst;
    }

    @Override
    public ProductReponse create(ProductRequest requet) {
        requet.setId(-1);
        Product entity = checkAndReturnProduct(requet);
        if (entity == null) {
            return null;
        }
        entity.setCreateBy("admin");
        entity.setCreateTime(Timestamp.from(Instant.now()));
        entity.setStatus(0);
        entity = productRepository.save(entity);
        // for (ProductImageRequest productImage : requet.getProductImages()) {
        // ProductImage tmp = ProductImage.builder()
        // .product(entity.getProductImage())
        // .build();
        // productImageRepository.save(tmp);
        // }
        return ObjectMapperUtils.map(entity, ProductReponse.class);
    }

    @Override
    @Transactional
    public ProductReponse update(ProductAllRequest requet) {
        try {
            Product product = checkAllAndReturnProduct(requet);
            if (product == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dữ liệu không hợp lệ", null);
            }
            if (product.getId() == null || product.getId() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy sản phẩm", null);
            }
            if (!productRepository.existsById(product.getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy sản phẩm", null);
            }
            Product productOld = productRepository.findById(requet.getId()).get();
            product.setCreateBy(productOld.getCreateBy());
            product.setCreateTime(productOld.getCreateTime());
            product.setStatus(productOld.getStatus());
            product.setUpdateTime(Timestamp.from(Instant.now()));
            product.setUpdateBy("admin");
            System.out.println("cate--------ccc: " + product.getCategory().getId());
            product = productRepository.save(product);
            List<ProductImage> lstProductImages = new ArrayList<>();
            if (requet.getProductImage() != null) {
                for (ProductImageRequest x : requet.getProductImage()) {
                    if (x.getId() <= 0 || x.getId() == null) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy ảnh", null);
                    }
                    if (!productImageRepository.existsById(x.getId())) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy ảnh", null);
                    }
                    ProductImage entity = productImageRepository.findById(x.getId()).get();
                    entity.setCreateBy(entity.getCreateBy());
                    entity.setCreateTime(entity.getCreateTime());
                    entity.setUpdateTime(Timestamp.from(Instant.now()));
                    entity.setUpdateBy("admin");
                    entity.setUrlImage(x.getUrlImage());
                    entity.setStatus(entity.getStatus());
                    entity.setColor(entity.getColor());
                    entity.setProduct(product);
                    lstProductImages.add(entity);
                }
                productImageRepository.saveAll(lstProductImages);
            }
            return ObjectMapperUtils.map(product, ProductReponse.class);
        } catch (Exception e) {
            logger.error(e.toString());
            responseFormat.response(HttpServletResponse.SC_BAD_REQUEST, null, "Dữ liệu không hợp lệ");
            throw new RuntimeException("Failed to update product", e);
        }
    }

    public Product checkAndReturnProduct(ProductRequest requet) {
        if (checkIdProduct(requet.getCategory(), categoryRepository.existsById(requet.getCategory()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cate null", null);
        } // check cate
        if (checkIdProduct(requet.getMaterial(), materialRepository.existsById(requet.getMaterial()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mate null", null);
        } // check mate
        if (checkIdProduct(requet.getDesign(), designRepository.existsById(requet.getDesign()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Degin null", null);
        } // check degin
        if (checkIdProduct(requet.getForm(), formRepository.existsById(requet.getForm()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Form null", null);
        } // check form
        if (checkIdProduct(requet.getSleeve(), sleeveRepository.existsById(requet.getSleeve()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sleeve null", null);
        } // check sleeve
        if (checkIdProduct(requet.getCollar(), collarRepository.existsById(requet.getCollar()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Collar null", null);
        } // check collar
        Product entity = ObjectMapperUtils.map(requet, Product.class);
        // if (requet.getDiscount() <= 0 || requet.getDiscount() == null) {
        // // entity.setDiscount(null);
        // } else if (!designRepository.existsById(requet.getDiscount())) {
        // return null;
        // } else {
        // Discount discount = new Discount();
        // discount.setId(requet.getDiscount());
        // // entity.setDiscount(discount);
        // }

        Category category = new Category();
        category.setId(requet.getCategory());
        entity.setCategory(category);

        Material material = new Material();
        material.setId(requet.getMaterial());
        entity.setMaterial(material);

        Design design = new Design();
        design.setId(requet.getDesign());
        entity.setDesign(design);

        Form form = new Form();
        form.setId(requet.getForm());
        entity.setForm(form);

        Sleeve sleeve = new Sleeve();
        sleeve.setId(requet.getSleeve());
        entity.setSleeve(sleeve);

        Collar collar = new Collar();
        collar.setId(requet.getCollar());
        entity.setCollar(collar);

        return entity;
    }

    public Product checkAllAndReturnProduct(ProductAllRequest requet) {
        if (checkIdProduct(requet.getCategory(), categoryRepository.existsById(requet.getCategory()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cate null", null);
        } // check cate
        if (checkIdProduct(requet.getMaterial(), materialRepository.existsById(requet.getMaterial()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mate null", null);
        } // check mate
        if (checkIdProduct(requet.getDesign(), designRepository.existsById(requet.getDesign()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Degin null", null);
        } // check degin
        if (checkIdProduct(requet.getForm(), formRepository.existsById(requet.getForm()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Form null", null);
        } // check form
        if (checkIdProduct(requet.getSleeve(), sleeveRepository.existsById(requet.getSleeve()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sleeve null", null);
        } // check sleeve
        if (checkIdProduct(requet.getCollar(), collarRepository.existsById(requet.getCollar()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Collar null", null);
        } // check collar
        Product entity = ObjectMapperUtils.map(requet, Product.class);
        // if (requet.getDiscount() <= 0 || requet.getDiscount() == null) {
        // entity.setDiscount(null);
        // } else if (!designRepository.existsById(requet.getDiscount())) {
        // return null;
        // } else {
        // Discount discount = new Discount();
        // discount.setId(requet.getDiscount());
        // entity.setDiscount(discount);
        // }
        // entity.setDiscount(null);
        Category category = new Category();
        category.setId(requet.getCategory());
        entity.setCategory(category);

        Material material = new Material();
        material.setId(requet.getMaterial());
        entity.setMaterial(material);

        Design design = new Design();
        design.setId(requet.getDesign());
        entity.setDesign(design);

        Form form = new Form();
        form.setId(requet.getForm());
        entity.setForm(form);

        Sleeve sleeve = new Sleeve();
        sleeve.setId(requet.getSleeve());
        entity.setSleeve(sleeve);

        Collar collar = new Collar();
        collar.setId(requet.getCollar());
        entity.setCollar(collar);
        System.out.println("----------------" + entity.toString());
        return entity;
    }

    @Override
    public ProductReponse delete(Integer id) {
        if (checkIdProduct(id, productRepository.existsById(id))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy chi tiết sản phẩm", null);
        }
        Product entity = productRepository.findById(id).get();
        entity.setStatus(1);
        entity = productRepository.save(entity);
        return ObjectMapperUtils.map(entity, ProductReponse.class);
    }

    @Override
    public ProductReponse findById(Integer id) {
        if (checkIdProduct(id, productRepository.existsById(id))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy chi tiết sản phẩm", null);
        }
        Product entity = productRepository.findById(id).get();
        Integer total = 0;
        for (ProductDetail x : entity.getProductDetail()) {
            total = x.getQuantity() + total;
        }
        ProductReponse response = ObjectMapperUtils.map(entity, ProductReponse.class);
        return response;
    }

    @Override
    @Transactional
    public ProductReponse createProductDetailImage(ProductAllRequest request) {
        try {
            Product product = checkAllAndReturnProduct(request);
            if (product == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dữ liệu không hợp lệ", null);
            }
            product.setCreateBy("admin");
            product.setCreateTime(Timestamp.from(Instant.now()));
            product.setStatus(0);
            product.setProductDetail(null);
            product.setProductImage(null);
            System.out.println("cate--------ccc: " + product.getCategory().getId());
            product = productRepository.save(product);
            List<ProductDetail> productDetails = new ArrayList<>();
            for (ProductDetailRequest x : request.getProductDetail()) {
                Color c = colorRepository.findById(x.getColor()).get();
                if (c.getId() != x.getColor()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid color", null);
                }
                Size size = sizeRepository.findById(x.getSize()).get();
                ProductDetail eDetail = new ProductDetail();
                eDetail.setCreateTime(Timestamp.from(Instant.now()));
                eDetail.setCreateBy("admin");
                eDetail.setProduct(product);
                eDetail.setColor(c);
                eDetail.setSize(size);
                eDetail.setBarCode("");
                eDetail.setQuantity(x.getQuantity());
                eDetail.setStatus(0);
                productDetails.add(eDetail);
            }
            productDetailRepository.saveAll(productDetails);
            List<ProductDetail> newProductDetail = new ArrayList<>();
            for (ProductDetail x : productDetails) {// Create Barcode product detail
                x.setBarCode(createBarCode(x.getId()));
                newProductDetail.add(x);
            }
            productDetailRepository.saveAll(newProductDetail);
            List<ProductImage> lstProductImages = new ArrayList<>();
            for (ProductImageRequest x : request.getProductImage()) {
                ProductImage entity = new ProductImage();
                Color c = colorRepository.findById(x.getColorId()).get();
                if (c.getId() != x.getColorId()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid color", null);
                }
                entity.setUrlImage(x.getUrlImage());
                entity.setStatus(Status.STOCKING);
                entity.setCreateBy("admin");
                entity.setColor(c);
                entity.setCreateTime(Timestamp.from(Instant.now()));
                entity.setProduct(product);
                lstProductImages.add(entity);
            }
            productImageRepository.saveAll(lstProductImages);
            return ObjectMapperUtils.map(product, ProductReponse.class);
        } catch (Exception e) {
            logger.error(e.toString());
            responseFormat.response(HttpServletResponse.SC_BAD_REQUEST, null, "Dữ liệu không hợp lệ");
            throw new RuntimeException("Failed to create product", e);
        }
    }

    @Override
    public List<ProductReponse> getAllByFilter(ProductFilterRequest filter) {
        List<Integer> Category = filter.getCategory();
        List<Integer> Collar = filter.getCollar();
        List<Integer> Color = filter.getColor();
        List<Integer> Size = filter.getSize();
        List<Integer> Sleeve = filter.getSleeve();
        List<Integer> Design = filter.getDesign();
        List<Integer> Form = filter.getForm();
        List<Integer> Material = filter.getMaterial();
        Integer Status = filter.getStatus();
        Double Low = filter.getLow();
        Double High = filter.getHigh();
        if (filter.getLow() == null) {
            Low = productRepository.getLow();
        }
        if (filter.getHigh() == null) {
            High = productRepository.getHigh();
        }
        if (Category == null || Category.size() == 0){
            Category = categoryRepository.findAllId();
        }
        if (Collar == null || Collar.size() == 0){
            Collar = collarRepository.findAllId();
        }
        if (Color == null || Color.size() == 0){
            Color = colorRepository.findAllId();
        }
        if (Size == null || Size.size() == 0){
            Size = sizeRepository.findAllId();
        }
        if (Sleeve == null || Sleeve.size() == 0){
            Sleeve = sleeveRepository.findAllId();
        }
        if (Design == null || Design.size() == 0){
            Design = designRepository.findAllId();
        }
        if (Form == null || Form.size() == 0){
            Form = formRepository.findAllId();
        }
        if (Material == null || Material.size() == 0){
            Material = materialRepository.findAllId();
        }
        // List<Product> getAll = productRepository.getAllByAll(Category, Collar, Design, Form, Material, Sleeve, Size, Color, Status, Low, High);
        List<Product> getAll = productRepository.getAllProduct();
      
        return getAll.stream()
                .map(entity -> ObjectMapperUtils.map(entity, ProductReponse.class))
                .collect(Collectors.toList());
    }
    public int getCharValue(char a) {
        return Character.getNumericValue(a);
    }

    public String createBarCode(Integer id) {// quy tắc:
                                             // https://vi.wikipedia.org/wiki/EAN-13#:~:text=V%C3%A9%2C%20phi%E1%BA%BFu-,Quy%20t%E1%BA%AFc%20t%C3%ADnh%20s%E1%BB%91%20ki%E1%BB%83m%20tra,8%2C10%2C12)
        final String MaQuocGia = "893";
        final String MaDoanhNghiep = "12345";
        String MaSanPham = null;
        if (id < 10) {
            MaSanPham = "000" + String.valueOf(id);
        } else if (id < 100) {
            MaSanPham = "00" + String.valueOf(id);
        } else if(id<1000){
            MaSanPham ="0"+ String.valueOf(id);
        }else{
            MaSanPham = String.valueOf(id);
        }
        System.out.println("111111111");
        String ma = MaQuocGia + MaDoanhNghiep + MaSanPham;
        int sum = getCharValue(ma.charAt(0)) + getCharValue(ma.charAt(2)) + getCharValue(ma.charAt(4)) +
                getCharValue(ma.charAt(6)) + getCharValue(ma.charAt(8)) + getCharValue(ma.charAt(10));
        int sum2 = getCharValue(ma.charAt(1)) + getCharValue(ma.charAt(3)) + getCharValue(ma.charAt(5)) +
                getCharValue(ma.charAt(7)) + getCharValue(ma.charAt(9)) + getCharValue(ma.charAt(11));
        sum2 = sum2 * 3;
        sum2 = sum2 + sum;
        int sumfinal = sum2 % 10;
        if (sumfinal != 0) {
            sumfinal = 10 - sumfinal;
        }
        String x = ma + sumfinal;
        System.out.println(x);
        return x;
    }

    // public static void main(String[] args) {
    // ProductServiceImpl a = new ProductServiceImpl();
    // a.createBarCode(12);
    // }

}
