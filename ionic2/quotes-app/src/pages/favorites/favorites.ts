import {Component} from '@angular/core';
import {QuotesService} from "../../providers/quotes-service";
import {Quote} from "../../data/quote.interface";
import {ModalController} from "ionic-angular";
import {QuotePage} from "../quote/quote";
import {SettingsService} from "../../providers/settings-service";

@Component({
  selector: 'page-favorites',
  templateUrl: 'favorites.html'
})
export class FavoritesPage {
  favoriteQuotes: Quote[];

  constructor(private readonly quotesService: QuotesService,
              private readonly modalCtrl: ModalController,
              public settingsService: SettingsService) {
  }

  ionViewWillEnter() {
    this.favoriteQuotes = this.quotesService.getFavoriteQuotes();
  }

  onViewQuote(quote: Quote) {
    const modal = this.modalCtrl.create(QuotePage, quote);
    modal.present();

    modal.onDidDismiss((remove) => {
      if (remove) {
        this.quotesService.removeQuoteFromFavorites(quote);
        this.ionViewWillEnter();
      }
    });
  }

  onRemoveFromFavorites(quote: Quote) {
    this.quotesService.removeQuoteFromFavorites(quote);
    this.ionViewWillEnter();
  }

  isAltBackground() {
    return this.settingsService.isAlternativeBackground();
  }

  getBackground() {
    if (this.settingsService.isAlternativeBackground()) {
      return 'altQuoteBackground';
    }
    else {
      return 'quoteBackground';
    }
  }

}
