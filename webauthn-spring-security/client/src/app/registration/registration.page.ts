import {Component, inject} from '@angular/core';
import {MessagesService} from '../messages.service';
import {AuthService} from '../auth.service';
import {lastValueFrom} from 'rxjs';
import {FormsModule} from "@angular/forms";
import {
  IonBackButton,
  IonButton,
  IonButtons,
  IonCol,
  IonContent,
  IonGrid,
  IonHeader,
  IonInput,
  IonItem,
  IonRow,
  IonTitle,
  IonToolbar
} from "@ionic/angular/standalone";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.page.html',
  styleUrl: './registration.page.css',
  imports: [FormsModule, IonHeader, IonToolbar, IonButtons, IonBackButton, IonTitle, IonContent, IonGrid, IonRow, IonCol, IonItem, IonInput, IonButton]
})
export class RegistrationPage {
  label = '';

  private readonly messagesService = inject(MessagesService);
  private readonly authService = inject(AuthService);


  async registerWebAuthn(): Promise<void> {
    if (!this.label.trim()) {
      this.messagesService.showErrorToast('Please enter a label for your passkey');
      return;
    }

    if (!window.PublicKeyCredential) {
      this.messagesService.showErrorToast('WebAuthn is not supported by your browser');
      return;
    }

    const loading = await this.messagesService.showLoading('Creating passkey...');
    await loading.present();

    try {
      await lastValueFrom(this.authService.registerWebAuthn(this.label.trim()));
      await this.messagesService.showErrorToast('Passkey created successfully!');
      // Optionally redirect or show success message
      window.location.href = '/webauthn/register?success';
    } catch (error: any) {
      console.error('Registration failed:', error);
      await this.messagesService.showErrorToast(error.message || 'Registration failed');
    } finally {
      await loading.dismiss();
    }
  }

}

