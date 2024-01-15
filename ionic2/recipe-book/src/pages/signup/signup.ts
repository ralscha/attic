import {Component} from "@angular/core";
import {NgForm} from "@angular/forms";
import {AuthService} from "../../providers/auth-service";
import {LoadingController, AlertController} from "ionic-angular";

@Component({
  selector: 'page-signup',
  templateUrl: 'signup.html'
})
export class SignupPage {

  constructor(private readonly authService: AuthService,
              private readonly loadingCtrl: LoadingController,
              private readonly alertCtrl: AlertController) {
  }

  onSignup(form: NgForm) {
    const loading = this.loadingCtrl.create({
      content: 'Signing you up...'
    });
    loading.present();

    this.authService.signup(form.value.email, form.value.password)
      .then(data => {
        loading.dismiss();
      })
      .catch(e => {
        loading.dismiss();
        const alert = this.alertCtrl.create({
          title: 'Signup failed',
          message: e.message,
          buttons: ['OK']
        });
        alert.present();
      });
  }

}
