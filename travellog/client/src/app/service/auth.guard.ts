import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthService} from './auth.service';
import {map, take} from 'rxjs/operators';
import {ConnectionService} from './connection.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard {

  constructor(private readonly authService: AuthService,
              private readonly connectionService: ConnectionService,
              private readonly router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot):
    Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    const requiredRole = route.data.role as 'ADMIN' | 'USER';
    const offlineCapable = route.data.offline === true;

    return this.connectionService.connectionState()
      .pipe(
        take(1),
        map(cs => {
          if (!cs.isAuthenticated()) {
            if (cs.isOnline()) {
              return this.router.createUrlTree(['/login']);
            }
            return this.router.createUrlTree(['/offline']);
          }

          if (requiredRole && (cs.isOnline() || offlineCapable)) {
            if (requiredRole === 'ADMIN' && cs.isAdmin()) {
              return true;
            } else {
              return requiredRole === 'USER' && cs.isUser();
            }
          } else if (cs.isOnline() || offlineCapable) {
            return true;
          } else {
            if (cs.isUser()) {
              return this.router.createUrlTree(['/travel']);
            }
            return this.router.createUrlTree(['/offline']);
          }
        })
      );
  }

}
