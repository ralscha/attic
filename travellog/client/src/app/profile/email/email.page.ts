import {Component} from '@angular/core';
import {ProfileService} from '../../service/profile.service';
import {MessagesService} from '../../service/messages.service';

@Component({
  selector: 'app-email',
  templateUrl: './email.page.html',
  styleUrls: ['./email.page.scss'],
})
export class EmailPage {

  submitError: string = null;
  changeSent = false;

  constructor(private readonly profileService: ProfileService,
              private readonly messagesService: MessagesService) {
  }

  async changeEmail(newEmail: string, password: string): Promise<void> {
    const loading = await this.messagesService.showLoading('Saving email change request');
    this.submitError = null;

    this.profileService.changeEmail(newEmail, password)
      .subscribe(async (flag) => {
        await loading.dismiss();
        if (flag === 'SAME') {
          this.submitError = 'noChange';
          this.messagesService.showErrorToast('New email same as old email');
        } else if (flag === 'USE') {
          this.submitError = 'emailRegistered';
          this.messagesService.showErrorToast('Email already registered');
        } else if (flag === 'PASSWORD') {
          this.submitError = 'passwordInvalid';
          this.messagesService.showErrorToast('Password invalid');
        } else {
          await this.messagesService.showSuccessToast('Email change request successfully saved');
          this.changeSent = true;
        }
      }, () => {
        loading.dismiss();
        this.messagesService.showErrorToast();
      });

  }

}
