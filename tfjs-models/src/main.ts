import {provideRouter, RouteReuseStrategy, withHashLocation} from '@angular/router';
import {IonicRouteStrategy, provideIonicAngular} from '@ionic/angular/standalone';
import {bootstrapApplication} from '@angular/platform-browser';
import {AppComponent} from './app/app.component';
import {routes} from "./app/tabs/tabs.routes";


bootstrapApplication(AppComponent, {
  providers: [
    provideIonicAngular(),
    provideRouter(routes, withHashLocation()),
    {provide: RouteReuseStrategy, useClass: IonicRouteStrategy}
  ]
})
  .catch(err => console.error(err));
