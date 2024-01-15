import {Component} from '@angular/core';
import {ViewController, NavParams} from "ionic-angular";
import {Quote} from "../../data/quote.interface";

@Component({
  selector: 'page-quote',
  templateUrl: 'quote.html'
})
export class QuotePage {

  selectedQuote: Quote;

  constructor(private readonly viewCtrl: ViewController,
              private readonly navParams: NavParams) {
  }

  ionViewDidLoad() {
    this.selectedQuote = this.navParams.data;
  }

  close(remove = false) {
    this.viewCtrl.dismiss(remove);
  }

}
