import {Component, OnInit} from '@angular/core';
import {NavParams, AlertController} from "ionic-angular";
import {Quote} from "../../data/quote.interface";
import {QuotesService} from "../../providers/quotes-service";

@Component({
  selector: 'page-quotes',
  templateUrl: 'quotes.html'
})
export class QuotesPage implements OnInit {

  quoteGroup: {category: string, quotes: Quote[], icon: string};

  constructor(private readonly navParams: NavParams,
              private readonly alertCtrl: AlertController,
              private readonly quotesService: QuotesService) {
  }

  ngOnInit() {
    this.quoteGroup = this.navParams.data;
  }

  onAddToFavorites(selectedQuote: Quote) {
    const alert = this.alertCtrl.create({
      title: 'Add Quote',
      subTitle: 'Are you sure?',
      message: 'Are you sure you want to add the quote?',
      buttons: [
        {
          text: 'Yes, go ahead',
          handler: () => this.quotesService.addQuoteToFavorites(selectedQuote)
        },
        {
          text: 'No, I changed my mind',
          role: 'cancel'
        }
      ]
    });

    alert.present();
  }

  onRemoveFromFavorite(selectedQuote: Quote) {
    this.quotesService.removeQuoteFromFavorites(selectedQuote);
  }

  isFavorite(quote: Quote) {
    return this.quotesService.isQuoteFavorite(quote);
  }

}
