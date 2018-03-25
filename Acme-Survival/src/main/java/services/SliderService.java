package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SliderRepository;
import domain.Slider;

@Service
@Transactional
public class SliderService {

	// Managed repository --------------------------------------------------

	@Autowired
	private SliderRepository	sliderRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public Slider create() {
		Slider result;

		result = new Slider();

		return result;
	}

	public Collection<Slider> findAll() {

		Collection<Slider> result;

		Assert.notNull(this.sliderRepository);
		result = this.sliderRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Slider findOne(final int sliderId) {

		Slider result;

		result = this.sliderRepository.findOne(sliderId);

		return result;

	}

	public Slider save(final Slider slider) {

		assert slider != null;

		Slider result;

		result = this.sliderRepository.save(slider);

		return result;

	}

	public void delete(final Slider slider) {

		assert slider != null;
		assert slider.getId() != 0;

		Assert.isTrue(this.sliderRepository.exists(slider.getId()));

		this.sliderRepository.delete(slider);

	}
}

