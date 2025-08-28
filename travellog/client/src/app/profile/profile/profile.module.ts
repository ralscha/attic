import {inject, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ActivatedRouteSnapshot, RouterModule, Routes} from '@angular/router';
import {IonicModule} from '@ionic/angular';
import {ProfilePage} from './profile.page';
import {AuthGuard} from '../../service/auth.guard';

const routes: Routes = [
  {
    path: '',
    component: ProfilePage,
    canActivate: [(route: ActivatedRouteSnapshot) => inject(AuthGuard).canActivate(route)],
    pathMatch: 'full'
  },
  {
    path: 'password',
    loadChildren: () => import('../password/password.module').then(m => m.PasswordPageModule),
    canActivate: [(route: ActivatedRouteSnapshot) => inject(AuthGuard).canActivate(route)]
  },
  {
    path: 'email',
    loadChildren: () => import('../email/email.module').then(m => m.EmailPageModule),
    canActivate: [(route: ActivatedRouteSnapshot) => inject(AuthGuard).canActivate(route)]
  },
  {
    path: 'sessions',
    loadChildren: () => import('../sessions/sessions.module').then(m => m.SessionsPageModule),
    canActivate: [(route: ActivatedRouteSnapshot) => inject(AuthGuard).canActivate(route)]
  },
  {
    path: 'account',
    loadChildren: () => import('../account/account.module').then(m => m.AccountPageModule),
    canActivate: [(route: ActivatedRouteSnapshot) => inject(AuthGuard).canActivate(route)]
  },
  {
    path: '**',
    redirectTo: '/profile'
  }
];


@NgModule({
  imports: [
    CommonModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [ProfilePage]
})
export class ProfilePageModule {
}
