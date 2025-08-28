import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';
import {IonicModule} from '@ionic/angular';
import {TravelEditPage} from './edit.page';

const routes: Routes = [
  {
    path: ':id',
    component: TravelEditPage
  },
  {
    path: '',
    component: TravelEditPage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [TravelEditPage]
})
export class TravelEditPageModule {
}
