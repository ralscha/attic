import {inject, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {ActivatedRouteSnapshot, RouterModule, Routes} from '@angular/router';
import {IonicModule} from '@ionic/angular';
import {TravelListPage} from './list.page';
import {AuthGuard} from '../../service/auth.guard';

const routes: Routes = [
  {
    path: '',
    component: TravelListPage,
    canActivate: [(route: ActivatedRouteSnapshot) => inject(AuthGuard).canActivate(route)],
    data: {role: 'USER', offline: true},
    pathMatch: 'full'
  },
  {
    path: 'edit',
    loadChildren: () => import('../edit/edit.module').then(m => m.TravelEditPageModule),
    canActivate: [(route: ActivatedRouteSnapshot) => inject(AuthGuard).canActivate(route)],
    data: {role: 'USER', offline: true}
  },
  {
    path: '**',
    redirectTo: '/travel'
  },
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [TravelListPage]
})
export class TravelListPageModule {
}
