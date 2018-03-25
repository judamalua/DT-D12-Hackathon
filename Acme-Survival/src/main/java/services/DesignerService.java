package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.DesignerRepository;
import domain.Designer;

@Service
@Transactional
public class DesignerService {

	// Managed repository --------------------------------------------------

	@Autowired
	private DesignerRepository	designerRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public Designer create() {
		Designer result;

		result = new Designer();

		return result;
	}

	public Collection<Designer> findAll() {

		Collection<Designer> result;

		Assert.notNull(this.designerRepository);
		result = this.designerRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Designer findOne(final int designerId) {

		Designer result;

		result = this.designerRepository.findOne(designerId);

		return result;

	}

	public Designer save(final Designer designer) {

		assert designer != null;

		Designer result;

		result = this.designerRepository.save(designer);

		return result;

	}

	public void delete(final Designer designer) {

		assert designer != null;
		assert designer.getId() != 0;

		Assert.isTrue(this.designerRepository.exists(designer.getId()));

		this.designerRepository.delete(designer);

	}
}

