import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Plugins} from '@capacitor/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss']
})
export class HomePage implements OnInit {

  notificationReceived: string;
  notificationActionPerformed: string;
  pendingNotifications: string;

  constructor(private readonly changeDetectorRef: ChangeDetectorRef) {
  }

  async ngOnInit() {
    await Plugins.LocalNotifications.requestPermissions();

    Plugins.LocalNotifications.addListener('localNotificationReceived', notification => {
      this.notificationReceived = JSON.stringify(notification);
      this.changeDetectorRef.markForCheck();
    });

    Plugins.LocalNotifications.addListener('localNotificationActionPerformed', notification => {
      this.notificationActionPerformed = JSON.stringify(notification);
      this.changeDetectorRef.markForCheck();
    });
  }

  async cancelAllPendingNotification() {
    const pendingNotifications = await Plugins.LocalNotifications.getPending();
    await Plugins.LocalNotifications.cancel(pendingNotifications);
    this.getPending();
  }

  async getPending() {
    this.pendingNotifications = JSON.stringify(await Plugins.LocalNotifications.getPending());
  }

  onceNow() {
    Plugins.LocalNotifications.schedule({
      notifications: [{
        title: 'Breaking News!',
        body: 'Ionic Capacitor released',
        id: 1
      }]
    });
  }

  onceInTwoMinutes() {
    const now = new Date();
    Plugins.LocalNotifications.schedule({
      notifications: [{
        title: 'The Future is here',
        body: 'You are 2 minutes older now',
        id: 2,
        schedule: {
          at: new Date(now.getTime() + (2 * 60 * 1000))
        }
      }]
    });
  }

  repeatingOneMinuteAt() {
    const now = new Date();
    Plugins.LocalNotifications.schedule({
      notifications: [{
        title: 'At that repeats',
        body: 'I\'m here every minute',
        id: 3,
        schedule: {
          at: new Date(now.getTime() + (60 * 1000)),
          repeats: true
        }
      }]
    });
  }

  repeatingEveryHour() {
    const now = new Date();
    Plugins.LocalNotifications.schedule({
      notifications: [{
        title: 'Every',
        body: 'Every hour',
        id: 4,
        schedule: {
          every: 'hour', // 'year' | 'month' | 'two-weeks' | 'week' | 'day' | 'hour' | 'minute' | 'second'
        }
      }]
    });
  }

  repeatingOn() {
    const now = new Date();
    Plugins.LocalNotifications.schedule({
      notifications: [{
        title: 'Cron Like Scheduling',
        body: 'Every hour at 55 minutes: 00:55, 01:55, 02:55, ....',
        id: 5,
        schedule: {
          on: {
            day: 18
            /*
              year?: number;
        month?: number;
        day?: number;
        hour?: number;
        minute?: number;
             */
          }
        }
      }]
    });
  }

}
