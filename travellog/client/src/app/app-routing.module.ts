import {inject, NgModule} from '@angular/core';
import {ActivatedRouteSnapshot, PreloadAllModules, RouterModule, Routes} from '@angular/router';
import {AuthGuard} from './service/auth.guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/log',
    pathMatch: 'full'
  },
  {
    path: 'log',
    loadChildren: () => import('./log/log.module').then(m => m.LogModule),
    canActivate: [(route: ActivatedRouteSnapshot) => inject(AuthGuard).canActivate(route)],
    data: {role: 'USER', offline: true}
  },
  {
    path: 'travel',
    loadChildren: () => import('./travel/list/list.module').then(m => m.TravelListPageModule),
    canActivate: [(route: ActivatedRouteSnapshot) => inject(AuthGuard).canActivate(route)],
    data: {role: 'USER', offline: true}
  },
  {
    path: 'profile',
    loadChildren: () => import('./profile/profile/profile.module').then(m => m.ProfilePageModule),
    canActivate: [(route: ActivatedRouteSnapshot) => inject(AuthGuard).canActivate(route)]
  },
  {
    path: 'email-change-confirm',
    loadChildren: () => import('./profile/email-change-confirm/email-change-confirm.module').then(m => m.EmailChangeConfirmPageModule)
  },
  {
    path: 'login',
    loadChildren: () => import('./login/login.module').then(m => m.LoginPageModule)
  },
  {
    path: 'logout',
    loadChildren: () => import('./logout/logout.module').then(m => m.LogoutPageModule)
  },
  {
    path: 'signup',
    loadChildren: () => import('./signup/signup.module').then(m => m.SignupPageModule)
  },
  {
    path: 'signup-confirm',
    loadChildren: () => import('./signup-confirm/signup-confirm.module').then(m => m.SignupConfirmPageModule)
  },
  {
    path: 'password-reset-request',
    loadChildren: () => import('./password-reset-request/password-reset-request.module').then(m => m.PasswordResetRequestPageModule)
  },
  {
    path: 'password-reset',
    loadChildren: () => import('./password-reset/password-reset.module').then(m => m.PasswordResetPageModule)
  },
  {
    path: 'users',
    loadChildren: () => import('./users/users.module').then(m => m.UsersPageModule),
    canActivate: [(route: ActivatedRouteSnapshot) => inject(AuthGuard).canActivate(route)],
    data: {role: 'ADMIN'}
  },
  {
    path: 'offline',
    loadChildren: () => import('./offline/offline.module').then(m => m.OfflinePageModule)
  },
  {
    path: '**',
    redirectTo: '/log'
  }

];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {preloadingStrategy: PreloadAllModules, useHash: true})
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
