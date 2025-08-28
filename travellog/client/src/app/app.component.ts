import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from './service/auth.service';
import {Subscription} from 'rxjs';
import {ConnectionService, ConnectionState} from './service/connection.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {

  authenticated = false;

  appPages: Array<{ title: string, url: string, icon?: string, faIcon?: string }> = [];

  private subscription!: Subscription;

  constructor(private readonly authService: AuthService,
              private readonly connectionService: ConnectionService) {
  }

  ngOnInit(): void {
    this.subscription = this.connectionService.connectionState().subscribe(cs => this.updateMenu(cs));
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  private updateMenu(connectionState: ConnectionState): void {
    this.authenticated = connectionState.isAuthenticated();

    const newMenu = [];

    if (connectionState.isUser()) {
      newMenu.push({
        title: 'Log',
        url: '/log',
        faIcon: 'far fa-clipboard-list fa-fw'
      }, {
        title: 'Travels',
        url: '/travel',
        faIcon: 'far fa-globe fa-fw'
      });
    }

    if (connectionState.isAdmin()) {
      newMenu.push({
        title: 'Users',
        url: '/users',
        icon: 'people'
      });
    }

    if (connectionState.isOnlineAuthenticated()) {
      newMenu.push({
        title: 'Profile',
        url: '/profile',
        icon: 'person'
      });
    }

    if (connectionState.isAuthenticated()) {
      newMenu.push({
        title: 'Log out',
        url: '/logout',
        icon: 'log-out'
      });
    }

    this.appPages = newMenu;
  }
}
