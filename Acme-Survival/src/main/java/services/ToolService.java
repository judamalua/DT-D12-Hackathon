package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ToolRepository;
import domain.Tool;

@Service
@Transactional
public class ToolService {

	// Managed repository --------------------------------------------------

	@Autowired
	private ToolRepository	toolRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public Tool create() {
		Tool result;

		result = new Tool();

		return result;
	}

	public Collection<Tool> findAll() {

		Collection<Tool> result;

		Assert.notNull(this.toolRepository);
		result = this.toolRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Tool findOne(final int toolId) {

		Tool result;

		result = this.toolRepository.findOne(toolId);

		return result;

	}

	public Tool save(final Tool tool) {

		assert tool != null;

		Tool result;

		result = this.toolRepository.save(tool);

		return result;

	}

	public void delete(final Tool tool) {

		assert tool != null;
		assert tool.getId() != 0;

		Assert.isTrue(this.toolRepository.exists(tool.getId()));

		this.toolRepository.delete(tool);

	}
}

