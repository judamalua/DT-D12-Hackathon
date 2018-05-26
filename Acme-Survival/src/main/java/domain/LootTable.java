
package domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class LootTable extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private String	name;
	private boolean	finalMode;


	@NotBlank
	@SafeHtml
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean getFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(final boolean finalMode) {
		this.finalMode = finalMode;
	}


	// Relationships ----------------------------------------------------------
	private Collection<ProbabilityEvent>	probabilityEvents;
	private Collection<ProbabilityItem>		probabilityItems;


	@NotNull
	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<ProbabilityEvent> getProbabilityEvents() {
		return this.probabilityEvents;
	}

	public void setProbabilityEvents(final Collection<ProbabilityEvent> probabilityEvents) {
		this.probabilityEvents = probabilityEvents;

	}

	@NotNull
	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<ProbabilityItem> getProbabilityItems() {
		return this.probabilityItems;
	}

	public void setProbabilityItems(final Collection<ProbabilityItem> probabilityItems) {
		this.probabilityItems = probabilityItems;
	}

	public List<ItemDesign> getResultItems(final Integer luck, final Integer capacity) {
		final Random randomGenerator = new Random();

		final List<ItemDesign> items = new ArrayList<ItemDesign>();

		final Comparator<ProbabilityItem> comparator = new Comparator<ProbabilityItem>() {

			@Override
			public int compare(final ProbabilityItem left, final ProbabilityItem right) {
				return (left.getValue() > right.getValue()) ? 1 : -1; // use your logic
			}
		};

		final List<ProbabilityItem> probItems = new ArrayList<ProbabilityItem>(this.probabilityItems);

		Collections.sort(probItems, comparator);

		for (final ProbabilityItem probItem : probItems) {

			if (probItem.getValue() * (1 + (luck / 100.0)) > randomGenerator.nextDouble()) {
				items.add(probItem.getItemDesign());
			}
			if (items.size() >= capacity) {
				break;
			}
		}

		return items;
	}

	public List<Event> getResultEvents(final Integer luck) {
		final Random randomGenerator = new Random();

		final List<Event> events = new ArrayList<Event>();
		for (final ProbabilityEvent probevent : this.probabilityEvents) {

			if (probevent.getValue() > randomGenerator.nextDouble()) {
				events.add(probevent.getEvent());
			}

		}

		return events;
	}
}
