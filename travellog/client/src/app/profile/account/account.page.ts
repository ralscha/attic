import {Component, ViewChild} from '@angular/core';
import {MessagesService} from '../../service/messages.service';
import {AlertController, NavController} from '@ionic/angular';
import {AppDatabase} from '../../model/app-database';
import {NgForm} from '@angular/forms';
import {ProfileService} from '../../service/profile.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.page.html',
  styleUrls: ['./account.page.scss'],
})
export class AccountPage {

  submitError: string = null;

  @ViewChild('deleteForm')
  deleteForm: NgForm;

  constructor(private readonly navCtrl: NavController,
              private readonly profileService: ProfileService,
              private readonly messagesService: MessagesService,
              private readonly appDatabase: AppDatabase,
              private readonly alertController: AlertController) {
  }

  async deleteAccount(password: string): Promise<void> {
    this.submitError = null;

    const alert = await this.alertController.create({
      header: 'Delete Account',
      message: 'Do you really want to delete your account? This action is <strong>irreversible!</strong>',
      buttons: [
        {
          text: 'Cancel',
          role: 'cancel',
          handler: () => {
            this.deleteForm.resetForm();
          }
        }, {
          text: 'Delete Account',
          handler: () => this.reallyDeleteAccount(password)
        }
      ]
    });

    await alert.present();

  }

  private async reallyDeleteAccount(password: string): Promise<void> {
    const loading = await this.messagesService.showLoading('Deleting account');

    this.profileService.deleteAccount(password)
      .subscribe(async (success) => {
        await loading.dismiss();
        if (success) {
          await this.messagesService.showSuccessToast('Account successfully deleted');
          await this.appDatabase.authenticationToken.clear();
          this.navCtrl.navigateRoot('/login');
        } else {
          this.deleteForm.resetForm();
          this.submitError = 'passwordInvalid';
          this.messagesService.showErrorToast('Password invalid');
        }
      }, () => {
        loading.dismiss();
        this.messagesService.showErrorToast('Deleting account failed');
      });
  }

}
