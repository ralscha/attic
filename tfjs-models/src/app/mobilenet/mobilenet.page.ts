import {Component, ElementRef, inject, ViewChild} from '@angular/core';
import {
  IonButton,
  IonContent,
  IonFooter,
  IonHeader,
  IonTitle,
  IonToolbar,
  LoadingController
} from '@ionic/angular/standalone';
import {load, MobileNet} from '@tensorflow-models/mobilenet';
import {AsyncPipe, DecimalPipe} from '@angular/common';

@Component({
  selector: 'app-mobilenet',
  templateUrl: './mobilenet.page.html',
  styleUrls: ['./mobilenet.page.scss'],
  imports: [
    IonHeader,
    IonToolbar,
    IonTitle,
    IonContent,
    IonFooter,
    IonButton,
    AsyncPipe,
    DecimalPipe
  ]
})
export class MobilenetPage {
  @ViewChild('image', {static: true}) image!: ElementRef;
  @ViewChild('fileSelector') fileInput!: ElementRef;
  modelPromise: Promise<MobileNet>;
  predictions: Promise<Array<{ className: string; probability: number }>> | null = null;
  private readonly loadingController = inject(LoadingController);

  constructor() {
    this.modelPromise = load();
  }

  clickFileSelector(): void {
    this.fileInput.nativeElement.click();
  }

  onFileCange(event: Event): void {
    // @ts-ignore
    this.image.nativeElement.src = URL.createObjectURL(event.target.files[0]);
    this.predict();
  }

  private async predict(): Promise<void> {
    this.predictions = null;

    const loading = await this.loadingController.create({
      message: 'Predicting...'
    });
    await loading.present();

    const model = await this.modelPromise;
    this.predictions = model.classify(this.image.nativeElement, 4).then(predictions => {
      loading.dismiss();
      console.log(predictions);
      return predictions;
    });
  }

}
