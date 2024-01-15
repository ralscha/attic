import { NgModule, ErrorHandler } from '@angular/core';
import { IonicApp, IonicModule, IonicErrorHandler } from 'ionic-angular';
import { MyApp } from './app.component';
import { HomePage } from '../pages/home/home';
import {SettingsPage} from "../pages/settings/settings";
import {IonicStorageModule} from "@ionic/storage";
import {Data} from "../providers/data";
import {Reddit} from "../providers/reddit";

@NgModule({
  declarations: [
    MyApp,
    HomePage,
    SettingsPage
  ],
  imports: [
    IonicModule.forRoot(MyApp),
    IonicStorageModule.forRoot()
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    HomePage,
    SettingsPage
  ],
  providers: [Data, Reddit, {provide: ErrorHandler, useClass: IonicErrorHandler}]
})
export class AppModule {}
