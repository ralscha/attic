import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {IonicModule} from '@ionic/angular';
import {SessionsPage} from './sessions.page';
import {RelativeTimePipe} from '../../pipe/relative-time.pipe';

const routes: Routes = [
  {
    path: '',
    component: SessionsPage
  }
];

@NgModule({
  imports: [
    CommonModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [SessionsPage, RelativeTimePipe]
})
export class SessionsPageModule {
}
