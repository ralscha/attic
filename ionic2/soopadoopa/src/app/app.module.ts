import {NgModule, ErrorHandler} from '@angular/core';
import {IonicApp, IonicModule, IonicErrorHandler} from 'ionic-angular';
import {MyApp} from './app.component';
import {HomePage} from '../pages/home/home';
import {AlphaMalePage} from "../pages/alpha-male/alpha-male";
import {ClimateHeroPage} from "../pages/climate-hero/climate-hero";
import {GrandTheftNoughtoPage} from "../pages/grand-theft-noughto/grand-theft-noughto";
import {WorldTravellerPage} from "../pages/world-traveller/world-traveller";
import {HappyFamiliesPage} from "../pages/happy-families/happy-families";
import {KingForADayPage} from "../pages/king-for-a-day/king-for-a-day";
import {Database} from "../providers/database";
import {Utilities} from "../providers/utilities";

@NgModule({
  declarations: [
    MyApp,
    HomePage,
    AlphaMalePage,
    ClimateHeroPage,
    GrandTheftNoughtoPage,
    HappyFamiliesPage,
    KingForADayPage,
    WorldTravellerPage
  ],
  imports: [
    IonicModule.forRoot(MyApp)
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    HomePage,
    AlphaMalePage,
    ClimateHeroPage,
    GrandTheftNoughtoPage,
    HappyFamiliesPage,
    KingForADayPage,
    WorldTravellerPage
  ],
  providers: [{provide: ErrorHandler, useClass: IonicErrorHandler}, Database, Utilities]
})
export class AppModule {
}
