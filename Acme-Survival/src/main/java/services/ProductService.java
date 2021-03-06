
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProductRepository;
import domain.Actor;
import domain.Manager;
import domain.Product;

@Service
@Transactional
public class ProductService {

	// Managed repository --------------------------------------------------

	@Autowired
	private ProductRepository	productRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods --------------------------------------------------

	/**
	 * Creates a Product
	 * 
	 * @return the created Product
	 * @author Juanmi
	 */
	public Product create() {
		this.actorService.checkActorLogin();

		Product result;

		result = new Product();

		result.setFinalMode(false);
		result.setDiscontinued(false);

		return result;
	}

	/**
	 * Finds every product in the system
	 * 
	 * @return the collection of every Product
	 * @author Juanmi
	 */
	public Collection<Product> findAll() {

		Collection<Product> result;

		Assert.notNull(this.productRepository);
		result = this.productRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	/**
	 * Finds one product in the system
	 * 
	 * @param productId
	 * @return the product with the given id
	 */
	public Product findOne(final int productId) {

		Product result;

		result = this.productRepository.findOne(productId);

		return result;

	}

	/**
	 * Saves the product given by parameters
	 * 
	 * @param product
	 *            to be saved
	 * @return the product saved
	 */
	public Product save(final Product product) {

		assert product != null;

		Product result;
		Actor actor;

		actor = this.actorService.findActorByPrincipal();

		// Checking that the user trying to modify/create a product is a manager.
		Assert.isTrue(actor instanceof Manager);

		if (product.getId() == 0)
			// Checking that a product cannot be created as a discontinued product.
			Assert.isTrue(!product.getDiscontinued());

		result = this.productRepository.save(product);

		return result;

	}

	/**
	 * Deletes the product in parameters
	 * 
	 * @param product
	 *            to be deleted
	 * @author Juanmi
	 */
	public void delete(final Product product) {

		assert product != null;
		assert product.getId() != 0;
		Actor actor;

		actor = this.actorService.findActorByPrincipal();

		// Checking that the user trying to delete a product is a manager.
		Assert.isTrue(actor instanceof Manager);
		Assert.isTrue(this.productRepository.exists(product.getId()));
		// Checking that the product trying to be deleted is not in final mode.
		Assert.isTrue(!product.getFinalMode());

		this.productRepository.delete(product);

	}

	// Other business methods ----------------------------------------------------------------------------------

	/**
	 * This method marks a product as discontinued
	 * 
	 * @param product
	 * @author Juanmi
	 */
	public void discontinueProduct(final Product product) {
		Actor actor;

		actor = this.actorService.findActorByPrincipal();

		// Checking that the user trying to discontinue a product is a manager.
		Assert.isTrue(actor instanceof Manager);

		// Checking that the product that is going to be marked as discontinued is a final mode product.
		Assert.isTrue(product.getFinalMode());
		Assert.isTrue(!product.getDiscontinued());

		product.setDiscontinued(true);

		this.save(product);
	}

	/**
	 * This method marks a product as in stock
	 * 
	 * @param product
	 * @author Juanmi
	 */
	public void productInStock(final Product product) {
		Actor actor;

		actor = this.actorService.findActorByPrincipal();

		// Checking that the user trying to discontinue a product is a manager.
		Assert.isTrue(actor instanceof Manager);

		// Checking that the product that is going to be marked as discontinued is a final mode product.
		Assert.isTrue(product.getFinalMode());
		Assert.isTrue(product.getDiscontinued());

		product.setDiscontinued(false);

		this.save(product);
	}

	/**
	 * This method marks a product as final mode
	 * 
	 * @param product
	 * @author Juanmi
	 */
	public void setFinalModeProduct(final Product product) {
		Actor actor;

		actor = this.actorService.findActorByPrincipal();

		// Checking that the user trying to mark a product as final mode is a manager.
		Assert.isTrue(actor instanceof Manager);

		// Checking that the product that is going to be marked as final mode is not a final mode product.
		Assert.isTrue(!product.getFinalMode());

		product.setFinalMode(true);

		this.save(product);
	}

	/**
	 * This method returns every final mode product in the system with a pageable object
	 * 
	 * @param pageable
	 * @return a page of Products
	 * 
	 * @author Juanmi
	 */
	public Page<Product> getFinalModeProducts(final Pageable pageable) {
		Page<Product> result;

		Assert.notNull(pageable);

		result = this.productRepository.getFinalModeProducts(pageable);

		return result;

	}

	/**
	 * This method returns every draft mode product in the system with a pageable object
	 * 
	 * @param pageable
	 * @return a page of Products
	 * 
	 * @author Juanmi
	 */
	public Page<Product> getDraftModeProducts(final Pageable pageable) {
		Page<Product> result;
		Actor actor;

		actor = this.actorService.findActorByPrincipal();

		// Checking that the user trying to list draft mode products is a manager.
		Assert.isTrue(actor instanceof Manager);
		Assert.notNull(pageable);

		result = this.productRepository.getDraftModeProducts(pageable);

		return result;

	}

	/**
	 * This method returns every catalogued product in the system with a pageable object
	 * 
	 * @param pageable
	 * @return a page of Products
	 * 
	 * @author Juanmi
	 */
	public Page<Product> getCataloguedProducts(final Pageable pageable) {
		Page<Product> result;

		Assert.notNull(pageable);

		result = this.productRepository.getCataloguedProducts(pageable);

		return result;

	}

	/**
	 * This method flushes the repository, this forces the cache to be saved to the database, which then forces the test data to be validated. This is only used
	 * in tests
	 * 
	 * @author Juanmi
	 */
	public void flush() {
		this.productRepository.flush();
	}
}
