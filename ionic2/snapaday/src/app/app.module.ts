import { NgModule, ErrorHandler } from '@angular/core';
import { IonicApp, IonicModule, IonicErrorHandler } from 'ionic-angular';
import { MyApp } from './app.component';
import { IonicStorageModule } from '@ionic/storage';
import { HomePage } from '../pages/home/home';
import { DaysAgo } from '../pipes/days-ago';
import { SlideshowPage } from '../pages/slideshow/slideshow';
import { SimpleAlert } from '../providers/simple-alert';
import { Data } from '../providers/data';

@NgModule({
  declarations: [
    MyApp,
    HomePage,
    SlideshowPage,
    DaysAgo
  ],
  imports: [
    IonicModule.forRoot(MyApp),
    IonicStorageModule.forRoot()
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    HomePage,
    SlideshowPage
  ],
  providers: [Data, SimpleAlert, {provide: ErrorHandler, useClass: IonicErrorHandler}]
})
export class AppModule {}