package com.xuanluan.mc.product.service.impl;

import com.xuanluan.mc.domain.entity.DataSequence;
import com.xuanluan.mc.domain.enums.SequenceType;
import com.xuanluan.mc.domain.model.filter.ResultList;
import com.xuanluan.mc.domain.validation.file.ImageFileValidator;
import com.xuanluan.mc.exception.ServiceException;
import com.xuanluan.mc.product.model.entity.FileStorage;
import com.xuanluan.mc.product.model.entity.Product;
import com.xuanluan.mc.product.model.entity.ProductShare;
import com.xuanluan.mc.product.model.entity.ProductVariant;
import com.xuanluan.mc.product.model.request.*;
import com.xuanluan.mc.product.model.request.filter.ProductFilter;
import com.xuanluan.mc.product.pattern.builder.FileStorageBuilder;
import com.xuanluan.mc.product.repository.file.FileStorageRepository;
import com.xuanluan.mc.product.repository.product.ProductRepository;
import com.xuanluan.mc.product.repository.product_share.ProductShareRepository;
import com.xuanluan.mc.product.repository.variant.ProductVariantRepository;
import com.xuanluan.mc.product.service.IProductService;
import com.xuanluan.mc.product.utils.MapperUtils;
import com.xuanluan.mc.service.impl.DataSequenceServiceImpl;
import com.xuanluan.mc.utils.BaseStringUtils;
import com.xuanluan.mc.utils.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Xuan Luan
 * @createdAt 2/27/2023
 */
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements IProductService {
    private final DataSequenceServiceImpl sequenceService;
    private final ProductRepository productRepository;
    private final ProductVariantRepository variantRepository;
    private final ProductShareRepository shareRepository;
    private final FileStorageRepository fileStorageRepository;

    private void invalidMinAndMaxPrice(double minPrice, double maxPrice, double salePrice) {
        ExceptionUtils.notNegative("Giá thấp nhất", minPrice);
        ExceptionUtils.notNegative("Giá cao nhất", maxPrice);
        ExceptionUtils.notNegative("Giá bán", salePrice);
        if (minPrice > maxPrice || salePrice < minPrice || salePrice > maxPrice) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "Required salePrice >= minPrice and <= maxPrice", "Yêu cầu: giá thấp nhất <= giá bán <= giá cao nhất");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Product createNewProduct(String clientId, String orgId, NewProduct request) {
        invalidMinAndMaxPrice(request.getMinPrice(), request.getMaxPrice(), request.getSalePrice());
        Product product = productRepository.findBySkuOrSlug(clientId, orgId, request.getSku(), request.getSlug());
        if (product != null) {
            if (request.getSlug() != null && request.getSlug().equals(product.getSlug())) {
                throw new ServiceException(HttpStatus.CONFLICT, "slug= " + request.getSlug() + " is already exist", "slug: " + request.getSlug() + " đã tồn tại");
            }
            if (request.getSku() != null && request.getSku().equals(product.getSku())) {
                throw new ServiceException(HttpStatus.CONFLICT, "sku= " + request.getSku() + " is already exist", "sku: " + request.getSku() + " đã tồn tại");
            }
        }
        DataSequence sequence = sequenceService.generateDataSequence("all", "all", Product.class, SequenceType.NUMBER);
        product = MapperUtils.convertToProduct(clientId, orgId, sequence.getSequenceValue(), request);
        product.setSlug(product.getSlug() != null ? request.getSlug().trim().replaceAll("\\s+", "-") : product.getProductId());
        product.setSku(BaseStringUtils.hasTextAfterTrim(product.getSku()) ? product.getSku() : product.getProductId());

        // upload file to storage
        if (request.getFile() != null) {
            FileEntityRequest<Product> fileEntity = FileEntityRequest.<Product>builder()
                    .entityClass(Product.class)
                    .entityId(product.getProductId())
                    .file(request.getFile())
                    .build();
            FileStorage fileStorage = fileStorageRepository.save(new FileStorageBuilder<>(clientId, orgId, fileEntity).init());
            product.setFileStorage(fileStorage);
        }

        return productRepository.save(product);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<ProductVariant> addVariantsForProduct(String clientId, String orgId, String productId, NewProductVariantsInput request) {
        ExceptionUtils.invalidInput("Danh sách biến thể", request.getVariants());
        ExceptionUtils.isTrue(request.getVariants().size() > 0, "Danh sách biến thể không được rỗng", "New variants must not be empty");

        Product product = productRepository.findByProductId(clientId, orgId, productId);
        if (product == null) {
            throw new ServiceException(HttpStatus.NOT_FOUND, "Not found product, productId= " + productId, "Không tìm thấy sản phẩm, productId: " + productId);
        }
        ProductVariant newestVariant = variantRepository.getNewestVariantOfProductId(clientId, orgId, productId);
        String sequenceValue = newestVariant != null ? newestVariant.getPVariantId() : productId;
        final int[] value = {Integer.parseInt(sequenceValue)};
        if (newestVariant != null) value[0]++;
        List<FileStorage> fileStorageList = new ArrayList<>();
        List<ProductVariant> variants = request.getVariants().stream()
                .map(variantInput -> {
                    ExceptionUtils.invalidInput("Màu sắc", variantInput.getColor());
                    ProductVariant variant = MapperUtils.convertToProductVariant(clientId, orgId, product, variantInput);
                    variant.setPVariantId("" + value[0]++);
                    //sku is blank => sku= <productId>.<pVariantId>
                    variant.setSku(product.getProductId() + "." + variant.getPVariantId());
                    variant.setCreatedBy(request.getByUser());
                    //add list file
                    if (variantInput.getFile() != null) {
                        if (!ImageFileValidator.isSupportedContentType(variantInput.getFile().getType())) {
                            throw new ServiceException(HttpStatus.BAD_REQUEST, "Invalid file type!", "Chỉ chấp nhận file png|jpg|jpeg");
                        }
                        FileEntityRequest<ProductVariant> fileEntity = FileEntityRequest.<ProductVariant>builder()
                                .entityClass(ProductVariant.class)
                                .entityId(variant.getId())
                                .file(variantInput.getFile())
                                .build();
                        FileStorage fileStorage = new FileStorageBuilder<>(clientId, orgId, fileEntity).init();
                        fileStorageList.add(fileStorage);

                        variant.setFileStorage(fileStorage);
                    }

                    return variant;
                })
                .collect(Collectors.toList());

        fileStorageRepository.saveAll(fileStorageList);
        return (List<ProductVariant>) variantRepository.saveAll(variants);
    }

    @Override
    public ResultList<Product> searchProduct(String clientId, String orgId, ProductFilter filter) {
        return productRepository.searchFullText(clientId, orgId, filter);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<ProductShare> shareProductsForClient(String clientId, String orgId, NewProductShare request) {
        ExceptionUtils.invalidInput("Danh sách sản phẩm", request.getProductIds());
        ExceptionUtils.invalidInput("Mã dịch vụ", request.getToClientId());
        ExceptionUtils.invalidInput("Danh sách mã tổ chức", request.getToOrgIds());
        return (List<ProductShare>) shareRepository.saveAll(
                request.getToOrgIds().stream()
                        .map(toOrgId -> MapperUtils.convertToProductShares(clientId, orgId, toOrgId, request))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList())
        );
    }

    private void notActive(boolean isActive) {
        if (!isActive) {
            throw new ServiceException(HttpStatus.LOCKED, "This status is no longer active", "Trạng thái đã dừng hoạt động");
        }
    }

    /**
     * @param clientId is toClientId
     * @param orgId    is toOrgId
     */
    @Override
    public ProductShare updateProductShare(String clientId, String orgId, String productId, ChangeProductShare request) {
        ProductShare productShare = shareRepository.findByProductIdFromAnotherClient(clientId, orgId, productId);
        ExceptionUtils.notFoundData("productId", productId, productShare);
        notActive(productShare.isActive());

        if (request.isOpen()) {
            Product product = productRepository.findByProductId(productShare.getClientId(), productShare.getOrgId(), productId);
            ExceptionUtils.notFoundData("productId", productId, productShare);
            notActive(productShare.isActive());
            if (request.getOfficialPrice() == null && !productShare.isOpening()) {
                productShare.setOfficialPrice(product.getMinPrice());
            } else if (request.getOfficialPrice() != null) {
                if (request.getOfficialPrice() < product.getMinPrice() || request.getOfficialPrice() > product.getMaxPrice()) {
                    throw new ServiceException(
                            HttpStatus.BAD_REQUEST,
                            "officialPrice must be greater " + product.getMinPrice() + " and less " + product.getMaxPrice(),
                            "Giá sản phẩm không được thấp hơn " + product.getMinPrice() + " hoặc cao hơn " + product.getMaxPrice()
                    );
                }
                productShare.setOfficialPrice(request.getOfficialPrice());
            }
        }
        productShare.setOpening(request.isOpen());
        productShare.setUpdatedBy(request.getByUser());
        return shareRepository.save(productShare);
    }
}
