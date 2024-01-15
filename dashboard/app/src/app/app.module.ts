import {NgModule, ErrorHandler} from '@angular/core';
import {IonicApp, IonicModule, IonicErrorHandler} from 'ionic-angular';
import {MyApp} from './app.component';
import {HomePage} from '../pages/home/home';
import {PlayerService} from "../providers/player-service";
import {InputPage} from "../pages/input/input";

@NgModule({
  declarations: [
    MyApp,
    HomePage,
    InputPage
  ],
  imports: [
    IonicModule.forRoot(MyApp)
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    HomePage,
    InputPage
  ],
  providers: [{provide: ErrorHandler, useClass: IonicErrorHandler}, PlayerService]
})
export class AppModule {
}
