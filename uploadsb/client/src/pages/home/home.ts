import {Component} from '@angular/core';
import {Loading, LoadingController, ToastController} from "ionic-angular";
import {Camera} from '@ionic-native/camera';
import {File} from "@ionic-native/file";
import {FileTransfer, FileTransferObject, FileUploadOptions} from "@ionic-native/file-transfer";

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {
  public myPhoto: any;
  public error: string;
  private loading: Loading;

  constructor(private readonly loadingCtrl: LoadingController,
              private readonly toastCtrl: ToastController,
              private readonly camera: Camera,
              private file: File,
              private transfer: FileTransfer) {
  }

  async takePhoto() {
    try {
      const imageData = await this.camera.getPicture({
        quality: 100,
        destinationType: this.camera.DestinationType.FILE_URI,
        sourceType: this.camera.PictureSourceType.CAMERA,
        encodingType: this.camera.EncodingType.PNG
      });

      await this.uploadPhoto(imageData);
    }
    catch (e) {
      console.log(e);
    }
  }

  async selectPhoto() {
    try {
      const imageData = await
        this.camera.getPicture({
          sourceType: this.camera.PictureSourceType.PHOTOLIBRARY,
          destinationType: this.camera.DestinationType.FILE_URI,
          quality: 100,
          encodingType: this.camera.EncodingType.PNG,
        });

      await this.uploadPhoto(imageData);
    }
    catch (e) {
      console.log(e);
    }
  }

  async uploadPhoto(imageFileUri: any): Promise<void> {
    this.myPhoto = imageFileUri;

    this.error = null;
    this.loading = this.loadingCtrl.create({
      content: 'Uploading...'
    });

    await this.loading.present();

    const fileTransfer: FileTransferObject = this.transfer.create();

    const fileEntry = await this.file.resolveLocalFilesystemUrl(imageFileUri);

    const options: FileUploadOptions = {
      fileKey: 'file',
      fileName: fileEntry.name,
      headers: {}
    };

    try {
      const result = await fileTransfer.upload(imageFileUri, 'http://192.168.178.84:8080/upload', options);
      console.log(result.bytesSent);
      console.log(result.responseCode);
      this.showToast(true);
    }
    catch (e) {
      console.log(e);
      this.showToast(false);
    }
    finally {
      this.loading.dismiss();
    }
  }

  private showToast(ok: boolean) {
    if (ok) {
      const toast = this.toastCtrl.create({
        message: 'Upload successful',
        duration: 3000,
        position: 'top'
      });
      toast.present();
    }
    else {
      const toast = this.toastCtrl.create({
        message: 'Upload failed',
        duration: 3000,
        position: 'top'
      });
      toast.present();
    }
  }

}
