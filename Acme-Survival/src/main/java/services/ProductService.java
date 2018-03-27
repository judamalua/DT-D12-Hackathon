
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
		this.actorService.checkUserLogin();

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

		// TODO: Check in controllers that the product that is being modified is not in final mode.

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

	/**
	 * This method marks a product as discontinued
	 * 
	 * @param product
	 * @author Juanmi
	 */
	public void discontinueProduct(final Product product) {
		Actor actor;

		actor = this.actorService.findActorByPrincipal();

		// Checking that the user trying to delete a product is a manager.
		Assert.isTrue(actor instanceof Manager);

		// Checking that the product that is going to be marked as discontinued is a final mode product.
		Assert.isTrue(product.getFinalMode());

		product.setDiscontinued(true);

		this.save(product);
	}

	// Other business methods ----------------------------------------------------------------------------------

	/**
	 * This method returns every final mode product in the system with a pageable object
	 * 
	 * @param page
	 * @return a page of Products
	 * 
	 * @author Juanmi
	 */
	Page<Product> getFinalModeProducts(final Pageable page) {
		Page<Product> result;

		Assert.notNull(page);

		result = this.productRepository.getFinalModeProducts(page);

		return result;

	}

	/**
	 * This method returns every draft mode product in the system with a pageable object
	 * 
	 * @param page
	 * @return a page of Products
	 * 
	 * @author Juanmi
	 */
	Page<Product> getDraftModeProducts(final Pageable page) {
		Page<Product> result;
		Actor actor;

		actor = this.actorService.findActorByPrincipal();

		// Checking that the user trying to list draft mode products is a manager.
		Assert.isTrue(actor instanceof Manager);
		Assert.notNull(page);

		result = this.productRepository.getDraftModeProducts(page);

		return result;

	}

	/**
	 * This method returns every catalogued product in the system with a pageable object
	 * 
	 * @param page
	 * @return a page of Products
	 * 
	 * @author Juanmi
	 */
	Page<Product> getCataloguedProducts(final Pageable page) {
		Page<Product> result;

		Assert.notNull(page);

		result = this.productRepository.getCataloguedProducts(page);

		return result;

	}
}
