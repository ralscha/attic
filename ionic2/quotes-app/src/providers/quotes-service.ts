import {Quote} from "../data/quote.interface";

export class QuotesService {

  private favoriteQuotes: Quote[] = [];

  addQuoteToFavorites(quote: Quote) {
    this.favoriteQuotes.push(quote);
  }

  removeQuoteFromFavorites(quote: Quote) {
    const pos = this.favoriteQuotes.findIndex(q => q.id === quote.id);
    this.favoriteQuotes.splice(pos, 1);
  }

  getFavoriteQuotes() {
    return this.favoriteQuotes.slice();
  }

  isQuoteFavorite(quote: Quote): boolean {
    return this.favoriteQuotes.find(q=> q.id === quote.id) != null;
  }
}
