import {Component} from '@angular/core';
import {NavController, Platform} from 'ionic-angular';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Data} from '../../providers/data';

@Component({
  selector: 'page-camp-details',
  templateUrl: 'camp-details.html'
})
export class CampDetailsPage {

  campDetailsForm: FormGroup;

  constructor(public navCtrl: NavController, public platform: Platform, public formBuilder: FormBuilder, public dataService: Data) {

    this.campDetailsForm = formBuilder.group({
      gateAccessCode: [''],
      ammenitiesCode: [''],
      wifiPassword: [''],
      phoneNumber: [''],
      departure: [''],
      notes: ['']
    });

  }

  ionViewDidLoad() {

    this.platform.ready().then(() => {

      this.dataService.getCampDetails().then((details) => {

        let savedDetails: any = false;

        if (details && typeof(details) != "undefined") {
          savedDetails = JSON.parse(details);
        }

        let formControls: any = this.campDetailsForm.controls;

        if (savedDetails) {
          formControls.gateAccessCode.setValue(savedDetails.gateAccessCode);
          formControls.ammenitiesCode.setValue(savedDetails.ammenitiesCode);
          formControls.wifiPassword.setValue(savedDetails.wifiPassword);
          formControls.phoneNumber.setValue(savedDetails.phoneNumber);
          formControls.departure.setValue(savedDetails.departure);
          formControls.notes.setValue(savedDetails.notes);
        }

      });

    });

  }

  saveForm(): void {
    let data = this.campDetailsForm.value;
    this.dataService.setCampDetails(data);
  }

}
