import {Component, OnDestroy, OnInit} from '@angular/core';
import {ConnectionService} from '../service/connection.service';
import {Subscription} from 'rxjs';
import {Router} from '@angular/router';

@Component({
  selector: 'app-offline',
  templateUrl: './offline.page.html',
  styleUrls: ['./offline.page.scss'],
})
export class OfflinePage implements OnInit, OnDestroy {

  private subscription: Subscription = null;

  constructor(private readonly connectionService: ConnectionService,
              private readonly router: Router) {
  }

  reconnect(): void {
    this.connectionService.reconnect();
  }

  ngOnInit(): void {
    this.subscription = this.connectionService.connectionState()
      .subscribe(cs => {
        if (cs.isOnline()) {
          if (cs.isUser()) {
            this.router.navigate(['/travel']);
          } else if (cs.isAdmin()) {
            this.router.navigate(['/users']);
          } else {
            this.router.navigate(['/login']);
          }
        }
      });
  }

  ngOnDestroy(): void {
    if (this.subscription !== null) {
      this.subscription.unsubscribe();
    }
  }

}
