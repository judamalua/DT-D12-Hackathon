
package controllers.manager;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.ProductService;
import domain.Actor;
import domain.Manager;
import domain.Product;

@Controller
@RequestMapping("/product/manager")
public class ProductManagerController {

	// Services -------------------------------------------------------

	@Autowired
	ProductService			productService;
	@Autowired
	ActorService			actorService;
	@Autowired
	ConfigurationService	configurationService;


	// Listing ----------------------------------------------------

	//		@RequestMapping(value = "/list")
	//		public ModelAndView list(@RequestParam final int rendezvousId) {
	//			ModelAndView result;
	//			Collection<Question> questions;
	//			Rendezvous rendezvous;
	//			User user;
	//			String rendezvousName;
	//			boolean rendezvousIsInFinalMode, rendezvousIsDeleted;
	//
	//			try {
	//
	//				result = new ModelAndView("question/list");
	//				user = (User) this.actorService.findActorByPrincipal();
	//				rendezvous = this.rendezvousService.findOne(rendezvousId);
	//				Assert.notNull(rendezvous);
	//				// Checking if the user trying to access is the creator of this Rendezvous
	//				Assert.isTrue(user.getCreatedRendezvouses().contains(rendezvous));
	//				rendezvousName = rendezvous.getName();
	//				questions = rendezvous.getQuestions();
	//				rendezvousIsInFinalMode = rendezvous.getFinalMode();
	//				rendezvousIsDeleted = rendezvous.getDeleted();
	//
	//				result.addObject("rendezvousName", rendezvousName);
	//				result.addObject("rendezvousId", rendezvousId);
	//				result.addObject("questions", questions);
	//				result.addObject("rendezvousIsInFinalMode", rendezvousIsInFinalMode);
	//				result.addObject("rendezvousIsDeleted", rendezvousIsDeleted);
	//
	//			} catch (final Throwable oops) {
	//				result = new ModelAndView("redirect:/misc/403");
	//			}
	//
	//			return result;
	//		}

	// Editing ---------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int productId) {
		ModelAndView result;
		Product product;
		Actor actor;

		try {
			actor = this.actorService.findActorByPrincipal();
			// Checking that the user trying to edit the product is a manager.
			Assert.isTrue(actor instanceof Manager);

			product = this.productService.findOne(productId);
			Assert.notNull(product);

			// Checking that the product trying to be edited is not a final mode product.
			Assert.isTrue(!product.getFinalMode());

			result = this.createEditModelAndView(product);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}

	// Creating ---------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Product product;
		Actor actor;

		try {
			actor = this.actorService.findActorByPrincipal();
			// Checking that the user trying to create a product is a manager.
			Assert.isTrue(actor instanceof Manager);

			product = this.productService.create();
			result = this.createEditModelAndView(product);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}

	// Saving -------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Product product, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(product, "product.params.error");
		else
			try {
				this.productService.save(product);
				result = new ModelAndView("redirect:list-draft.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(product, "product.commit.error");
			}

		return result;
	}

	// Deleting ------------------------------------------------------------------------

	//		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	//		public ModelAndView delete(@RequestParam final int rendezvousId, Question question, final BindingResult binding) {
	//			ModelAndView result;
	//
	//			try {
	//				question = this.questionService.reconstruct(question, binding);
	//			} catch (final Throwable oops) {
	//				result = new ModelAndView("redirect:/misc/403");
	//				return result;
	//			}
	//			try {
	//				this.questionService.delete(question);
	//				result = new ModelAndView("redirect:list.do?rendezvousId=" + rendezvousId);
	//
	//			} catch (final Throwable oops) {
	//				result = this.createEditModelAndView(question, "question.commit.error");
	//				result.addObject("rendezvousId", rendezvousId);
	//			}
	//
	//			return result;
	//		}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Product product) {
		ModelAndView result;

		result = this.createEditModelAndView(product, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Product product, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("product/edit");
		result.addObject("product", product);

		result.addObject("message", messageCode);

		return result;

	}

}
