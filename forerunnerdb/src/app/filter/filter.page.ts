import {Component, OnInit} from '@angular/core';
import {Filter} from '../filter';
import {ModalController, NavParams} from '@ionic/angular';

@Component({
  selector: 'app-filter',
  templateUrl: './filter.page.html',
  styleUrls: ['./filter.page.scss'],
})
export class FilterPage implements OnInit {

  filter: Filter;

  constructor(private readonly navParams: NavParams,
              private readonly modalCtrl: ModalController) {
  }

  ngOnInit() {
    this.filter = this.navParams.get('filter');
  }

  applyFilters({value}) {
    this.dismiss(value);
  }

  dismiss(data?: any) {
    if (data) {
      data.myLocation = this.filter.myLocation;
    }
    this.modalCtrl.dismiss(data);
  }

}
