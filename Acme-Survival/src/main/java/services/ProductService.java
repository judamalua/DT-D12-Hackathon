package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProductRepository;
import domain.Product;

@Service
@Transactional
public class ProductService {

	// Managed repository --------------------------------------------------

	@Autowired
	private ProductRepository	productRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public Product create() {
		Product result;

		result = new Product();

		return result;
	}

	public Collection<Product> findAll() {

		Collection<Product> result;

		Assert.notNull(this.productRepository);
		result = this.productRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Product findOne(final int productId) {

		Product result;

		result = this.productRepository.findOne(productId);

		return result;

	}

	public Product save(final Product product) {

		assert product != null;

		Product result;

		result = this.productRepository.save(product);

		return result;

	}

	public void delete(final Product product) {

		assert product != null;
		assert product.getId() != 0;

		Assert.isTrue(this.productRepository.exists(product.getId()));

		this.productRepository.delete(product);

	}
}

