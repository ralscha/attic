import {Component, inject} from '@angular/core';
import {
  IonButton,
  IonCol,
  IonContent,
  IonGrid,
  IonHeader,
  IonRouterLink,
  IonRow,
  IonTitle,
  IonToolbar,
  NavController
} from '@ionic/angular/standalone';
import {MessagesService} from '../messages.service';
import {AuthService} from '../auth.service';
import {RouterLink} from '@angular/router';
import {lastValueFrom} from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  imports: [RouterLink, IonRouterLink, IonHeader, IonToolbar, IonTitle, IonContent, IonGrid, IonRow, IonCol, IonButton]
})
export class LoginPage {
  private readonly navCtrl = inject(NavController);
  private readonly authService = inject(AuthService);
  private readonly messagesService = inject(MessagesService);

  async signIn(): Promise<void> {
    const loading = await this.messagesService.showLoading('Authenticating...');
    await loading.present();

    try {
      const redirectUrl = await lastValueFrom(this.authService.authenticateWithWebAuthn());
      if (redirectUrl) {
        window.location.href = redirectUrl;
      } else {
        await this.navCtrl.navigateRoot('/home', {replaceUrl: true});
      }
    } catch (error: any) {
      console.error('Authentication failed:', error);
      await this.messagesService.showErrorToast(error.message || 'Authentication failed');
    } finally {
      await loading.dismiss();
    }
  }

}
