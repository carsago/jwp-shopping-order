package cart.application;

import static java.util.stream.Collectors.toList;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.exception.ProductNotFound;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productDao.findAll().stream()
                .map(ProductResponse::of)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long productId) {
        Product product = productDao.findById(productId)
                .orElseThrow(ProductNotFound::new);
        return ProductResponse.of(product);
    }

    @Transactional
    public Long createProduct(ProductRequest productRequest) {
        Product product = productRequest.toEntity();
        return productDao.save(product);
    }


    @Transactional
    public void updateProduct(Long productId, ProductRequest productRequest) {
        Product product = productRequest.toEntity();
        productDao.updateById(productId, product);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        productDao.deleteById(productId);
    }
}
