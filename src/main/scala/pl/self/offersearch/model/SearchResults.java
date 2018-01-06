package pl.self.offersearch.model;

import java.util.List;

public class SearchResults {

	private final  List<Offer> offers;
	private final int pages;
	public SearchResults(List<Offer> offers, int pages) {
		super();
		this.offers = offers;
		this.pages = pages;
	}
	public List<Offer> getOffers() {
		return offers;
	}
	public int getPages() {
		return pages;
	}
	
}
