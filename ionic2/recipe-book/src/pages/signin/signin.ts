import {Component} from '@angular/core';
import {NgForm} from "@angular/forms";
import {AuthService} from "../../providers/auth-service";
import {LoadingController, AlertController} from "ionic-angular";

@Component({
  selector: 'page-signin',
  templateUrl: 'signin.html'
})
export class SigninPage {

  constructor(private readonly authService: AuthService,
              private readonly loadingCtrl: LoadingController,
              private readonly alertCtrl: AlertController) {
  }

  onSignin(form: NgForm) {
    const loading = this.loadingCtrl.create({
      content: 'Signing you in...'
    });
    loading.present();

    this.authService.signin(form.value.email, form.value.password)
      .then(data => {
        loading.dismiss();
      })
      .catch(e => {
        loading.dismiss();
        const alert = this.alertCtrl.create({
          title: 'Signin failed',
          message: e.message,
          buttons: ['OK']
        });
        alert.present();
      });
  }

}
