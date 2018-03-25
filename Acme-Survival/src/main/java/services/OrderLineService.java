package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.OrderLineRepository;
import domain.OrderLine;

@Service
@Transactional
public class OrderLineService {

	// Managed repository --------------------------------------------------

	@Autowired
	private OrderLineRepository	orderLineRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public OrderLine create() {
		OrderLine result;

		result = new OrderLine();

		return result;
	}

	public Collection<OrderLine> findAll() {

		Collection<OrderLine> result;

		Assert.notNull(this.orderLineRepository);
		result = this.orderLineRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public OrderLine findOne(final int orderLineId) {

		OrderLine result;

		result = this.orderLineRepository.findOne(orderLineId);

		return result;

	}

	public OrderLine save(final OrderLine orderLine) {

		assert orderLine != null;

		OrderLine result;

		result = this.orderLineRepository.save(orderLine);

		return result;

	}

	public void delete(final OrderLine orderLine) {

		assert orderLine != null;
		assert orderLine.getId() != 0;

		Assert.isTrue(this.orderLineRepository.exists(orderLine.getId()));

		this.orderLineRepository.delete(orderLine);

	}
}

