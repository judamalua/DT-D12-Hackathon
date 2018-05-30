
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.OrderRepository;
import domain.Order;

@Service
@Transactional
public class OrderService {

	// Managed repository --------------------------------------------------

	@Autowired
	private OrderRepository	orderRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public Order create() {
		Order result;

		result = new Order();

		return result;
	}

	public Collection<Order> findAll() {

		Collection<Order> result;

		Assert.notNull(this.orderRepository);
		result = this.orderRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Order findOne(final int orderId) {

		Order result;

		result = this.orderRepository.findOne(orderId);

		return result;

	}

	public Order save(final Order order) {

		assert order != null;

		Order result;

		result = this.orderRepository.save(order);

		return result;

	}

	public Collection<Collection<String>> findNumOrdersByActor() {
		Collection<Collection<String>> result;

		result = this.orderRepository.findNumOrdersByActor();

		return result;

	}

	public void delete(final Order order) {

		assert order != null;
		assert order.getId() != 0;

		Assert.isTrue(this.orderRepository.exists(order.getId()));

		this.orderRepository.delete(order);

	}
}
