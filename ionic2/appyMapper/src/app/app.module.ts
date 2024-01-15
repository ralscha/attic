import {NgModule, ErrorHandler} from '@angular/core';
import {IonicApp, IonicModule, IonicErrorHandler} from 'ionic-angular';
import {MyApp} from './app.component';
import {Database} from '../providers/database';
import {Distances} from '../providers/distances';
import {Files} from '../providers/files';
import {Sanitiser} from '../pipes/sanitiser';
import {HomePage} from '../pages/home/home';

@NgModule({
  declarations: [
    MyApp,
    HomePage,
    Sanitiser
  ],
  imports: [
    IonicModule.forRoot(MyApp)
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    HomePage
  ],
  providers: [{provide: ErrorHandler, useClass: IonicErrorHandler}, Database, Distances, Files, Sanitiser]
})
export class AppModule {
}
